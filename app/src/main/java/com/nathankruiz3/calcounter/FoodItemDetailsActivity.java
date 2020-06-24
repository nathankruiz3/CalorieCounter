package com.nathankruiz3.calcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nathankruiz3.calcounter.Data.DatabaseHandler;
import com.nathankruiz3.calcounter.Model.Food;

import org.w3c.dom.Text;

public class FoodItemDetailsActivity extends AppCompatActivity {

    private TextView detsFoodName, detsCaloriesTotal, detsDateText;
    private Button shareButton, deleteButton;
    private int foodID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item_details);

        detsFoodName = (TextView) findViewById(R.id.detsFoodName);
        detsCaloriesTotal = (TextView) findViewById(R.id.detsCaloriesTotal);
        detsDateText = (TextView) findViewById(R.id.detsDateText);
        shareButton = (Button) findViewById(R.id.detsShareButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        Food food = (Food) getIntent().getSerializableExtra("userObj");

        detsFoodName.setText(food.getFoodName());
        detsCaloriesTotal.setText(String.valueOf(food.getCalories()));
        detsDateText.setText("Consumed on.. " + food.getRecordDate());
        foodID = food.getFoodID();

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareCals();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FoodItemDetailsActivity.this);
                builder.setTitle("Delete?");
                builder.setMessage("Are you sure you want to delete this item?");
                builder.setNegativeButton("No", null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        db.deleteFood(foodID);
                        Toast.makeText(getApplicationContext(), "Food Item Deleted!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(FoodItemDetailsActivity.this, DisplayFoods.class));

                        FoodItemDetailsActivity.this.finish();
                    }
                });
                builder.show();
            }
        });
    }

    public void shareCals() {

        StringBuilder dataString = new StringBuilder();
        String name = detsFoodName.getText().toString();
        String cals = detsCaloriesTotal.getText().toString();
        String date = detsDateText.getText().toString();

        dataString.append(" Food: " + name + "\n");
        dataString.append(" Calories: " + cals + "\n");
        dataString.append(date);

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_SUBJECT, "My Caloric Intake");
        i.putExtra(Intent.EXTRA_EMAIL, new String[] {"nathankruiz3@gmail.com"});
        i.putExtra(Intent.EXTRA_TEXT, dataString.toString());

        try{
            startActivity(Intent.createChooser(i, "Send mail..."));
        }catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Please install email client before sending", Toast.LENGTH_SHORT).show();
        }
    }
}