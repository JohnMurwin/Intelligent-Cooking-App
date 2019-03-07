package team1.intelligentcookingapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/*
    This class is used in conjunction with RecipeList.java
    each object in element contains these variables
    This data is collected so recipes can be shown to the user
 */
class Recipe {

    @SerializedName("publisher")
    String publisher;

    @SerializedName("f2f_url")
    String f2f_Url;

    @SerializedName("ingredients")
    List<String> ingredients;

    @SerializedName("source_url")
    String original_Url;

    @SerializedName("recipe_id")
    String rId;

    @SerializedName("image_url")
    String image_Url;

    @SerializedName("social_rank")
    String socialRank;

    @SerializedName("publisher_url")
    String publisher_Url;

    @SerializedName("title")
    String title;
}
