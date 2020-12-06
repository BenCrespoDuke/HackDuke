package com.example.hackduke;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView elementOne, elementTwo, elementThree;

    ArrayList<String> myTexts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        elementOne = findViewById(R.id.elementOne);
        elementTwo = findViewById(R.id.elementTwo);
        elementThree = findViewById(R.id.elementThree);

        getData();
        setData();

//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
    }

    private void getData(){
        if(getIntent().hasExtra("test")) {
            myTexts = getIntent().getStringArrayListExtra("test");
        } else{
            Log.d("FOOOOD1","NODATA");;
        }
    }

    private void setData() {

        elementOne.setText(myTexts.get(0));
        elementTwo.setText(myTexts.get(1));
        elementThree.setText(myTexts.get(2));
    }
}