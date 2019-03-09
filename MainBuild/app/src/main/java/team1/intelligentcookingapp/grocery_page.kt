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

class grocery_page : AppCompatActivity() {

    private var gestureObject: GestureDetectorCompat? = null
    internal var ingredients: MutableList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grocery_page)
        gestureObject = GestureDetectorCompat(this, LearnGesture())

        // Variable declarations ===================================================================

        var NewGroceryItem = findViewById(R.id.editText) as EditText

        var home = findViewById(R.id.homeImage) as ImageView
        var favorites = findViewById(R.id.favoritesImage) as ImageView
        var grocery = findViewById(R.id.groceryImage) as ImageView
        var addImage = findViewById(R.id.addImage) as ImageView
        var findStores = findViewById(R.id.findStores) as Button

        // Onclick image listeners =================================================================
        // =========================================================================================

        // Home image listener ---------------------------------------------------------------------
        home.setOnClickListener(View.OnClickListener {
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        })
        // Favorites image listener ----------------------------------------------------------------
        favorites.setOnClickListener(View.OnClickListener {
            val intent2 = Intent(baseContext, favorites_page::class.java)
            startActivity(intent2)
        })
        // Grocery image listener ------------------------------------------------------------------
        grocery.setOnClickListener(View.OnClickListener {
            // Already on the grocery page
        })

        // Find recipe button listener -------------------------------------------------------------
        findStores.setOnClickListener(View.OnClickListener {
            // Intent intent = new Intent (getBaseContext(), .class);
            // startActivity(intent);
        })

        // Add image listener ----------------------------------------------------------------------
        addImage.setOnClickListener {
            checkBoxCreator(NewGroceryItem.getText().toString());
        }

    }

    fun checkBoxCreator(newIngredient: String){
        val linearLayout = findViewById(R.id.linearLayout2) as LinearLayout

        val checkBox1 = CheckBox(this)
        checkBox1.text = newIngredient
        checkBox1.isChecked = true
        linearLayout.addView(checkBox1)

        ingredients.add(newIngredient)

        checkBox1.setOnClickListener { v ->
            val checkBox2 = v as CheckBox

            if (checkBox2.isChecked) {
                ingredients.add(newIngredient)
                //String test = "List added: " + ingredients.get(iter);
                //checkBox2.setText(test);
                //iter++;
            } else {
                //iter--;
                //String test = "List removed: " + ingredients.get(iter);
                ingredients.remove(newIngredient)
                //checkBox2.setText(test);
            }
        }

    }

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
                val intentSwipeLeft = Intent(this@grocery_page, MainActivity::class.java)
                finish()
                startActivity(intentSwipeLeft)
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            }
            else if (event2.x < event1.x) {
                val intentSwipeRight = Intent(this@grocery_page, favorites_page::class.java)
                finish()
                startActivity(intentSwipeRight)
            }
            return true
        }
    }
}
