package com.example.hackduke;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //TextView elementOne, elementTwo, elementThree;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<meal> MealHistory = new ArrayList<meal>();
    /*ArrayList<String> myTexts = new ArrayList<>();
    String data;*/
    List<Friend> localFriends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnToCamera;
        Button btnToFriends;
        Button btnToInstructions;

        /*elementOne = findViewById(R.id.elementOne);
        elementTwo = findViewById(R.id.elementTwo);
        elementThree = findViewById(R.id.elementThree);*/
        btnToCamera = findViewById(R.id.camera_button);
        btnToFriends = findViewById(R.id.friends_button);
        btnToInstructions = findViewById(R.id.instructions_button);

        //getData();
        //setData();

        db.collection("meals").whereEqualTo("Uid", "1").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()==true){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        MealHistory.add(new meal(document.getData()));
                    }
                }
            }
        });

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

        btnToFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFriendsActivity(v);
            }
        });

        btnToInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStartActivity(v);
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
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openFriendsActivity(View view){
        Intent intent = new Intent(this, FriendsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openStartActivity(View view){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    /*private void setData() {

        elementOne.setText(data);
    }*/
}