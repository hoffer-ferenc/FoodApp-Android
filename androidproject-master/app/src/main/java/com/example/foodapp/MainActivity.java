package com.example.foodapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private final Fragment fragment_home = new FoodListFragment();
    private final Fragment fragment_fav = new FavoriteListFragment();
    private final FragmentManager fm = getSupportFragmentManager();
    private Fragment active = fragment_home;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        BottomNavigationView bottomNav = findViewById(R.id.main_toolbar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        fm.beginTransaction().add(R.id.fragmentContainer, fragment_fav, "favorites")
                .hide(fragment_fav).commit();

        fm.beginTransaction().add(R.id.fragmentContainer, fragment_home, "home").commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        switch (item.getItemId()) {
            case R.id.action_home:
                fm.beginTransaction().hide(active).show(fragment_home).commit();
                active = fragment_home;
                return true;
            case R.id.action_favorites:
                hideSoftKeyboard(activity);
                fm.beginTransaction().hide(active).show(fragment_fav).commit();
                active = fragment_fav;
                return true;
        }
        return false;
    };

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);

        if (activity.getCurrentFocus() != null)
        {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}