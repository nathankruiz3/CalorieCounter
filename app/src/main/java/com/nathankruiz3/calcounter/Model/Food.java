package com.nathankruiz3.calcounter.Model;

import java.io.Serializable;

public class Food implements Serializable {
    private static final long serialVersionUID = 10L;
    private String foodName, recordDate;
    private int calories, foodID;

    public Food(String foodName, String recordDate, int calories, int foodID) {
        this.foodName = foodName;
        this.recordDate = recordDate;
        this.calories = calories;
        this.foodID = foodID;
    }

    public Food() {

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }
}
