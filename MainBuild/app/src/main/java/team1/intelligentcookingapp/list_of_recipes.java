package team1.intelligentcookingapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;
import android.os.StrictMode;
import android.widget.Space;
import android.widget.TextView;
import com.squareup.picasso.*;

public class list_of_recipes extends AppCompatActivity {
    LinearLayout LL;
    ImageView home, favorites, grocery;

    // Used if result from list of ingredients = null
    String nullResultMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_recipes);

        // For some reason this makes the API connection work
        // move API work to a service in the future?
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ArrayList<String> pantryList = getIntent().getStringArrayListExtra("ingredients");

        LL = (LinearLayout)findViewById(R.id.LinearLayout);
        home = (ImageView)findViewById(R.id.homeImage);
        favorites = (ImageView)findViewById(R.id.favoritesImage);
        grocery = (ImageView)findViewById(R.id.groceryImage);

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

        String ingredients = ListToString(pantryList);

        List<Recipe> recipes = getRecipes(ingredients, "r");

        generateRecipes(recipes);
    }

    public String ListToString(ArrayList<String> list)
    {
        String st = "";
        for (String s : list)
        {
            if (s.contains(" "))
            {
                String newS = s.replace(" ", "%20");
                st += newS + ",";
            }
            else
            {
                st += s + ",";
            }
        }
        return st;
    }

    public List<Recipe> getRecipes(String ingredients, String sort)
    {
        List<String> recipeIDs;
        if (ingredients.isEmpty() || ingredients == null)
        {
            String searchResultall = Food2ForkInteract.searchRequest("", sort, "1");
            recipeIDs = Food2ForkInteract.ParseJsonRecipes(searchResultall);
            nullResultMsg = "No results match your ingredients. Popular recipes are shown below.";;
        }
        else
        {
            nullResultMsg = null;
            String searchResult = Food2ForkInteract.searchRequest(ingredients, sort, "1");
            recipeIDs = Food2ForkInteract.ParseJsonRecipes(searchResult);
        }
        List<String> recipeJsons = Food2ForkInteract.getRecipeRequest(recipeIDs);
        List<Recipe> recipes = Food2ForkInteract.parseJsonRecipe(recipeJsons);
        return recipes;
    }

    // only https works, not http
    public String reformatURL(String url)
    {
        String newUrl;
        if (url.contains("http") && !url.contains("https"))
        {
            newUrl = url.replace("http", "https");
            return newUrl;
        }
        else
        {
            return url;
        }
    }

    public void generateRecipes(List<Recipe> recipes)
    {
        if (recipes.size() != 0)
        {
            for (Recipe r : recipes)
            {
                final String rTitle = r.title;
                final String rURL = r.original_Url;
                final String[] ingredients = new String[r.ingredients.size()];
                for (int i = 0; i < r.ingredients.size(); i++)
                {
                    ingredients[i] = r.ingredients.get(i);
                }

                LinearLayout recipeLayout = new LinearLayout(LL.getContext());
                recipeLayout.setOrientation(LinearLayout.VERTICAL);

                if (nullResultMsg != null)
                {
                    TextView n = new TextView(recipeLayout.getContext());
                    n.setText(nullResultMsg);
                    n.setTextColor(Color.BLACK);
                    n.setTextSize(18);
                    n.setWidth(recipeLayout.getWidth());
                    n.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    recipeLayout.addView(n);
                    Space s = new Space(recipeLayout.getContext());
                    s.setMinimumHeight(100);
                    s.setMinimumWidth(recipeLayout.getWidth());
                    recipeLayout.addView(s);
                    nullResultMsg = null;
                }

                ImageView imageView = new ImageView(recipeLayout.getContext());
                final String imageUrl = reformatURL(r.image_Url);
                Picasso.with(recipeLayout.getContext()).load(imageUrl).resize(600, 500)
                        .into(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(), selected_recipe.class);
                        intent.putExtra("rTitle", rTitle);
                        intent.putExtra("rIngredients", ingredients);
                        intent.putExtra("rImgUrl", imageUrl);
                        intent.putExtra("rUrl", rURL);

                        startActivity(intent);
                    }
                });
                recipeLayout.addView(imageView);

                String recipeTitle = r.title;
                TextView t = new TextView(recipeLayout.getContext());
                t.setText(r.title);
                t.setTextColor(Color.BLACK);
                //t.setBackgroundColor(Color.WHITE);
                t.setTextSize(18);
                t.setWidth(300);
                t.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                t.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(), selected_recipe.class);
                        intent.putExtra("rTitle", rTitle);
                        intent.putExtra("rIngredients", ingredients);
                        intent.putExtra("rImgUrl", imageUrl);
                        intent.putExtra("rUrl", rURL);
                        startActivity(intent);
                    }
                });
                recipeLayout.addView(t);

                Space s = new Space(recipeLayout.getContext());
                s.setMinimumHeight(100);
                s.setMinimumWidth(recipeLayout.getWidth());
                recipeLayout.addView(s);

                LL.addView(recipeLayout);

            }
        }
    }

}
