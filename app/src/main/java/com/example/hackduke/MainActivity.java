package com.example.hackduke;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //ArrayList<String> myTexts = new ArrayList<>();
    //String data;

    Button btnToCamera;
    Button btnToFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnToCamera = findViewById(R.id.camera_button);
        btnToFriends = findViewById(R.id.friends_button);

        //getData();

        /*BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);*/

        btnToCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraActivity(v);
            }
        });
    }

    /*private void getData(){
        if(getIntent().hasExtra("data")) {
            //myTexts = getIntent().getStringArrayListExtra("test");
            data = getIntent().getStringExtra("data");
        } else{
            Log.d("FOOOOD1","NODATA");;
        }
    }*/

    public void openCameraActivity(View view){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }


}