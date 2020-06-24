package com.nathankruiz3.calcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.nathankruiz3.calcounter.Data.CustomListViewAdapter;
import com.nathankruiz3.calcounter.Data.DatabaseHandler;
import com.nathankruiz3.calcounter.Model.Food;
import com.nathankruiz3.calcounter.Util.Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DisplayFoods extends AppCompatActivity {

    private DatabaseHandler db;
    private ArrayList<Food> dbFoods = new ArrayList<>();
    private CustomListViewAdapter foodAdapter;
    private ListView listView;
    private Food myFood;
    private TextView totalCals, totalFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_foods);

        listView = (ListView) findViewById(R.id.list);
        totalCals = (TextView) findViewById(R.id.totalAmountTextView);
        totalFoods = (TextView) findViewById(R.id.totalItemsTextView);

        refreshData();

    }

    private void refreshData() {
        dbFoods.clear();

        db = new DatabaseHandler(getApplicationContext());

        ArrayList<Food> foodsFromDB = db.getFoods();

        int calsValue = db.getTotalCalories();
        int totalItems = db.getTotalItems();

        String formattedValue = Utils.formatNumber(calsValue);
        String formattedItems = Utils.formatNumber(totalItems);

        totalCals.setText("Total Calories: " + formattedValue);
        totalFoods.setText("Total Foods: " + formattedItems);

        for (int i=0; i<foodsFromDB.size(); i++) {

            String name = foodsFromDB.get(i).getFoodName();
            String dateText = foodsFromDB.get(i).getRecordDate();
            int cals = foodsFromDB.get(i).getCalories();
            int foodID = foodsFromDB.get(i).getFoodID();

            Log.v("Food IDS: ", String.valueOf(foodID));

            myFood = new Food();
            myFood.setFoodName(name);
            myFood.setCalories(cals);
            myFood.setRecordDate(dateText);
            myFood.setFoodID(foodID);

            dbFoods.add(myFood);
        }
        db.close();

        // Setup adapter
        foodAdapter = new CustomListViewAdapter(DisplayFoods.this, R.layout.list_row, dbFoods);
        listView.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();
    }
}