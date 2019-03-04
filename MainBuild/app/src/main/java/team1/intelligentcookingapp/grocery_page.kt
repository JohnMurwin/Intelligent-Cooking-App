package team1.intelligentcookingapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView

class grocery_page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grocery_page)
        // Variable declarations ===================================================================

        var NewGroceryItem = findViewById(R.id.editText) as EditText

        var home = findViewById(R.id.homeImage) as ImageView
        var favorites = findViewById(R.id.favoritesImage) as ImageView
        var grocery = findViewById(R.id.groceryImage) as ImageView

        var findStores = findViewById(R.id.findStores) as Button

        var ingredient1 = findViewById(R.id.checkBox) as CheckBox
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
        home.setOnClickListener(View.OnClickListener {
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
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
    }
}
