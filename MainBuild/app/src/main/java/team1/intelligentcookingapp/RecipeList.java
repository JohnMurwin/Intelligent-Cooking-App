package team1.intelligentcookingapp;

import com.google.gson.annotations.SerializedName;

/*
    This class will store a recipe object @element
    when an API get call is made with a recipe ID to retrieve recipe data
    all data for a recipe object can be found in Recipe.java
 */
class RecipeList {

    @SerializedName("recipe")
    Recipe element;
}
