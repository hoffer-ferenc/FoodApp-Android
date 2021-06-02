package com.example.foodapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.clients.spoonacular.FoodAppService;
import com.example.foodapp.clients.spoonacular.FoodClient;
import com.example.foodapp.clients.spoonacular.dto.FoodResponse;
import com.example.foodapp.clients.spoonacular.dto.FoodResult;
import com.example.foodapp.common.FoodAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodListFragment extends Fragment implements FoodAdapter.OnItemClickListener {
    public FoodListFragment() {
        // Required empty public constructor
    }

    public static FoodListFragment newInstance() {
        FoodListFragment fragment = new FoodListFragment();
        return fragment;
    }

    private EditText searchEditText;
    private Button searchButton;
    private FoodAppService foodAppService;

    private RecyclerView recyclerView;
    private List<FoodResult> foodList;
    private Activity activity;

    private int searchNumber = 10;
    private int searchOffset = 0;

    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_food_list, container, false);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.spoonacular.com/recipes/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            FoodClient client = retrofit.create(FoodClient.class);

            Call<FoodResponse> call = client.searchForApple(searchNumber, searchOffset);

            call.enqueue(new Callback<FoodResponse>() {
                @Override
                public void onResponse(Call<FoodResponse> call, Response<FoodResponse> response) {
                    if (response.isSuccessful()) {
                        foodList = response.body().results;
                        recyclerView.setAdapter(new FoodAdapter(getActivity(), foodList));
                        FoodAdapter.setOnItemClickListener(FoodListFragment.newInstance());
                    }
                    else {
                        Toast.makeText(getActivity(), "Status code: " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<FoodResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            activity = getActivity();

            foodAppService = new FoodAppService();

            searchEditText = rootView.findViewById(R.id.searchEditText);
            searchButton = rootView.findViewById(R.id.searchButton);

            searchButton.setOnClickListener(v -> {
                String query = searchEditText.getText().toString();
                foodAppService.searchByName(query, searchNumber, searchOffset, new Callback<FoodResponse>() {
                    @Override
                    public void onResponse(Call<FoodResponse> call, Response<FoodResponse> response) {
                        MainActivity.hideSoftKeyboard(activity);

                        if (response.isSuccessful()) {
                            foodList = response.body().results;
                            recyclerView.setAdapter(new FoodAdapter(getActivity(), foodList));
                            FoodAdapter.setOnItemClickListener(FoodListFragment.this);
                        }
                        else {
                            Toast.makeText(getActivity(), "Status code: " + response.code(), Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<FoodResponse> call, Throwable t) {

                    }
                });
            });

            recyclerView = rootView.findViewById(R.id.foodRecyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            recyclerView.setAdapter(new FoodAdapter(this, foodList));
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        if (rootView.getParent() != null) {
            ((ViewGroup)rootView.getParent()).removeView(rootView);
        }
        super.onDestroyView();
    }

    @Override
    public void OnItemClick(int position) {

    }
}
