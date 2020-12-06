package com.example.hackduke;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    private Button cam_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Calculation calc = new Calculation(name,1);
        calc.calculate();
        TextView foodName = findViewById(R.id.foodName);
        foodName.setText(name);
        TextView foodGroup= findViewById(R.id.foodGroup);
        foodGroup.setText(calc.getGroup().getName());
        TextView score = findViewById(R.id.score);
        score.setText(""+calc.getCo2());
        TextView  recommendation = findViewById(R.id.Recommendation);
        recommendation.setText(calc.getRec());
        ImageView img = findViewById(R.id.imageView2);
        img.setImageBitmap(bm);
        cam_button = (Button) findViewById(R.id.backToCam);
        cam_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });
    }
    public void openHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void getData(){
        if(getIntent().hasExtra("data")) {
            //myTexts = getIntent().getStringArrayListExtra("test");
            data = getIntent().getStringExtra("data");
        } else{
            Log.d("FOOOOD1","NODATA");;
        }
    }

    private void setData() {

        elementOne.setText(data);
    }
}