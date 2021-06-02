package com.example.foodapp.clients.spoonacular;

import com.example.foodapp.clients.spoonacular.dto.FoodResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodAppService {
    private final String BASE_URL = "https://api.spoonacular.com/recipes/";
    private final Retrofit retrofit;

    public FoodAppService() {
        retrofit = buildRetrofit();
    }

    public void searchForApple(int number, int offset, Callback<FoodResponse> callback) {
        Call<FoodResponse> call = retrofit.create(FoodClient.class).searchForApple(number, offset);
        call.enqueue(callback);
    }

    public void searchByName(String query, int number, int offset, Callback<FoodResponse> callback) {
        Call<FoodResponse> call = retrofit.create(FoodClient.class).searchByName(query, number, offset);
        call.enqueue(callback);
    }

    private Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
