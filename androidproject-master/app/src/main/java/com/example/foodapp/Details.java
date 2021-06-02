package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodapp.clients.spoonacular.dto.FoodData;
import com.example.foodapp.common.DBhelper;

import java.util.List;

public class Details extends AppCompatActivity {
    private Button addFavButton;
    private DBhelper DB;
    private List<FoodData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        FoodData.setMatch(false);
        addFavButton = findViewById(R.id.addFavButton);
        DB = new DBhelper(this);

        Intent intent = getIntent();
        TextView titleTextView = findViewById(R.id.title_activity_detail);
        ImageView imageView = findViewById(R.id.image_activity_detail);

        TextView caloriesValueTextView = findViewById(R.id.caloriesValueTextView);
        TextView fatValueTextView = findViewById(R.id.fatValueTextView);
        TextView carbsValueTextView = findViewById(R.id.carbsValueTextView);
        TextView proteinValueTextView = findViewById(R.id.proteinValueTextView);

        Glide.with(this)
                .load(intent.getStringExtra("imageUrl"))
                .into(imageView);

        titleTextView.setText(intent.getStringExtra("title"));

        caloriesValueTextView.setText(
                String.valueOf(intent.getFloatExtra("calories_value", 0)));

        fatValueTextView.setText(
                String.valueOf(intent.getFloatExtra("fat_value", 0)));

        carbsValueTextView.setText(
                String.valueOf(intent.getFloatExtra("carbohydrates_value", 0)));

        proteinValueTextView.setText(
                String.valueOf(intent.getFloatExtra("protein_value", 0)));

        addFavButton.setOnClickListener(view -> {
            list = loadFoodData();
            if(list == null) {
                dbInsertFavorites(DB, intent);
            }
            if(list != null) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getTitle().equals(intent.getStringExtra("title"))) {
                        FoodData.setMatch(true);
                    }
                }
                if (FoodData.getMatch() == false) {
                    dbInsertFavorites(DB, intent);
                    Toast.makeText(Details.this, "Added to favorites!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Details.this, "Item is already on the favorites list!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void dbInsertFavorites(DBhelper DB, Intent intent) {
        DB.InsertFavorites(
                intent.getStringExtra("imageUrl"),
                intent.getStringExtra("title"),
                intent.getFloatExtra("calories_value", 0),
                intent.getFloatExtra("fat_value", 0),
                intent.getFloatExtra("carbohydrates_value", 0),
                intent.getFloatExtra("protein_value", 0)
        );
    }

    public List<FoodData> loadFoodData() {
        List<FoodData> list = DB.getAllData();
        return list;
    }
}
