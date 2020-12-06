package com.example.hackduke;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AddFriendsActivity extends AppCompatActivity {
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ArrayList<Friend> friendsRequesting = new ArrayList<Friend>();
    public RecyclerView recycle;
    public Context ct = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);


        //Get Users requesting to be friends
        Query query = db.collection("user").whereEqualTo("Uid",0);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() == true) {
                    ArrayList<String> friendreq = new ArrayList<String>();
                    recycle = findViewById(R.id.recyclerView);
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        //DocID = documentSnapshot.getId();
                        friendreq = (ArrayList<String>) documentSnapshot.get("friend requests");
                    }
                    Query query1 = db.collection("users").whereArrayContains("email",friendreq);
                    query1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if(task.isSuccessful()==true){
                                for (DocumentSnapshot documentSnapshot:task.getResult()) {
                                    friendsRequesting.add(new Friend(documentSnapshot.getData()));
                                }
                                FriendsAdapter friend = new FriendsAdapter(ct, new ArrayList<String>(), new ArrayList<Long>(), new ArrayList<Integer>(), new ArrayList<String>(), new ArrayList<String>());
                                for(Friend f : friendsRequesting) {
                                    friend.names.add((String) f.FriendData.get("name"));
                                    friend.carbons.add((Long) f.FriendData.get("carbonAverage"));
                                    friend.images.add(R.drawable.pfp);
                                    friend.meals.add((String) f.FriendData.get("number of meals"));
                                    friend.emails.add((String) f.FriendData.get("email"));
                                }
                                recycle.setAdapter(friend);
                                recycle.setLayoutManager(new LinearLayoutManager(ct));
                                //Do UI setup with requesting Users
                                for(Friend f : friendsRequesting){

                                }
                            }
                        }
                    });
                }
            }
        });
    }
}