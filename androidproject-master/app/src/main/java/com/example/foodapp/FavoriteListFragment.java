package com.example.foodapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.clients.spoonacular.dto.FoodData;
import com.example.foodapp.common.DBhelper;
import com.example.foodapp.common.FoodAdapterFavorite;

import java.util.List;

public class FavoriteListFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<FoodData> foodList;
    private DBhelper DB;

    public FavoriteListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favorite_list, container, false);

        DB = new DBhelper(getContext());
        foodList = DB.getAllData();
        recyclerView = rootView.findViewById(R.id.favFoodRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new FoodAdapterFavorite(getActivity(), foodList));

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        DB = new DBhelper(getContext());
        foodList = DB.getAllData();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new FoodAdapterFavorite(getActivity(), foodList));
    }
}