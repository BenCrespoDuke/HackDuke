package com.example.hackduke;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(this);

        TextView start = findViewById(R.id.textView);
        start.setBackgroundColor(Color.parseColor("#3ead60"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
            default :
                return;
        }
    }
}