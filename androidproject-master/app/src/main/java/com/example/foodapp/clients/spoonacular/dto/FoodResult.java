package com.example.foodapp.clients.spoonacular.dto;

import com.google.gson.annotations.SerializedName;

public class FoodResult {
    public long id;
    public String title;
    @SerializedName("image")
    public String imageUrl;
    public FoodNutrition nutrition;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public FoodNutrition getNutrition() {
        return nutrition;
    }
}
