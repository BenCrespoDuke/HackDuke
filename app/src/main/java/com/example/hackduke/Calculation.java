package com.example.hackduke;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Calculation {
    HashMap<String[], Integer> valueMap = new HashMap<>();//List of foods in group and kg/kgCo2
    private String name;
    private double servSize;
    private String group;
    private double co2g;
    public Calculation(String n, double s) {
        valueMap.put(new String[]{"Beef","hamburger", "steak"}, 5);
        valueMap.put(new String[]{"Eggs","egg"}, 4);
        valueMap.put(new String[]{"Lamb"}, 3);
        valueMap.put(new String[]{"Pork","bacon"}, 2);
        valueMap.put(new String[]{"Chicken"}, 1);
        name = n;
        servSize = s;
    }
    public void calculate() {
        for(String[] array: valueMap.keySet()) {
            List<String> list = Arrays.asList(array);
            if(list.contains(name)) {
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
