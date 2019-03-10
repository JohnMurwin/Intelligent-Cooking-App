package team1.intelligentcookingapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.squareup.picasso.Picasso;

public class selected_recipe extends AppCompatActivity {
    ImageView recipeImg;
    ImageView home, favorites, grocery;

    LinearLayout LL;
    TextView titleHolder;
    TextView numOfIngred;
    Button addIngredientsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_recipe);

        String title = getIntent().getStringExtra("rTitle");
        String[] ingredients = getIntent().getStringArrayExtra("rIngredients");
        String imageUrl = getIntent().getStringExtra("rImgUrl");
        String webUrl = getIntent().getStringExtra("rUrl");

        addIngredientsBtn = (Button)findViewById(R.id.addIngredientsBtn);
        home = (ImageView)findViewById(R.id.homeImage);
        favorites = (ImageView)findViewById(R.id.favoritesImage);
        grocery = (ImageView)findViewById(R.id.groceryImage);

        addIngredientsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send ingredients to be checkboxes
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
        });

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), favorites_page.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        grocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), grocery_page.class);
                startActivity(i);
            }
        });

        LL = (LinearLayout)findViewById(R.id.rlinlay);
        titleHolder = (TextView)findViewById(R.id.recipeTitle);
        numOfIngred = (TextView)findViewById(R.id.numOfIngred);
        recipeImg = (ImageView)findViewById(R.id.imageView);

        Picasso.with(LL.getContext()).load(imageUrl).resize(410, 240)
                .into(recipeImg);

        titleHolder.setText(title);

        String ingredientSize = ingredients.length + " ingredients";
        numOfIngred.setText(ingredientSize);
        for (String i : ingredients)
        {
            TextView ingred = new TextView(LL.getContext());
            String format = "- " + i;
            ingred.setText(format);
            ingred.setTextSize(24);
            LL.addView(ingred);
        }

        Space s = new Space(LL.getContext());
        s.setMinimumHeight(50);
        s.setMinimumWidth(LL.getWidth());
        LL.addView(s);

        TextView stepHead = new TextView(LL.getContext());
        stepHead.setText("Steps");
        stepHead.setTextSize(24);
        stepHead.setTextColor(Color.BLACK);
        LL.addView(stepHead);

        TextView stepURL = new TextView(LL.getContext());
        String stepDescript = "View on " + webUrl;
        stepURL.setText(stepDescript);
        stepURL.setTextSize(24);
        LL.addView(stepURL);

    }
}
