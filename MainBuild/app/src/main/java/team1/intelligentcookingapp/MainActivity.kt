package team1.intelligentcookingapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var gestureObject: GestureDetectorCompat? = null
    internal var ingredients: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gestureObject = GestureDetectorCompat(this, LearnGesture())

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
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        }
        // Grocery image listener ------------------------------------------------------------------
        grocery.setOnClickListener {
            val intent2 = Intent(baseContext, grocery_page::class.java)
            startActivity(intent2)
        }

        // Find recipe button listener -------------------------------------------------------------
        findRecipes.setOnClickListener {
            //val intentFind = Intent(baseContext, ::class.java)
            //intentFind.putStringArrayListExtra("ingredients", ingredients as ArrayList<String>)
            //startActivity(intentFind);
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

    }

    fun checkBoxCreator(newIngredient: String){
        val linearLayout = findViewById(R.id.linearLayout2) as LinearLayout

        val checkBox1 = CheckBox(this)
        checkBox1.text = newIngredient
        checkBox1.isChecked = true
        linearLayout.addView(checkBox1)

        checkBox1.setOnClickListener(View.OnClickListener {
            val checkBox = this as CheckBox

            if (checkBox.isChecked) {
                ingredients.add(checkBox.text.toString())
            } else {
                ingredients.remove(checkBox.text.toString())
            }
        })

    }

    // Onclick checkbox listeners ==============================================================
    // =========================================================================================

    fun onCheckChanged(v: View) {

        val checkBox = v as CheckBox

        if (checkBox.isChecked) {
            ingredients.add(checkBox.text.toString())
        } else {
            ingredients.remove(checkBox.text.toString())
        }
    }

    // Gesture listener ============================================================================
    // =============================================================================================

    override fun onTouchEvent(event: MotionEvent): Boolean {
        this.gestureObject?.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    internal inner class LearnGesture : GestureDetector.SimpleOnGestureListener() {

        override fun onFling(
            event1: MotionEvent, event2: MotionEvent,
            velocityX: Float, velocityY: Float
        ): Boolean {

            if (event2.x > event1.x) {
                val intentSwipeLeft = Intent(this@MainActivity, favorites_page::class.java)
                finish()
                startActivity(intentSwipeLeft)
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            }
            else if (event2.x < event1.x) {
                val intentSwipeRight = Intent(this@MainActivity, grocery_page::class.java)
                finish()
                startActivity(intentSwipeRight)
            }
            return true
        }
    }

}
