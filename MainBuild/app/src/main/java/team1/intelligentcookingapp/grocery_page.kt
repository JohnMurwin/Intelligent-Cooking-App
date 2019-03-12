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

    internal var ingredientsList: MutableList<String> = ArrayList()
    internal var groceryList: MutableList<String> = ArrayList()
    internal var favoritesList: MutableList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grocery_page)
        gestureObject = GestureDetectorCompat(this, LearnGesture())

        // Getting Intent Extras ===================================================================

        val intent = intent
        val extras = getIntent().extras

        if (intent.hasExtra("ingredients")) {
            ingredientsList = extras!!.getStringArrayList("ingredients")
        }
        if (intent.hasExtra("grocery")) {
            groceryList = extras!!.getStringArrayList("grocery")
            removeDuplicates2(groceryList as ArrayList<String>)

        }
        if (intent.hasExtra("favorites")) {
            favoritesList = extras!!.getStringArrayList("favorites")
        }

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
            intent.putStringArrayListExtra("ingredients", ingredientsList as ArrayList<String>)
            intent.putStringArrayListExtra("grocery", groceryList as ArrayList<String>)
            intent.putStringArrayListExtra("favorites", favoritesList as ArrayList<String>)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        })
        // Favorites image listener ----------------------------------------------------------------
        favorites.setOnClickListener(View.OnClickListener {
            val intent2 = Intent(baseContext, favorites_page::class.java)
            intent2.putStringArrayListExtra("ingredients", ingredientsList as ArrayList<String>)
            intent2.putStringArrayListExtra("grocery", groceryList as ArrayList<String>)
            intent2.putStringArrayListExtra("favorites", favoritesList as ArrayList<String>)
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

    fun removeDuplicates2(savedIngredient: ArrayList<*>) {
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

    fun checkBoxCreator(newIngredient: String){
        val linearLayout = findViewById(R.id.linearLayout2) as LinearLayout

        val checkBox1 = CheckBox(this)
        checkBox1.text = newIngredient
        checkBox1.isChecked = true
        linearLayout.addView(checkBox1)

        groceryList.add(newIngredient)

        checkBox1.setOnClickListener { v ->
            val checkBox2 = v as CheckBox

            if (checkBox2.isChecked) {
                groceryList.add(newIngredient)
                //String test = "List added: " + ingredients.get(iter);
                //checkBox2.setText(test);
                //iter++;
            } else {
                //iter--;
                //String test = "List removed: " + ingredients.get(iter);
                groceryList.remove(newIngredient)
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
                intentSwipeLeft.putStringArrayListExtra("ingredients", ingredientsList as ArrayList<String>)
                intentSwipeLeft.putStringArrayListExtra("grocery", groceryList as ArrayList<String>)
                intentSwipeLeft.putStringArrayListExtra("favorites", favoritesList as ArrayList<String>)
                //finish()
                startActivity(intentSwipeLeft)
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            }
            else if (event2.x < event1.x) {
                val intentSwipeRight = Intent(this@grocery_page, favorites_page::class.java)
                intentSwipeRight.putStringArrayListExtra("ingredients", ingredientsList as ArrayList<String>)
                intentSwipeRight.putStringArrayListExtra("grocery", groceryList as ArrayList<String>)
                intentSwipeRight.putStringArrayListExtra("favorites", favoritesList as ArrayList<String>)
                //finish()
                startActivity(intentSwipeRight)
            }
            return true
        }
    }
}
