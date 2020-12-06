package com.example.hackduke;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Calculation {
    ArrayList<FoodGroup> groupList = new ArrayList<>();
    private String name;
    private double servSize;
    private double co2;
    FoodGroup currentGroup = new FoodGroup();
    public Calculation(String n, double s) {
        FoodGroup lamb = new FoodGroup("Meat","Lamb",new String[]{"lamb", "mutton"}, 39.2);
        FoodGroup beef = new FoodGroup("Meat","Beef",new String[]{"beef", "hamburger", "steak", "meatball", "meatloaf","prime rib", "roast beef", "beef stew", "cheeseburger", "beef wellington", "beef stroganoff", "bulgogi", "brisket", "beef brisket"}, 27.0);
        FoodGroup cheese = new FoodGroup("Dairy","Cheese",new String[]{"cheese", "cheddar", "mozzarella", "feta", "provolone", "american cheese", "cream cheese", "swiss cheese", "blue cheese"}, 13.5);
        FoodGroup bacon = new FoodGroup("Meat","Pork",new String[]{"pork", "bacon", "ribs", "rib", "pulled pork", "praised pork", "pig", "smoked pork", "pork chops", "pork loins", "kebab"}, 12.1);
        FoodGroup salmon = new FoodGroup("Fish","Salmon",new String[]{"salmon", "grilled salmon", "smoked salmon", "fried salmon", "atlantic salmon", "baked salmon", "raw salmon"}, 11.9);
        FoodGroup turkey = new FoodGroup("Poultry","Turkey",new String[]{"turkey", "turkey wing", "turkey breast", "turkey leg", "grilled turkey", "roast turkey", "roasted turkey"}, 10.9);
        FoodGroup chicken = new FoodGroup("Poultry","Chicken",new String[]{"chicken", "chicken sandwich", "grilled chicken", "fried chicken", "chicken legs", "chicken feet", "chicken breast", "drumstick", "chicken thigh", "chicken wing", "chicken tenders", "chicken parmesan", "roasted chicken", "roast chicken"}, 6.9);
        FoodGroup tuna = new FoodGroup("Fish","Tuna",new String[]{"tuna", "canned tuna", "tuna sandwich", "tuna salad"}, 6.1);
        FoodGroup eggs = new FoodGroup("Beans","Eggs",new String[]{"eggs","egg", "hard boiled egg", "scrambled eggs", "fried egg", "deviled egg", "omelette", "balut", "omelet"}, 4.8);
        FoodGroup potatoes = new FoodGroup("Carbs","Potatoes",new String[]{"potatoes", "mashed potatoes", "baked potato", "potato", "french fries", "chips", "fires", "hash brown", "fries", "fry"}, 2.9);
        FoodGroup rice = new FoodGroup("Carbs","Rice",new String[]{"rice"}, 2.7);
        FoodGroup peanut_butter = new FoodGroup("Nut","Peanut Butter",new String[]{"peanut butter"}, 2.5);
        FoodGroup nuts = new FoodGroup("Nut","Nuts",new String[]{"nuts", "nut", "pecan", "walnut", "peanut", "almond", "hazelnut", "cashew", "chestnut", "macadamia", "pistachio", "sunflower seed"}, 2.3);
        FoodGroup yogurt = new FoodGroup("Dairy","Yogurt",new String[]{"yogurt"}, 2.2);
        FoodGroup tofu = new FoodGroup("Beans","Tofu",new String[]{"tofu"}, 2.0);
        FoodGroup beans = new FoodGroup("Beans","Beans",new String[]{"beans", "pinto beans", "red beans", "canned beans", "baked beans", "bean", "green beans", "pinto bean", "red bean", "canned bean", "baked bean", "green bean"}, 2.0);
        FoodGroup milk = new FoodGroup("Dairy","Milk",new String[]{"milk"}, 1.9);
        FoodGroup fish = new FoodGroup("Fish","Fish",new String[]{"fish", "fishes", "marlin", "cod", "fish stick", "fish sticks"}, 6.2);
        FoodGroup fruits = new FoodGroup("Fruits","Fruits",new String[]{"apple", "banana", "grape", "grapes", "orange", "berry", "strawberry", "blueberry", "strawberries", "blueberries", "coconut", "citrus", "lime", "lemon"}, 0.9);
        FoodGroup vegetables = new FoodGroup("Vegetables","Vegetables",new String[]{"lentils", "lentil", "broccoli", "tomatoes", "tomato", "avocado", "lettuce", "carrot", "pepper", "corn", "squash"}, 0.9);
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
        groupList.add(tofu);
        groupList.add(beans);
        groupList.add(milk);
        groupList.add(fish);
        groupList.add(fruits);
        groupList.add(vegetables);
        name = n;
        servSize = s;
    }
    public void calculate() {
        for(FoodGroup g: groupList) {
            List<String> list = Arrays.asList(g.getFoodList());
            if(list.contains(name.toLowerCase())) {
                co2 = servSize * g.getCo2lb();
                currentGroup = g;
                return;
            }
        }
        co2 = 0;
        currentGroup = new FoodGroup("Other");
    }
    public double getCo2() {
        return co2;
    }
    public FoodGroup getGroup() {
        return currentGroup;
    }
    public String getRec() {
        if(currentGroup.getCo2lb() < 5) {
            return "Congratulations, your choice of " +
                    name + " is in the " +
                    currentGroup.getName().toLowerCase() + " group which is sustainable!";
        }
        ArrayList<FoodGroup> sameType = new ArrayList<>();
        for(FoodGroup f: groupList) {
            if(f.getFoodType().equals(currentGroup.getFoodType())) {
                sameType.add(f);
            }
        }
        FoodGroup minGroup = sameType.get(0);
        for(FoodGroup f: sameType) {
            if(f.getCo2lb() < minGroup.getCo2lb()) {
                minGroup = f;
            }
        }
        int rnd = new Random().nextInt(minGroup.getFoodList().length);
        String dishRec = minGroup.getFoodList()[rnd];
        if(currentGroup.getCo2lb() < 15) {
            return "Your choice of " +
                    name + " is in the " +
                    currentGroup.getName().toLowerCase() + " group which is moderately sustainable. " +
                    "Consider more sustainable alternatives such as foods of " +
                    minGroup.getName().toLowerCase() + ", for example, " + dishRec + ".";
        }
        return  "Your choice of " +
                name + " is in the " +
                currentGroup.getName().toLowerCase() + " group which is very unsustainable. " +
                "Consider more sustainable alternatives such as foods of " +
                minGroup.getName().toLowerCase() + ", for example, " + dishRec + ".";


    }

    public String getName(){
        return name;
    }


}
