package com.example.hackduke;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculation {
   ArrayList<FoodGroup> groupList = new ArrayList<>();
    private String name;
    private double servSize;
    private String group;
    private double co2g;
    public Calculation(String n, double s) {
        FoodGroup lamb = new FoodGroup("Meat","Lamb",new String[]{"lamb", "mutton", }, 39.2);
        FoodGroup beef = new FoodGroup("Meat","Beef",new String[]{"beef", "hamburger", "steak", "meatball", "meatloaf","prime rib", "roast beef", "beef stew", "cheeseburger", "beef wellington", "beef stroganoff" }, 27.0);
        FoodGroup cheese = new FoodGroup("Dairy","Cheese",new String[]{"cheese"}, 13.5);
        FoodGroup bacon = new FoodGroup("Meat","Pork",new String[]{"pork","bacon"}, 12.1);
        FoodGroup salmon = new FoodGroup("Fish","Salmon",new String[]{"salmon"}, 11.9);
        FoodGroup turkey = new FoodGroup("Poultry","Turkey",new String[]{"turkey"}, 10.9);
        FoodGroup chicken = new FoodGroup("Poultry","Chicken",new String[]{"chicken"}, 6.9);
        FoodGroup tuna = new FoodGroup("Fish","Tuna",new String[]{"tuna"}, 6.1);
        FoodGroup eggs = new FoodGroup("Beans","Eggs",new String[]{"eggs","egg", "hard boiled egg", "scrambled eggs", "fried egg", "deviled egg", "omelette", "balut"}, 4.8);
        FoodGroup potatoes = new FoodGroup("Carbs","Potatoes",new String[]{"potatoes"}, 2.9);
        FoodGroup rice = new FoodGroup("Carbs","Rice",new String[]{"rice"}, 2.7);
        FoodGroup peanut_butter = new FoodGroup("Nut","Peanut Butter",new String[]{"peanut butter"}, 2.5);
        FoodGroup nuts = new FoodGroup("Nut","Nuts",new String[]{"nuts"}, 2.3);
        FoodGroup yogurt = new FoodGroup("Dairy","Yogurt",new String[]{"yogurt"}, 2.2);
        FoodGroup broccoli = new FoodGroup("Vegetable","Broccoli",new String[]{"broccoli"}, 2.0);
        FoodGroup tofu = new FoodGroup("Beans","Tofu",new String[]{"tofu"}, 2.0);
        FoodGroup beans = new FoodGroup("Beans","Beans",new String[]{"beans"}, 2.0);
        FoodGroup milk = new FoodGroup("Dairy","Milk",new String[]{"milk"}, 1.9);
        FoodGroup tomatoes = new FoodGroup("Vegetables","Tomatoes",new String[]{"tomatoes"}, 1.1);
        FoodGroup lentils = new FoodGroup("Beans","Lentils",new String[]{"lentils"}, 0.9);
        groupList.add(lamb);
        groupList.add(beef);
        groupList.add(cheese);
        groupList.add(bacon);
        groupList.add(salmon);
        groupList.add(turkey);
        groupList.add(chicken);
        groupList.add(tuna);
        groupList.add(eggs);
        groupList.add(potatoes);
        groupList.add(rice);
        groupList.add(peanut_butter);
        groupList.add(nuts);
        groupList.add(yogurt);
        groupList.add(broccoli);
        groupList.add(tofu);
        groupList.add(beans);
        groupList.add(milk);
        groupList.add(tomatoes);
        groupList.add(lentils);
        name = n;
        servSize = s;
    }
    public void calculate() {
        for(FoodGroup g: groupList) {
            List<String> list = Arrays.asList(g.getFoodList());
            if(list.contains(name.toLowerCase())) {
                co2g = servSize * g.getCo2lb();
                group = g.getName();
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
