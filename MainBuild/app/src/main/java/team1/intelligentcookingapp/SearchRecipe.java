package team1.intelligentcookingapp;

import com.google.gson.annotations.SerializedName;

/*
    This class is used in conjunction with SearchResults.java
    each object in the List<SearchRecipe> Results contains these variables
    This data is collected so the @recipeID of each search result can be used
    to retrieve recipe information from the API to display to the user
 */
class SearchRecipe {
    @SerializedName("publisher")
    String publisher;

    @SerializedName("f2f_url")
    String f2fUrl;

    @SerializedName("title")
    String title;

    @SerializedName("source_url")
    String sourceURL;

    @SerializedName("recipe_id")
    String recipeId;

    @SerializedName("image_url")
    String imageUrl;

    @SerializedName("social_rank")
    String socialRank;

    @SerializedName("publisher_url")
    String publisherUrl;
}
