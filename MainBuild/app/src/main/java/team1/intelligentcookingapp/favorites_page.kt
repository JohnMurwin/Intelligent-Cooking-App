package team1.intelligentcookingapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class favorites_page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites_page)

        // Variable declarations ===================================================================

        var searchRecipe = findViewById(R.id.editText2) as EditText

        var home = findViewById(R.id.homeImage) as ImageView
        var favorites = findViewById(R.id.favoritesImage) as ImageView
        var grocery = findViewById(R.id.groceryImage) as ImageView

        var recipe1 = findViewById(R.id.textView6) as TextView
        var recipe2 = findViewById(R.id.textView7) as TextView
        var recipe3 = findViewById(R.id.textView8) as TextView
        var recipe4 = findViewById(R.id.textView9) as TextView
        var recipe5 = findViewById(R.id.textView10) as TextView
        var recipe6 = findViewById(R.id.textView11) as TextView
        var recipe7 = findViewById(R.id.textView12) as TextView
        var recipe8 = findViewById(R.id.textView13) as TextView
        // recipe9 = (TextView)findViewById(R.id.textView14);
        // recipe10 = (TextView)findViewById(R.id.textView15);
        // recipe11 = (TextView)findViewById(R.id.textView16);
        // recipe12 = (TextView)findViewById(R.id.textView17);

        // Onclick image listeners =================================================================
        // =========================================================================================

        // Home image listener ---------------------------------------------------------------------
        home.setOnClickListener(View.OnClickListener {
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
        })
        // Favorites image listener ----------------------------------------------------------------
        favorites.setOnClickListener(View.OnClickListener {
            // Already on the favorites page
        })
        // Grocery image listener ------------------------------------------------------------------
        grocery.setOnClickListener(View.OnClickListener {
            val intent2 = Intent(baseContext, grocery_page::class.java)
            startActivity(intent2)
        })

        // Camera image listener -------------------------------------------------------------------

        // Add image listener ----------------------------------------------------------------------

        // Onclick text listeners ==============================================================
        // =========================================================================================

    }
}
