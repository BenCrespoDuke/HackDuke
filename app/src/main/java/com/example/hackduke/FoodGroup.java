package com.example.hackduke;

public class FoodGroup {
    String[] foodList;
    String foodType;
    Double co2lb;
    String name;
    public FoodGroup() {
        foodList = null;
        foodType = "";
        co2lb = 0.0;
        name = "";
    }
    public FoodGroup(String o) {
        foodList = null;
        foodType = "";
        co2lb = 0.0;
        name = o;
    }
    public FoodGroup(String ft, String n, String[] a, Double c) {
        foodList = a;
        foodType = ft;
        co2lb = c;
        name = n;
    }

    public Double getCo2lb() {
        return co2lb;
    }

    public String getFoodType() {
        return foodType;
    }

    public String getName() {
        return name;
    }

    public String[] getFoodList() {
        return foodList;
    }
}
