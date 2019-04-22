package team1.intelligentcookingapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.PorterDuff
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.support.v4.view.GestureDetectorCompat
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.*
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.vision.barcode.Barcode
import team1.intelligentcookingapp.barcode.BarcodeCaptureActivity
import java.util.ArrayList

class MainActivity : AppCompatActivity(), SensorEventListener {

    private var gestureObject: GestureDetectorCompat? = null

    private var sensorIntent: Intent? = null
    private var myService: SensorService? = null
    private var isServiceBound: Boolean = false
    private var myServiceConnection: ServiceConnection? = null

    private lateinit var mResultTextView: TextView

    internal var ingredientsList: MutableList<String> = ArrayList()
    internal var groceryList: MutableList<String> = ArrayList()
    internal var favoritesList: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gestureObject = GestureDetectorCompat(this, LearnGesture())

        // Getting Intent Extras ===================================================================

        val intent = intent
        val extras = getIntent().extras

        if (intent.hasExtra("ingredients")) {
            ingredientsList = extras!!.getStringArrayList("ingredients")
            savedCheckBoxCreator(ingredientsList as ArrayList<String>)
        }
        if (intent.hasExtra("grocery")) {
            groceryList = extras!!.getStringArrayList("grocery")
        }
        if (intent.hasExtra("favorites")) {
            favoritesList = extras!!.getStringArrayList("favorites")
        }

        // Variable declarations ===================================================================

        var NewIngredient = findViewById(R.id.editText) as EditText

        var home = findViewById(R.id.homeImage) as ImageView
        var favorites = findViewById(R.id.favoritesImage) as ImageView
        var grocery = findViewById(R.id.groceryImage) as ImageView
        var camera = findViewById(R.id.camera) as ImageView
        var addImage = findViewById(R.id.addImage) as ImageView

        var findRecipes = findViewById(R.id.findRecipes) as Button

        mResultTextView = findViewById(R.id.result_textview)

        // Onclick image listeners =================================================================
        // =========================================================================================

        home.setColorFilter(home.context.resources.getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP)
        home.setOnClickListener {
            // Already on the home page
        }


        favorites.setOnClickListener {
            val intent = Intent(baseContext, favorites_page::class.java)
            intent.putStringArrayListExtra("ingredients", ingredientsList as ArrayList<String>)
            intent.putStringArrayListExtra("grocery", groceryList as ArrayList<String>)
            intent.putStringArrayListExtra("favorites", favoritesList as ArrayList<String>)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        }

        grocery.setOnClickListener {
            val intent2 = Intent(baseContext, grocery_page::class.java)
            intent2.putStringArrayListExtra("ingredients", ingredientsList as ArrayList<String>)
            intent2.putStringArrayListExtra("grocery", groceryList as ArrayList<String>)
            intent2.putStringArrayListExtra("favorites", favoritesList as ArrayList<String>)
            startActivity(intent2)
        }

        findRecipes.setOnClickListener {
            val intentFind = Intent(baseContext, list_of_recipes::class.java)
            intentFind.putStringArrayListExtra("ingredients", ingredientsList as ArrayList<String>)
            intentFind.putStringArrayListExtra("grocery", groceryList as ArrayList<String>)
            intentFind.putStringArrayListExtra("favorites", favoritesList as ArrayList<String>)
            startActivity(intentFind);
        }




        findViewById<Button>(R.id.scan_barcode_button).setOnClickListener {
            val intent = Intent(applicationContext, BarcodeCaptureActivity::class.java)
            startActivityForResult(intent, BARCODE_READER_REQUEST_CODE)
        }

