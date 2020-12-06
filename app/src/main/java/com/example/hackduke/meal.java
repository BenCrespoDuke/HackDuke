package com.example.hackduke;

import java.util.Map;

public class meal{

public String foodStuff;
public double time;
public double carbon;
public String date;
public String Uid;

public Map<String,Object> MealProps;
    public meal(){

    }
    public meal(Map<String,Object> map){
        MealProps = map;
    }

    public Map<String,Object> getMealDatat(){
        return MealProps;
    }



}