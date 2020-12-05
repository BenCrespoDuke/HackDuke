package com.example.hackduke;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Calculation {
    HashMap<String[], Double> valueMap = new HashMap<>();//List of foods in group and kg/kgCo2
    private String name;
    private double servSize;
    private String group;
    private double co2g;
    public Calculation(String n, double s) {
        valueMap.put(new String[]{"Lamb", "lamb", "mutton", }, 39.2);
        valueMap.put(new String[]{"Beef","beef", "hamburger", "steak", "meatball", "meatloaf","prime rib", "roast beef", "beef stew", "cheeseburger", "beef wellington", "beef stroganoff" }, 27.0);
        valueMap.put(new String[]{"Cheese", "cheese"}, 13.5);
        valueMap.put(new String[]{"Pork","bacon"}, 12.1);
        valueMap.put(new String[]{"Salmon", "salmon"}, 11.9);
        valueMap.put(new String[]{"Turkey", "turkey"}, 10.9);
        valueMap.put(new String[]{"Chicken", "chicken"}, 6.9);
        valueMap.put(new String[]{"Tuna", "tuna"}, 6.1);
        valueMap.put(new String[]{"Eggs","eggs","egg", "hard boiled egg", "scrambled eggs", "fried egg", "deviled egg", "omelette", "balut"}, 4.8);
        valueMap.put(new String[]{"Potatoes", "potatoes"}, 2.9);
        valueMap.put(new String[]{"Rice", "rice"}, 2.7);
        valueMap.put(new String[]{"Peanut Butter", "peanut butter"}, 2.5);
        valueMap.put(new String[]{"Nuts", "nuts"}, 2.3);
        valueMap.put(new String[]{"Yogurt", "yogurt"}, 2.2);
        valueMap.put(new String[]{"Broccoli", "broccoli"}, 2.0);
        valueMap.put(new String[]{"Tofu", "tofu"}, 2.0);
        valueMap.put(new String[]{"Beans", "beans"}, 2.0);
        valueMap.put(new String[]{"Milk", "milk"}, 1.9);
        valueMap.put(new String[]{"Tomatoes", "tomatoes"}, 1.1);
        valueMap.put(new String[]{"Lentils", "lentils"}, 0.9);

        name = n;
        servSize = s;
    }
    public void calculate() {
        for(String[] array: valueMap.keySet()) {
            List<String> list = Arrays.asList(array);
            if(list.contains(name.toLowerCase())) {
                co2g = servSize * valueMap.get(array);
                group = list.get(0);
                return;
            }
        }
        co2g = 0;
        group = "Other";
    }
    public double getCo2() {
        return co2g;
    }
    public String getGroup() {
        return group;
    }


}
