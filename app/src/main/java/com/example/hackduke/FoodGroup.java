package com.example.hackduke;

public class FoodGroup {
    String[] foodList;
    String foodType;
    Double co2lb;
    String name;
    public FoodGroup(String[] a, String ft, Double c, String n) {
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
