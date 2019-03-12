package team1.intelligentcookingapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.support.v4.view.GestureDetectorCompat
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var gestureObject: GestureDetectorCompat? = null
    internal var ingredients: MutableList<String> = ArrayList()

    private var sensorIntent: Intent? = null
    private var myService: SensorService? = null
    private var isServiceBound: Boolean = false
    private var myServiceConnection: ServiceConnection? = null

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

        clearIngredients()

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

        // Onclick image listeners =================================================================
        // =========================================================================================

        // Home image listener ---------------------------------------------------------------------
        home.setOnClickListener {
            // Already on the home page
        }
        // Favorites image listener ----------------------------------------------------------------
        favorites.setOnClickListener {
            val intent = Intent(baseContext, favorites_page::class.java)
            intent.putStringArrayListExtra("ingredients", ingredientsList as ArrayList<String>)
            intent.putStringArrayListExtra("grocery", groceryList as ArrayList<String>)
            intent.putStringArrayListExtra("favorites", favoritesList as ArrayList<String>)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        }
        // Grocery image listener ------------------------------------------------------------------
        grocery.setOnClickListener {
            val intent2 = Intent(baseContext, grocery_page::class.java)
            intent2.putStringArrayListExtra("ingredients", ingredientsList as ArrayList<String>)
            intent2.putStringArrayListExtra("grocery", groceryList as ArrayList<String>)
            intent2.putStringArrayListExtra("favorites", favoritesList as ArrayList<String>)
            startActivity(intent2)
        }

        // Find recipe button listener -------------------------------------------------------------
        findRecipes.setOnClickListener {
            val intentFind = Intent(baseContext, list_of_recipes::class.java)
            intentFind.putStringArrayListExtra("ingredients", ingredientsList as ArrayList<String>)
            intentFind.putStringArrayListExtra("grocery", groceryList as ArrayList<String>)
            intentFind.putStringArrayListExtra("favorites", favoritesList as ArrayList<String>)
            startActivity(intentFind);
        }

        // Camera image listener -------------------------------------------------------------------
        camera.setOnClickListener {
            // Intent intent = new Intent (getBaseContext(), .class);
            // startActivity(intent);
        }

        // Add image listener ----------------------------------------------------------------------
        addImage.setOnClickListener {
            checkBoxCreator(NewIngredient.getText().toString());
        }

        /*
        val imageView = findViewById<View>(R.id.imageView3) as ImageView
        imageView.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                sensorIntent = Intent(applicationContext, SensorService::class.java)
                startService(sensorIntent)
                bindMyService()
                return@OnTouchListener true
            } else if (event.action == MotionEvent.ACTION_UP) {
                if (isServiceBound) {
                    if (myService!!.shakeDetected())
                        clearIngredients()
                }
                return@OnTouchListener true
            }
            return@OnTouchListener false
        })
        */

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

        checkBox1.setOnClickListener { v ->
            val checkBox2 = v as CheckBox

            if (checkBox2.isChecked) {
                ingredientsList.add(newIngredient)
                //String test = "List added: " + ingredients.get(iter);
                //checkBox2.setText(test);
                //iter++;
            } else {
                //iter--;
                //String test = "List removed: " + ingredients.get(iter);
                ingredientsList.remove(newIngredient)
                //checkBox2.setText(test);
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

    private fun clearIngredients() {
        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout2)
        linearLayout.removeAllViewsInLayout()
    }
}
