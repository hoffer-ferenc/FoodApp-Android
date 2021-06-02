package com.example.foodapp.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.foodapp.clients.spoonacular.dto.FoodData;


import java.util.ArrayList;


public class DBhelper extends SQLiteOpenHelper {
    public DBhelper(Context context) {
        super(context, "Favorites.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Favorites(imageUrl TEXT, title TEXT PRIMARY KEY, calories_value TEXT, fat_value TEXT, carbohydrates_value TEXT, protein_value TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists Favorites");
    }

    public Boolean InsertFavorites(String imageUrl, String title, float calories_value, float fat_value, float carbohydrates_value, float protein_value)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("imageUrl", imageUrl);
        contentValues.put("title", title);
        contentValues.put("calories_value", calories_value);
        contentValues.put("fat_value", fat_value);
        contentValues.put("carbohydrates_value", carbohydrates_value);
        contentValues.put("protein_value", protein_value);
        long result=DB.insert("Favorites", null, contentValues);
        if(result==-1) {
            return false;
        }else{
            return true;
        }
    }

    public ArrayList<FoodData> getAllData() {
        ArrayList<FoodData> arrayList = new ArrayList<>();
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Favorites", null);

        while(cursor.moveToNext()) {
            String imageUrl = cursor.getString(0);
            String title = cursor.getString(1);
            float calories_value = cursor.getInt(2);
            float fat_value = cursor.getInt(3);
            float carbohydrates_value = cursor.getInt(4);
            float protein_value = cursor.getInt(5);
            FoodData stat = new FoodData(imageUrl, title, calories_value, fat_value, carbohydrates_value, protein_value);
            arrayList.add(stat);
        }
        return arrayList;
    }
    public Cursor getData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM Favorites", null);
        return cursor;
    }
    public Boolean deleteData(String title) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Favorites where title = ?", new String[]{title});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Favorites", "title=?", new String[]{title});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}
