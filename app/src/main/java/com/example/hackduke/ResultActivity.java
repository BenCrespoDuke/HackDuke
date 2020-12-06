package com.example.hackduke;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class ResultActivity extends AppCompatActivity {
    private Button cam_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle extras = getIntent().getExtras();
        ArrayList<String> predictList = extras.getStringArrayList("data");
        Intent intent = getIntent();
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("bit");
        String name = searchFoodList(predictList);
        Calculation calc = new Calculation(name,1);
        calc.calculate();

        TextView foodName = findViewById(R.id.foodName);
        foodName.setText(name);

        TextView foodGroup= findViewById(R.id.foodGroup);
        foodGroup.setText(calc.getGroup().getName());

        TextView score = findViewById(R.id.score);
        score.setText(""+calc.getCo2() +" lbs");

        TextView  recommendation = findViewById(R.id.Recommendation);
        recommendation.setText(calc.getRec());

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
    public String searchFoodList(ArrayList<String> list) {
        Calculation temp = new Calculation("",1);
        for(int i = 0; i < list.size(); i++) {
            for(FoodGroup f: temp.groupList) {
                if(Arrays.asList(f.getFoodList()).contains(list.get(i))) {
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