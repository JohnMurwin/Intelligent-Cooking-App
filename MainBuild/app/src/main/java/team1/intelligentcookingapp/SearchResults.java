package team1.intelligentcookingapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/*
    This class will store the @count and list of recipe @results
    when an API call is made to search for recipes that include
    pantry ingredients
 */
class SearchResults {

    @SerializedName("count")
    String count;

    @SerializedName("recipes")
    List<SearchRecipe> results;

}
