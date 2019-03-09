package team1.intelligentcookingapp;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;

class Food2ForkInteract {

    private static final String API_URL_BASE = "https://food2fork.com/api/";
    private static final String API_KEY = "3b76109effcf73fef0a1fac36a52cb3d";

    /*
    This method calls the API and retrieves recipe data for those that
    match with the list of ingredients and sort method
    returns data for 30 recipes max
     */
    static String searchRequest(String ingredients, String sort, String page)
    {
        String searchUrl = API_URL_BASE + "search?key=" + API_KEY + "&q=" + ingredients + "&sort=" + sort +
                "&page=" + page;
        StringBuilder response = new StringBuilder();

        try {
            URL obj = new URL(searchUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            // add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 +" +
                    "(KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393");

            int responseCode = con.getResponseCode();
            if(responseCode != 200)
            {
                return null;
            }
            else
            {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }
            return response.toString();
        }
        catch(Exception ex)
        {
            System.out.print(ex.getMessage());
        }
        return null;
    }

    /*
    This method parses the JSON result from searchRequest
    returns list of recipeIDs of each recipe found
     */
    static List<String> ParseJsonRecipes(String json)
    {
        List<String> recipeIdList = new ArrayList<String>();
        Gson gson = new Gson();
        if (json != null)
        {
            try
            {
                SearchResults recipes = gson.fromJson(json, SearchResults.class);
                List<SearchRecipe> results = recipes.results;

                for (SearchRecipe result : results) {
                    recipeIdList.add(result.recipeId);
                }
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
            return recipeIdList;
        }
        else
        {
            return Collections.emptyList();
        }
    }

    /*
    This method calls the API and retrieves recipe data for each
    recipeID found from searchRequest
     */
    static List<String> getRecipeRequest(List<String> ids)
    {
        // returns list of all Json get requests, 1 per recipe
        List<String> recipeJson = new ArrayList<String>();
        if (!ids.isEmpty())
        {
            //for (String id : ids)
            for (int i = 0; i < 3; i++)
            {
                    // Perform recipe get request
                    String searchUrl = API_URL_BASE + "get?key=" + API_KEY + "&rId=" + ids.get(i);
                    try {
                        URL obj = new URL(searchUrl);
                        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                        // optional default is GET
                        con.setRequestMethod("GET");

                        //add request header
                        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 +" +
                                "(KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393");

                        int responseCode = con.getResponseCode();
                        if (responseCode != 200) {
                            throw new RuntimeException("HttpResponseCode: " + responseCode);
                        } else {
                            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(con.getInputStream()));
                            String inputLine;
                            StringBuilder response = new StringBuilder();

                            while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                            }
                            in.close();
                            recipeJson.add(response.toString());
                        }
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex.getMessage());
                    }

            }
            return recipeJson;
        }
        else
        {
            return Collections.emptyList();
        }
    }

    /*
    This method parses the JSON result from getRecipeRequest
    returns list of recipe objects that contains all data for a recipe
    this list will be used to get the data to display to the user
     */
    static List<Recipe> parseJsonRecipe(List<String> jsonRecipes)
    {
        List<Recipe> recipes = new ArrayList<Recipe>();
        Gson gson = new Gson();
        if (!jsonRecipes.isEmpty())
        {
            try
            {
                for(String json : jsonRecipes)
                {
                    RecipeList r = gson.fromJson(json, RecipeList.class);
                    recipes.add(r.element);
                }
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
            return recipes;
        }
        else
        {
            return Collections.emptyList();
        }

    }

    /*
    Used in place of getRecipeRequest for testing
    only makes 1 API call to prevent maxing our limit of 250 API calls / day
     */
    static List<String> getRecipeRequestTEST(List<String> ids) {
        // returns list of all Json get requests, 1 per recipe
        List<String> recipeJson = new ArrayList<String>();
        if (!ids.isEmpty())
        {
            String id = ids.get(0);
            // Perform recipe get request
            String searchUrl = API_URL_BASE + "get?key=" + API_KEY + "&rId=" + id;
            try {
                URL obj = new URL(searchUrl);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                // optional default is GET
                con.setRequestMethod("GET");

                //add request header
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 +" +
                        "(KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393");

                int responseCode = con.getResponseCode();
                if (responseCode != 200) {
                    throw new RuntimeException("HttpResponseCode: " + responseCode);
                } else {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    recipeJson.add(response.toString());
                }
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            return recipeJson;
        }
        else
        {
            return Collections.emptyList();
        }
    }

}

