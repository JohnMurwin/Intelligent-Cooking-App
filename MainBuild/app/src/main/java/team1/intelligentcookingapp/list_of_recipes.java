package team1.intelligentcookingapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.os.StrictMode;
import android.widget.Space;
import android.widget.TextView;
import com.squareup.picasso.*;

public class list_of_recipes extends AppCompatActivity {
    LinearLayout LL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_recipes);

        // For some reason this makes the API connection work
        // move API work to a service in the future?
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        LL = (LinearLayout)findViewById(R.id.LinearLayout);

        List<String> pantryList = new ArrayList<>();
        pantryList.add("bacon");
        pantryList.add("chicken");
        pantryList.add("garlic");
        String ingredients = ListToString(pantryList);

        List<Recipe> recipes = getRecipes(ingredients, "r");

        Log.i("list_of_recipes", String.valueOf(recipes.size()));


        generateRecipes(recipes);
    }

    public String ListToString(List<String> list)
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
        String searchResult = Food2ForkInteract.searchRequest(ingredients, sort, "1");
        List<String> recipeIDs = Food2ForkInteract.ParseJsonRecipes(searchResult);
        if (recipeIDs.size() == 0)
        {
            String searchResultall = Food2ForkInteract.searchRequest("", sort, "1");
            recipeIDs = Food2ForkInteract.ParseJsonRecipes(searchResultall);

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
                ImageView imageView = new ImageView(recipeLayout.getContext());
                final String imageUrl = reformatURL(r.image_Url);
                Picasso.with(recipeLayout.getContext()).load(imageUrl).resize(400, 400)
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
                t.setTextSize(18);
                t.setWidth(recipeLayout.getWidth());
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
