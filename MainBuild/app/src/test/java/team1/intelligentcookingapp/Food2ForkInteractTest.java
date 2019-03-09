package team1.intelligentcookingapp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Food2ForkInteractTest {
    @Test
    public void printResults()
    {
        String searchResult = Food2ForkInteract.searchRequest("chicken%20breast,bacon,", null, "1");
        List<String> recipeIDs = Food2ForkInteract.ParseJsonRecipes(searchResult);
        List<String> recipeJsons = Food2ForkInteract.getRecipeRequestTEST(recipeIDs);
        List<Recipe> recipes = Food2ForkInteract.parseJsonRecipe(recipeJsons);

        System.out.println("API Search request output:");
        System.out.println(searchResult);

        System.out.println("A recipe ID from parseJsonRecipes:");
        System.out.println(recipeIDs.get(0));

        System.out.println("API Get request for recipe:");
        System.out.println(recipeJsons.get(0));

        System.out.println("Recipe ingredients from Recipe object parsed by parseJsonRecipe()");
        for (String ingredient : recipes.get(0).ingredients)
        {
            System.out.println(ingredient);
        }
    }

}