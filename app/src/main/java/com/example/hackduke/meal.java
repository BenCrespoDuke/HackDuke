package com.example.hackduke;

import java.util.Map;

public class meal{

public String foodStuff;
public double time;
public double carbon;
public String date;
public String Uid;


    public meal(){

    }
    public meal(Map<String,Object> map){
        if (map.containsKey("Food Stuff")){
            foodStuff =(String) map.get("foodStuff");
        }
        if (map.containsKey("date")){
            date =(String) map.get("date");
        }
        if (map.containsKey("time")){
            time =(double) map.get("time");
        }
        if (map.containsKey("carbon")){
            carbon =(double) map.get("carbon");
        }
        if (map.containsKey("Uid")){
            Uid =(String) map.get("Uid");
        }
    }



}