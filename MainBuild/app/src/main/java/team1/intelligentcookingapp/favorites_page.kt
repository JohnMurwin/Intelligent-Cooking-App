package team1.intelligentcookingapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import java.util.ArrayList

class favorites_page : AppCompatActivity() {

    private var gestureObject: GestureDetectorCompat? = null

    internal var ingredientsList: MutableList<String> = ArrayList()
    internal var groceryList: MutableList<String> = ArrayList()
    internal var favoritesList: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites_page)
        gestureObject = GestureDetectorCompat(this, LearnGesture())

        // Getting Intent Extras ===================================================================

        val intent = intent
        val extras = getIntent().extras

        if (intent.hasExtra("ingredients")) {
            ingredientsList = extras!!.getStringArrayList("ingredients")
        }
        if (intent.hasExtra("grocery")) {
            groceryList = extras!!.getStringArrayList("grocery")
        }
        if (intent.hasExtra("favorites")) {
            favoritesList = extras!!.getStringArrayList("favorites")
            removeDuplicates(favoritesList as ArrayList<String>)
        }

        // Variable declarations ===================================================================

        var searchRecipe = findViewById(R.id.editText2) as EditText

        var home = findViewById(R.id.homeImage) as ImageView
        var favorites = findViewById(R.id.favoritesImage) as ImageView
        var grocery = findViewById(R.id.groceryImage) as ImageView

        // Onclick image listeners =================================================================
        // =========================================================================================

        // Home image listener ---------------------------------------------------------------------
        home.setOnClickListener(View.OnClickListener {
            val intent = Intent(baseContext, MainActivity::class.java)
            intent.putStringArrayListExtra("ingredients", ingredientsList as ArrayList<String>)
            intent.putStringArrayListExtra("grocery", groceryList as ArrayList<String>)
            intent.putStringArrayListExtra("favorites", favoritesList as ArrayList<String>)
            startActivity(intent)
        })
        // Favorites image listener ----------------------------------------------------------------
        favorites.setOnClickListener(View.OnClickListener {
            // Already on the favorites page
        })
        // Grocery image listener ------------------------------------------------------------------
        grocery.setOnClickListener(View.OnClickListener {
            val intent2 = Intent(baseContext, grocery_page::class.java)
            intent2.putStringArrayListExtra("ingredients", ingredientsList as ArrayList<String>)
            intent2.putStringArrayListExtra("grocery", groceryList as ArrayList<String>)
            intent2.putStringArrayListExtra("favorites", favoritesList as ArrayList<String>)
            startActivity(intent2)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        })

        // Camera image listener -------------------------------------------------------------------

        // Add image listener ----------------------------------------------------------------------

        // Onclick text listeners ==============================================================
        // =========================================================================================

    }

    fun removeDuplicates(savedRecipe: ArrayList<*>) {
        for (i in savedRecipe.indices) {
            var duplicate = false
            val savedI = savedRecipe[i] as String
            for (j in 0 until i) {
                if (j != i) {
                    val savedI2 = savedRecipe[j] as String
                    if (savedI == savedI2) {
                        duplicate = true
                    }
                }
            }
            if ((duplicate == false)) {
                recipeCreator(savedI)
            }
        }
    }

    fun recipeCreator (recipe: String){}


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
                val intentSwipeLeft = Intent(this@favorites_page, grocery_page::class.java)
                intentSwipeLeft.putStringArrayListExtra("ingredients", ingredientsList as ArrayList<String>)
                intentSwipeLeft.putStringArrayListExtra("grocery", groceryList as ArrayList<String>)
                intentSwipeLeft.putStringArrayListExtra("favorites", favoritesList as ArrayList<String>)
                //finish()
                startActivity(intentSwipeLeft)
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            }
            else if (event2.x < event1.x) {
                val intentSwipeRight = Intent(this@favorites_page, MainActivity::class.java)
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
