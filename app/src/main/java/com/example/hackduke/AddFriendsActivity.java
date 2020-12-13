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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AddFriendsActivity extends AppCompatActivity {
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ArrayList<Friend> friendsRequesting = new ArrayList<Friend>();
    public RecyclerView recycle;
    public Context ct = this;
    public Button btnToFriends;
    public String DocID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        btnToFriends = findViewById(R.id.button5);

        btnToFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFriendsActivity(v);
            }
        });

        //Get Users requesting to be friends
        Query query = db.collection("users").whereEqualTo("Uid","0");
        //Get Current User's Friend Requests
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<String> friendreq = new ArrayList<String>();
                    recycle = findViewById(R.id.recyclerView2);
                    if(recycle == null)
                        Log.d("check","recylcer view is null");
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        DocID = documentSnapshot.getId();
                        friendreq = (ArrayList<String>) documentSnapshot.get("friend requests");
                    }
                    Log.d("Check",friendreq.size()+"");


                    if(!friendreq.isEmpty()) {
                        //Gets profiles of requesting friends
                        Query query1 = db.collection("users").whereIn("email", friendreq);
                        query1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                        friendsRequesting.add(new Friend(documentSnapshot.getData()));
                                    }


                                    Log.d("Check", friendsRequesting.size() + " pincdiphdc");
                                    AddFriendsAdapter friend = new AddFriendsAdapter(ct, new ArrayList<String>(), new ArrayList<Double>(), new ArrayList<Integer>(), new ArrayList<Double>(), new ArrayList<String>());
                                    editAdapter(friend, friendsRequesting);
                                    //Do UI setup with requesting Users


                                    // Adds Realtime Listener
                                    final DocumentReference reff = db.collection("users").document(DocID);
                                    reff.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if (error != null) {
                                                Log.w("check", "Snapshot ERROR", error);
                                            } else {
                                                ArrayList<String> newFriendRequests = (ArrayList<String>) value.get("friend requests");
                                                for (int i = friendsRequesting.size() - 1; i > -1; i--) {

                                                    if (newFriendRequests.indexOf(friendsRequesting.get(i).getFriendData().get("email")) == -1) {
                                                        friendsRequesting.remove(i);
                                                    }

                                                }

                                                AddFriendsAdapter friend = new AddFriendsAdapter(ct, new ArrayList<String>(), new ArrayList<Double>(), new ArrayList<Integer>(), new ArrayList<Double>(), new ArrayList<String>());
                                                editAdapter(friend, friendsRequesting);
                                                //Do UI setup with requesting Users
                                            }

                                        }

                                    });
                                }
                            }
                        });
                    }
                }


            }
        });
    }

    public void editAdapter(AddFriendsAdapter a, ArrayList<Friend> f) {
        for (Friend friend : f) {
            a.names.add((String) friend.getFriendData().get("name"));
            a.carbons.add((Double) friend.getFriendData().get("carbonAverage"));
            a.images.add(R.drawable.pfp);
            a.numMeals.add((Double) friend.getFriendData().get("number of meals"));
            a.emails.add((String) friend.getFriendData().get("email"));
            Log.d("check", "one loop");
        }
        recycle.setAdapter(a);
        recycle.setLayoutManager(new LinearLayoutManager(ct));
    }

    public void openFriendsActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}