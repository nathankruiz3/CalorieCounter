package com.nathankruiz3.calcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nathankruiz3.calcounter.Data.DatabaseHandler;
import com.nathankruiz3.calcounter.Model.Food;

public class MainActivity extends AppCompatActivity {

    private EditText foodName, foodCals;
    private Button submitButton, skipButton;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(MainActivity.this);

        foodName = (EditText) findViewById(R.id.foodEditText);
        foodCals = (EditText) findViewById(R.id.caloriesEditText);
        submitButton = (Button) findViewById(R.id.submitButton);
        skipButton = (Button) findViewById(R.id.skipButton);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DisplayFoods.class));
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveDataToDB();

            }
        });
    }

    private void saveDataToDB() {
        Food food = new Food();

        String name = foodName.getText().toString().trim();
        String calsString = foodCals.getText().toString().trim();

        int cals = Integer.parseInt(calsString);

        if (name.equals("") || calsString.equals("")) {
            Toast.makeText(getApplicationContext(), "No empty fields allowed", Toast.LENGTH_SHORT).show();
        } else {
            food.setFoodName(name);
            food.setCalories(cals);

            db.addFood(food);
            db.close();

            // Clear the fields
            foodName.setText("");
            foodCals.setText("");

            // Take users to next screen
            startActivity(new Intent(MainActivity.this, DisplayFoods.class));
        }

    }
}