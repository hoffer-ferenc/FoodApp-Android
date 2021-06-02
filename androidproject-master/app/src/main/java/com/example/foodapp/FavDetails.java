package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.foodapp.common.DBhelper;

public class FavDetails extends AppCompatActivity {
    private Button removeFavButton;
    private DBhelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_details);

        removeFavButton = findViewById(R.id.removeFavButton);
        DB = new DBhelper(this);
        Intent intent = getIntent();

        TextView titleTextView = findViewById(R.id.title_activity_FAVdetail);
        ImageView imageView = findViewById(R.id.image_activity_FAVdetail);

        TextView caloriesValueTextView = findViewById(R.id.caloriesValueTextViewFAV);
        TextView fatValueTextView = findViewById(R.id.fatValueTextViewFAV);
        TextView carbsValueTextView = findViewById(R.id.carbsValueTextViewFAV);
        TextView proteinValueTextView = findViewById(R.id.proteinValueTextViewFAV);

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

        removeFavButton.setOnClickListener(view -> {
            DB.deleteData(intent.getStringExtra("title"));
            Toast.makeText(FavDetails.this, "Removed from favorites!", Toast.LENGTH_SHORT).show();
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}