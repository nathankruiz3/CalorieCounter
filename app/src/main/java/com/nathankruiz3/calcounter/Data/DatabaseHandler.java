package com.nathankruiz3.calcounter.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nathankruiz3.calcounter.Model.Food;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final ArrayList<Food> foodList = new ArrayList<>();

    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "(" +
                Constants.KEY_ID + " INTEGER PRIMARY KEY, " + Constants.FOOD_NAME +
                " TEXT, " + Constants.FOOD_CALORIES_NAME + " INT, " + Constants.DATE_NAME +
                " LONG);";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    // Get total items saved
    public int getTotalItems() {
        int totalItems = 0;

        String query = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        totalItems = cursor.getCount();
        cursor.close();

        return totalItems;
    }

    // Get total calories consumed
    public int getTotalCalories() {
        int calories = 0;

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT SUM( " + Constants.FOOD_CALORIES_NAME + " ) "
                + "FROM " + Constants.TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            calories = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return calories;
    }

    // Delete food item
    public void deleteFood(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + " = ?",
                new String[] {String.valueOf(id)});
        db.close();
    }

    // Add content to db - add food
    public void addFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.FOOD_NAME, food.getFoodName());
        values.put(Constants.FOOD_CALORIES_NAME, food.getCalories());
        values.put(Constants.DATE_NAME, System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME,null,values);

        Log.v("Added Food Item: " + food.getFoodID(), "Yesss!!!");

        db.close();
    }

    // Get all foods
    public ArrayList<Food> getFoods() {

        foodList.clear();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {
                Constants.KEY_ID, Constants.FOOD_NAME, Constants.FOOD_CALORIES_NAME,
                Constants.DATE_NAME}, null, null, null, null,
                Constants.DATE_NAME + " DESC");

        // Loop through
        if (cursor.moveToFirst()) {
            do {

                Food food = new Food();

                food.setFoodName(cursor.getString(cursor.getColumnIndex(Constants.FOOD_NAME)));
                food.setCalories(cursor.getInt(cursor.getColumnIndex(Constants.FOOD_CALORIES_NAME)));
                food.setFoodID(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String date = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME))).getTime());

                food.setRecordDate(date);

                foodList.add(food);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return foodList;
    }
}
