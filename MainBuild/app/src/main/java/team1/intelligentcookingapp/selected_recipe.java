package team1.intelligentcookingapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class selected_recipe extends AppCompatActivity {
    ImageView recipeImg;
    ImageView home, favorites, grocery, back, star;

    LinearLayout LL;
    TextView titleHolder;
    TextView numOfIngred;
    Button addIngredientsBtn;

    List<String> ingredientsList = new ArrayList<>();
    List<String> groceryList = new ArrayList<>();
    List<String> favoritesList = new ArrayList<>();

    String[] ingredients;
    boolean selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_recipe);

        // Getting Intent Extras ===================================================================

        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();

        if (intent.hasExtra("ingredients")) {
            ingredientsList = extras.getStringArrayList("ingredients");
        }
        if (intent.hasExtra("grocery")){
            groceryList = extras.getStringArrayList("grocery");
        }
        if (intent.hasExtra("favorites")){
            favoritesList = extras.getStringArrayList("favorites");
        }

        String title = getIntent().getStringExtra("rTitle");
        ingredients = getIntent().getStringArrayExtra("rIngredients");
        String imageUrl = getIntent().getStringExtra("rImgUrl");
        final String webUrl = getIntent().getStringExtra("rUrl");

        back = (ImageView)findViewById(R.id.backarrow);
        star = (ImageView)findViewById(R.id.star);
        addIngredientsBtn = (Button)findViewById(R.id.addIngredientsBtn);
        home = (ImageView)findViewById(R.id.homeImage);
        favorites = (ImageView)findViewById(R.id.favoritesImage);
        grocery = (ImageView)findViewById(R.id.groceryImage);

        home.setColorFilter(home.getContext().getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                i.putStringArrayListExtra("ingredients", (ArrayList<String>) ingredientsList);
                i.putStringArrayListExtra("grocery", (ArrayList<String>) groceryList);
                i.putStringArrayListExtra("favorites", (ArrayList<String>) favoritesList);
                startActivity(i);
            }
        });

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), favorites_page.class);
                i.putStringArrayListExtra("ingredients", (ArrayList<String>) ingredientsList);
                i.putStringArrayListExtra("grocery", (ArrayList<String>) groceryList);
                i.putStringArrayListExtra("favorites", (ArrayList<String>) favoritesList);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        grocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), grocery_page.class);
                i.putStringArrayListExtra("ingredients", (ArrayList<String>) ingredientsList);
                i.putStringArrayListExtra("grocery", (ArrayList<String>) groceryList);
                i.putStringArrayListExtra("favorites", (ArrayList<String>) favoritesList);
                startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), list_of_recipes.class);
                i.putStringArrayListExtra("ingredient", (ArrayList<String>) ingredientsList);
                i.putStringArrayListExtra("grocery", (ArrayList<String>) groceryList);
                i.putStringArrayListExtra("favorites", (ArrayList<String>) favoritesList);
                startActivity(i);
            }
        });

        selected = false;
        final String recipeTitle = title;
        star.setColorFilter(star.getContext().getResources().getColor(R.color.DarkGrey), PorterDuff.Mode.SRC_ATOP);
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected == false){
                    selected = true;
                    star.setColorFilter(star.getContext().getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);

                    String favoritesText = recipeTitle + " - " + webUrl;
                    favoritesList.add(favoritesText);
                    
                    Toast.makeText(getApplicationContext(), "Recipe Added To Favorites", Toast.LENGTH_SHORT).show();
                }
                else {
                    selected = false;
                    star.setColorFilter(star.getContext().getResources().getColor(R.color.DarkGrey), PorterDuff.Mode.SRC_ATOP);
                    favoritesList.remove(titleHolder.getText().toString());
                    Toast.makeText(getApplicationContext(), "Recipe Removed From Favorites", Toast.LENGTH_SHORT).show();
                }
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
        String stepDescript = "View on ";
        stepURL.setText(stepDescript);
        stepURL.setTextSize(24);
        LL.addView(stepURL);

        TextView urlText = new TextView(LL.getContext());
        urlText.setText(webUrl);
        urlText.setTextSize(24);
        urlText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl));
                startActivity(browserIntent);
            }
        });
        LL.addView(urlText);

        addIngredientsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGroceryItems(ingredients);
            }
        });

    }

    public void addGroceryItems(String[] ingredients){
        String ingredientSize = ingredients.length + " ingredients";
        numOfIngred.setText(ingredientSize);
        for (String i : ingredients)
        {
            groceryList.add(i);
            Toast.makeText(getApplicationContext(), "Ingredients Added to Grocery List", Toast.LENGTH_SHORT).show();
        }
    }
}
