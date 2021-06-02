package com.example.foodapp.common;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.FavDetails;
import com.example.foodapp.FavoriteListFragment;
import com.example.foodapp.R;
import com.example.foodapp.clients.spoonacular.dto.FoodData;

import java.util.List;

public class FoodAdapterFavorite extends RecyclerView.Adapter<FoodAdapterFavorite.FoodHolder> {
    public List<FoodData> foodListData;
    public Context context;
    public static FoodAdapter.OnItemClickListener mListener;
    public FavoriteListFragment fragmentdata;

    public FoodAdapterFavorite(FavoriteListFragment fragmentdata, List<FoodData> foodListData) {
        this.fragmentdata = fragmentdata;
        this.foodListData = foodListData;
    }
    public FoodAdapterFavorite(Context context, List<FoodData> foodListData) {
        this.context = context;
        this.foodListData = foodListData;
    }

    @NonNull
    @Override
    public FoodAdapterFavorite.FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list_row, parent, false);
        return new FoodHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapterFavorite.FoodHolder holder, int position) {
        holder.title.setText(foodListData.get(position).getTitle());
        Glide.with(context)
                .load(foodListData.get(position).getImageUrl())
                .into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            Intent detailintent = new Intent(context, FavDetails.class);
            FoodData selectedFood = foodListData.get(position);

            detailintent.putExtra("imageUrl", selectedFood.getImageUrl());
            detailintent.putExtra("title", selectedFood.getTitle());

            detailintent.putExtra("calories_value", selectedFood.getCalories_value());
            detailintent.putExtra("fat_value", selectedFood.getFat_value());
            detailintent.putExtra("carbohydrates_value", selectedFood.getCarbohydrates_value());
            detailintent.putExtra("protein_value", selectedFood.getProtein_value());

            context.startActivity(detailintent);
        });
    }

    @Override
    public int getItemCount() {
        if(foodListData != null) {
            return foodListData.size();
        }
        return 0;
    }
    public class FoodHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;

        public FoodHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleTextView);
            image = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(v -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.OnItemClick(position);
                    }
                }
            });
        }
    }
}
