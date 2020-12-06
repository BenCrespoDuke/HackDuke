package com.example.hackduke;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage("Estimated Amount of Food(lbs)");

// Set an EditText view to get user input
        EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
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
                }

                ImageView img = findViewById(R.id.imageView2);
                img.setImageBitmap(bitmap);

                cam_button = (Button) findViewById(R.id.backToCam);
                cam_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openHome();
                    }
                });
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                ConstraintLayout w = findViewById(R.id.whole);
                w.setVisibility(View.VISIBLE);
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

                TextView  recommendation = findViewById(R.id.Recommendation);
                if(calc.currentGroup.getName().equals("Other")) {
                    score.setText("N/A");
                    register.setText("Failed to Register Food");
                    recommendation.setText("Sorry, your documented food is not found");
                }
                else {
                    score.setText(""+calc.getCo2() +" lbs");
                    register.setText("Successfully Registered Food");
                    recommendation.setText(calc.getRec());
                }

                ImageView img = findViewById(R.id.imageView2);
                img.setImageBitmap(bitmap);

                cam_button = (Button) findViewById(R.id.backToCam);
                cam_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openHome();
                    }
                });
            }
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