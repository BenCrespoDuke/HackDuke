package com.example.hackduke;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FriendsActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DownloadData downloadHelp = new DownloadData();
    ArrayList<meal> friendMeals = new ArrayList<meal>();
    ArrayList<Friend> friends;
    ArrayList<String> friendUid = new ArrayList<String>();
    String Uid;

    RecyclerView recyclerView;


    Context ct = this;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Long> carbon = new ArrayList<>();
    ArrayList<Integer> images = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uid = "0";
        setContentView(R.layout.activity_friends);

        Button btnToHome;
        Button btnToCamera;

        btnToHome = findViewById(R.id.home_button3);
        btnToCamera = findViewById(R.id.camera_button3);

        btnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity(v);
            }
        });

        btnToCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraActivity(v);
            }
        });


        //FindsList of Current Friends

        List<Friend> finalFriend = new ArrayList<Friend>();
        Query friendQuery = db.collection("users").whereEqualTo("Uid", Uid);
        friendQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() == true) {
                    Map<String, Object> tempMap;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        tempMap = document.getData();
                        Object[] temp = (Object[]) ((ArrayList<String>) (tempMap.get("friends"))).toArray();

                        for (Object string : temp) {
                            friendUid.add(string.toString());
                        }

                    }
                } else {

                    Log.w("Cloud Activty", "ERROR GETTING USER MEALS", task.getException());
                }


                db.collection("users").whereIn("Uid", friendUid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() == true) {
                            for (DocumentSnapshot document : task.getResult()) {
                                finalFriend.add(new Friend(document.getData()));
                            }
                        }


                        // Do any UI setup Using Friend Information
                        friends = (ArrayList<Friend>) finalFriend;

                        recyclerView = findViewById(R.id.recyclerView);

                        for (Friend friend : friends) {
                            names.add((String) friend.getFriendData().get("name"));
                            carbon.add((Long) friend.getFriendData().get("carbonAverage"));
                            images.add(R.drawable.c_plus_plus);
                        }

                        FriendsAdapter friendAdapter = new FriendsAdapter(ct, names, carbon, images);
                        recyclerView.setAdapter(friendAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ct));
                       if (friendUid.isEmpty() == false) {
                            db.collection("users").whereIn("Uid",friendUid).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if (error != null) {
                                        Log.w("Cloud Activity", "Listen failed", error);
                                        return;
                                    }

                                    if (value != null) {
                                        List<DocumentChange> changes = value.getDocumentChanges();
                                        if (changes.size() > 0) {
                                            for (DocumentChange ch : changes) {
                                                DocumentSnapshot tempDoc = ch.getDocument();
                                                for (int i = friends.size() - 1; i > -1; i--) {
                                                    if ((friends.get(i).getFriendData().containsKey("Uid") == true) && (tempDoc.contains("Uid") == true) &&
                                                            (friends.get(i).getFriendData().get("Uid")).equals((String) tempDoc.get("Uid"))) {
                                                        friends.remove(i);
                                                        friends.add(new Friend(tempDoc.getData()));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            });
                        }

                        db.collection("meals").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {
                                    Log.w("Cloud Activity", "Listen failed", error);
                                    return;
                                }
                                if (value != null) {
                                    List<DocumentChange> changes = value.getDocumentChanges();
                                    for (DocumentChange ch : changes) {
                                        DocumentSnapshot document = ch.getDocument();
                                        if (friends.indexOf(document.get("Uid")) != -1) {
                                            friendMeals.add(new meal(document.getData()));
                                        }
                                        for (Friend tempFriend : friends) {
                                            if (tempFriend.getFriendData().containsKey("Uid")) {
                                                friendUid.add((String) tempFriend.getFriendData().get("Uid"));
                                            }
                                        }
                                    }
                                }
                            }
                        });


                        // Finds Friend meals
                        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
                        db.collection("Meals").whereIn("Uid", Arrays.asList(friendUid)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful() == true) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (document.contains("isVisible") == true && (Boolean) document.get("isVisible").equals(new Boolean(false)))
                                            result.add(document.getData());
                                    }
                                } else {
                                    Log.w("Cloud Activty", "ERROR GETTING USER MEALS", task.getException());
                                }
                                ArrayList<meal> finalResult = new ArrayList<meal>();
                                for (Map<String, Object> map : result) {
                                    finalResult.add(new meal(map));
                                }
                                friendMeals = finalResult;
                                // Do any UI setup Using Friend Meal Informatiobn


                            }
                        });


                    }
                });


                //Listener for Friend meal changes


                //Listner for friend changes


                db.collection("users").whereEqualTo("Uid", FirebaseAuth.getInstance().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w("Cloud Activity", "Listen failed", error);
                            return;
                        }
                        if (value != null) {
                            List<DocumentChange> ch = value.getDocumentChanges();
                        }
                    }
                });
            }
        });
    }

    public void openMainActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void openCameraActivity(View view){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}