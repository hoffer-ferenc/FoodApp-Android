package com.example.foodapp.clients.spoonacular;

import com.example.foodapp.clients.spoonacular.dto.FoodResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodClient {
    String apiKeyValue = "fb103f76bba644c49ffec903616a8ee9";
    //String apiKeyValue = "d85e1baf55ba423aaa303b1c60b4d194";
    String apiKey = "apiKey=" + apiKeyValue;

    String apiKey_RecipeNutrition = apiKey + "&" + "addRecipeNutrition=true";

    @GET("complexSearch?" + apiKey_RecipeNutrition + "&" + "query=apple")
    Call<FoodResponse> searchForApple(@Query("number") int number, @Query("offset") int offset);

    @GET("complexSearch?" + apiKey_RecipeNutrition)
    Call<FoodResponse> searchByName(@Query("query") String query, @Query("number") int number, @Query("offset") int offset);
}