        addImage.setOnClickListener {
            checkBoxCreator(NewIngredient.getText().toString());
        }
    }

    // Saved CheckBox Creator ================================================================================================
    // =================================================================================================================

    fun savedCheckBoxCreator(savedIngredient: ArrayList<*>) {
        for (i in savedIngredient.indices) {
            var duplicate = false
            val savedI = savedIngredient[i] as String
            for (j in 0 until i) {
                if (j != i) {
                    val savedI2 = savedIngredient[j] as String
                    if (savedI == savedI2) {
                        duplicate = true
                    }
                }
            }
            if ((duplicate == false)) {
                checkBoxCreator(savedI)
            }
        }
    }

    // Add image CheckBox Creator ======================================================================================
    // =================================================================================================================

    fun checkBoxCreator(newIngredient: String){
        val linearLayout = findViewById(R.id.linearLayout2) as LinearLayout

        val checkBox1 = CheckBox(this)
        checkBox1.text = newIngredient
        checkBox1.isChecked = true
        linearLayout.addView(checkBox1)

        ingredientsList.add(newIngredient)
        //Toast.makeText(getApplicationContext(), "Ingredient added", Toast.LENGTH_SHORT).show()

        checkBox1.setOnClickListener { v ->
            val checkBox2 = v as CheckBox
            if (checkBox2.isChecked) {
                ingredientsList.add(newIngredient)
            } else {
                ingredientsList.remove(newIngredient)
            }
        }
    }

    // Onclick checkbox listeners ==============================================================
    // =========================================================================================

    fun onCheckChanged(v: View) {
        val checkBox = v as CheckBox
        ingredientsList.add(checkBox.text.toString())
        if (checkBox.isChecked) {
            ingredientsList.add(checkBox.text.toString())
        } else {
            ingredientsList.remove(checkBox.text.toString())
        }
    }

    // Gesture listener ============================================================================
    // =============================================================================================

    override fun onTouchEvent(event: MotionEvent): Boolean {
        this.gestureObject?.onTouchEvent(event)


        if (event.action == MotionEvent.ACTION_DOWN) {
            sensorIntent = Intent(applicationContext, SensorService::class.java)
            startService(sensorIntent)
            bindMyService()
        } else if (event.action == MotionEvent.ACTION_UP) {
            if (isServiceBound) {
                if (myService!!.shakeDetected()) {
                    clearIngredients()
                    ingredientsList.clear()
                }
            }
        }

        return super.onTouchEvent(event)
    }

    internal inner class LearnGesture : GestureDetector.SimpleOnGestureListener() {

        override fun onFling(
            event1: MotionEvent, event2: MotionEvent,
            velocityX: Float, velocityY: Float
        ): Boolean {

            if (event2.x > event1.x) {
                val intentSwipeLeft = Intent(this@MainActivity, favorites_page::class.java)
                intentSwipeLeft.putStringArrayListExtra("ingredients", ingredientsList as ArrayList<String>)
                intentSwipeLeft.putStringArrayListExtra("grocery", groceryList as ArrayList<String>)
                intentSwipeLeft.putStringArrayListExtra("favorites", favoritesList as ArrayList<String>)
                //finish()
                startActivity(intentSwipeLeft)
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            }
            else if (event2.x < event1.x) {
                val intentSwipeRight = Intent(this@MainActivity, grocery_page::class.java)
                intentSwipeRight.putStringArrayListExtra("ingredients", ingredientsList as ArrayList<String>)
                intentSwipeRight.putStringArrayListExtra("grocery", groceryList as ArrayList<String>)
                intentSwipeRight.putStringArrayListExtra("favorites", favoritesList as ArrayList<String>)
                //finish()
                startActivity(intentSwipeRight)
            }
            return true
        }
    }

    // Accelerometer ===============================================================================
    // =============================================================================================

    private fun bindMyService() {
        if (myServiceConnection == null) {
            myServiceConnection = object : ServiceConnection {
                override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
                    val myServiceBinder = iBinder as SensorService.MyLocalBinder
                    myService = myServiceBinder.getService()
                    isServiceBound = true
                }

                override fun onServiceDisconnected(componentName: ComponentName) {
                    isServiceBound = false
                }
            }
        }

        bindService(sensorIntent, myServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent) {
            sensorIntent = Intent(applicationContext, SensorService::class.java)
            startService(sensorIntent)
            bindMyService()
            if (isServiceBound) {
                if (myService!!.shakeDetected()) {
                    clearIngredients()
                    ingredientsList.clear()
                }
            }
    }

    private fun clearIngredients() {
        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout2)
        linearLayout.removeAllViewsInLayout()
        Toast.makeText(getApplicationContext(), "Pantry list cleared", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    val barcode = data.getParcelableExtra<Barcode>(BarcodeCaptureActivity.BarcodeObject)
                    val p = barcode.cornerPoints
                    mResultTextView.text = barcode.displayValue             //BARCODE VALUE HERE -> THROW THIS INTO THE VALUE ARRAY
                    // ingredientsList.add(variable name here);
                } else
                    mResultTextView.setText(R.string.no_barcode_captured)
            } else
                Log.e(LOG_TAG, String.format(getString(R.string.barcode_error_format),
                    CommonStatusCodes.getStatusCodeString(resultCode)))
        } else
            super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private val LOG_TAG = MainActivity::class.java.simpleName
        private val BARCODE_READER_REQUEST_CODE = 1
    }
}
