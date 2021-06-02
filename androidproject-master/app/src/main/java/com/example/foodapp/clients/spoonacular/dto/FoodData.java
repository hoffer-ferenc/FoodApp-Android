package com.example.foodapp.clients.spoonacular.dto;

public class FoodData {
    private String imageUrl;
    private String title;
    private float calories_value;
    private float fat_value;
    private float carbohydrates_value;
    private float protein_value;
    public static boolean match = false;
    public static boolean deleted = false;

    public FoodData(String imageUrl, String title, float calories_value, float fat_value, float carbohydrates_value, float protein_value) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.calories_value = calories_value;
        this.fat_value = fat_value;
        this.carbohydrates_value = carbohydrates_value;
        this.protein_value = protein_value;
    }

    public FoodData() {
    }

    public static boolean getDeleted() {
        return deleted;
    }

    public static void setDeleted(boolean deleted) {
        FoodData.deleted = deleted;
    }

    public static boolean getMatch() {
        return match;
    }

    public static void setMatch(boolean match) {
        FoodData.match = match;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getCalories_value() {
        return calories_value;
    }

    public void setCalories_value(int calories_value) {
        this.calories_value = calories_value;
    }

    public float getFat_value() {
        return fat_value;
    }

    public void setFat_value(int fat_value) {
        this.fat_value = fat_value;
    }

    public float getCarbohydrates_value() {
        return carbohydrates_value;
    }

    public void setCarbohydrates_value(int carbohydrates_value) {
        this.carbohydrates_value = carbohydrates_value;
    }

    public float getProtein_value() {
        return protein_value;
    }

    public void setProtein_value(int protein_value) {
        this.protein_value = protein_value;
    }

    @Override
    public String toString() {
        return "FoodData{" +
                "imageUrl='" + imageUrl + '\'' +
                ", title='" + title + '\'' +
                ", calories_value=" + calories_value +
                ", fat_value=" + fat_value +
                ", carbohydrates_value=" + carbohydrates_value +
                ", protein_value=" + protein_value +
                '}';
    }
}
