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
import com.example.foodapp.Details;
import com.example.foodapp.FoodListFragment;
import com.example.foodapp.R;
import com.example.foodapp.clients.spoonacular.dto.FoodNutrientItem;
import com.example.foodapp.clients.spoonacular.dto.FoodResult;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder> {

    public FoodListFragment fragment;
    public List<FoodResult> foodList;
    public Context context;
    public static OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public static void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public FoodAdapter(FoodListFragment fragment, List<FoodResult> foodList) {
        this.fragment = fragment;
        this.foodList = foodList;
    }

    public FoodAdapter(Context context, List<FoodResult> foodList) {
        this.context = context;
        this.foodList = foodList;
    }
    public List<FoodResult> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<FoodResult> foodList) {
        this.foodList = foodList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list_row, parent,false);
        return new FoodHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
        holder.title.setText(foodList.get(position).title);

        Glide.with(context)
                .load(foodList.get(position).imageUrl)
                .into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            Intent detailintent = new Intent(context, Details.class);
            FoodResult selectedFood = foodList.get(position);

            detailintent.putExtra("imageUrl", selectedFood.imageUrl);
            detailintent.putExtra("title", selectedFood.title);

            detailintent.putExtra("calories_value", getNutrition(selectedFood, "Calories"));
            detailintent.putExtra("fat_value", getNutrition(selectedFood, "Fat"));
            detailintent.putExtra("carbohydrates_value", getNutrition(selectedFood, "Carbohydrates"));
            detailintent.putExtra("protein_value", getNutrition(selectedFood, "Protein"));

            context.startActivity(detailintent);
        });
    }

    public float getNutrition(FoodResult item, String name) {
        List<FoodNutrientItem> nutrientItem = item.nutrition.nutrients;
        float amount = 0;
        for(int i = 0; i < nutrientItem.size(); i++) {
            if (nutrientItem.get(i).name.equals(name)) {
                amount = item.nutrition.nutrients.get(i).amount;
            }
        }
        return amount;
    }

    @Override
    public int getItemCount() {
        if(foodList != null) {
            return foodList.size();
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
