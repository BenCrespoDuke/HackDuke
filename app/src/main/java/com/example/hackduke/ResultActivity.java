package com.example.hackduke;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class ResultActivity extends AppCompatActivity {
    private Button cam_button;
    private double size = 1.0;
    private EditText input;
    private AlertDialog.Builder alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        boolean isValidInput = false;
        while(isValidInput == false) {
            alert = new AlertDialog.Builder(this);
            alert.setMessage("Estimated Amount of Food(lbs)");

            input = new EditText(this);
            String text = input.getText().toString();
            boolean digitsOnly = TextUtils.isDigitsOnly(text);
            if(digitsOnly == true) {
                isValidInput = true;
            }
            else {
                alert.setMessage("NOOOO");
            }
        }

        alert.setView(input);

        alert.setPositiveButton("Ok", (dialog, whichButton) -> {
            ConstraintLayout w = findViewById(R.id.whole);
            w.setVisibility(View.VISIBLE);
            size = Double.parseDouble(input.getText().toString());
            Bundle extras = getIntent().getExtras();
            ArrayList<String> predictList = extras.getStringArrayList("data");
            Intent intent = getIntent();
            Bitmap bitmap = (Bitmap) intent.getParcelableExtra("bit");
            String name = searchFoodList(predictList);
            Calculation calc = new Calculation(name,size);
            calc.calculate();

            TextView foodName = findViewById(R.id.foodName);
            foodName.setText(name);

            TextView foodGroup= findViewById(R.id.foodGroup);
            foodGroup.setText(calc.getGroup().getName());

            TextView register = findViewById(R.id.registered);

            TextView score = findViewById(R.id.score);

            TextView recommendation = findViewById(R.id.Recommendation);
            if(calc.currentGroup.getName().equals("Other")) {
                score.setText("N/A");
                register.setText("Failed to Register Food");
                recommendation.setText("Sorry, your documented food is not found");
            }
            else {
                score.setText(""+calc.getCo2() +" lbs");
                register.setText("Successfully Registered Food");
                recommendation.setText(calc.getRec());
                UploadData helper = new UploadData();
                        helper.uploadMeal(0+"","",calc.getCo2(),calc.getName(),true);
            }

            ImageView img = findViewById(R.id.imageView2);
            img.setImageBitmap(bitmap);

            cam_button = findViewById(R.id.backToCam);
            cam_button.setBackgroundColor(Color.parseColor("#3ead60"));
            cam_button.setOnClickListener(v -> openHome());

        });

        alert.show();

    }

    public String searchFoodList(ArrayList<String> list) {
        Calculation temp = new Calculation("",1);
        for(int i = 0; i < list.size(); i++) { // for each predicted label
            for(FoodGroup f: temp.groupList) { // for each food group
                if(Arrays.asList(f.getFoodList()).contains(list.get(i).toLowerCase())) { // if the food list of group contains given label
                    return list.get(i);
                }
            }
        }
        return list.get(0);
    }
    public void openHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}