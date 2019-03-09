package team1.intelligentcookingapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    // internal var NewIngredient: EditText? = null;

    // internal var home: ImageView? = null;
    // internal var favorites: ImageView? = null;
    // internal var grocery: ImageView? = null;
    // internal var camera: ImageView? = null
    // internal var addImage: ImageView? = null;

    // internal var findRecipes: Button? = null;

    // internal var ingredient1: CheckBox, internal var ingredient2:CheckBox, internal var ingredient3:CheckBox, internal var ingredient4:CheckBox, internal var ingredient5:CheckBox, internal var ingredient6:CheckBox
    // internal var ingredient7: CheckBox, internal var ingredient8:CheckBox, internal var ingredient9:CheckBox? = null, internal var ingredient10:CheckBox? = null, internal var ingredient11:CheckBox? = null, internal var ingredient12:CheckBox? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Variable declarations ===================================================================

        var NewIngredient = findViewById(R.id.editText) as EditText

        var home = findViewById(R.id.homeImage) as ImageView
        var favorites = findViewById(R.id.favoritesImage) as ImageView
        var grocery = findViewById(R.id.groceryImage) as ImageView
        var camera = findViewById(R.id.camera) as ImageView
        var addImage = findViewById(R.id.addImage) as ImageView

        var findRecipes = findViewById(R.id.findRecipes) as Button

        var ingredient1 = findViewById(R.id.checkBox1) as CheckBox
        var ingredient2 = findViewById(R.id.checkBox2) as CheckBox
        var ingredient3 = findViewById(R.id.checkBox3) as CheckBox
        var ingredient4 = findViewById(R.id.checkBox4) as CheckBox
        var ingredient5 = findViewById(R.id.checkBox5) as CheckBox
        var ingredient6 = findViewById(R.id.checkBox6) as CheckBox
        var ingredient7 = findViewById(R.id.checkBox7) as CheckBox
        var ingredient8 = findViewById(R.id.checkBox8) as CheckBox
        // ingredient9 = (CheckBox)findViewById(R.id.checkBox9);
        // ingredient10 = (CheckBox)findViewById(R.id.checkBox10);
        // ingredient11 = (CheckBox)findViewById(R.id.checkBox11);
        // ingredient12 = (CheckBox)findViewById(R.id.checkBox12);

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
        }
        // Grocery image listener ------------------------------------------------------------------
        grocery.setOnClickListener {
            val intent2 = Intent(baseContext, grocery_page::class.java)
            startActivity(intent2)
        }

        // Find recipe button listener -------------------------------------------------------------
        findRecipes.setOnClickListener {
            val intent = Intent (baseContext, list_of_recipes::class.java);
            startActivity(intent);
        }

        // Camera image listener -------------------------------------------------------------------

        // Add image listener ----------------------------------------------------------------------


        // Onclick checkbox listeners ==============================================================
        // =========================================================================================

        // Ingredient 1 ----------------------------------------------------------------------------
        ingredient1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView === ingredient1) {
                if (isChecked) {
                } else {
                }

            }
        }

        // Ingredient 2 ----------------------------------------------------------------------------
    }


}
