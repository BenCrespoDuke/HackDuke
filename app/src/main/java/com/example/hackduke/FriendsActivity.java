package com.example.hackduke;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DownloadData downloadHelp = new DownloadData();
    ArrayList<meal> friendMeals = new ArrayList<meal>();
    ArrayList<Friend> friends;
    ArrayList<String> friendUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        friends = (ArrayList<Friend>) downloadHelp.getFriends(FirebaseAuth.getInstance().getUid());
        friendMeals = downloadHelp.getFiendMeals(FirebaseAuth.getInstance().getUid());

        //Listener for Friend meal changes
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
                    }
                }
            }
        });
        for (Friend tempFriend : friends) {
            if (tempFriend.getFriendData().containsKey("Uid")) {
                friendUid.add((String) tempFriend.getFriendData().get("Uid"));
            }
        }

        //Listner for friend changes
        db.collection("users").whereIn("Uid", friendUid).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("Cloud Activity", "Listen failed", error);
                    return;
                }

                if (value != null) {
                    List<DocumentChange> changes = value.getDocumentChanges();
                    for (DocumentChange ch : changes) {
                        DocumentSnapshot tempDoc = ch.getDocument();
                        for(int i = friends.size()-1; i>-1;i--){
                            if ((friends.get(i).getFriendData().containsKey("Uid")==true)&&(tempDoc.contains("Uid")==true)&&
                                    (friends.get(i).getFriendData().get("Uid")).equals((String)tempDoc.get("Uid"))){
                                friends.remove(i);
                                friends.add(new Friend(tempDoc.getData()));

                            }
                        }
                    }
                }
            }
        });



        db.collection("users").whereEqualTo("Uid",FirebaseAuth.getInstance().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.w("Cloud Activity", "Listen failed", error);
                    return;
                }
                if(value!=null){
                    List<DocumentChange> ch = value.getDocumentChanges();


                }

            }
        });
    }
}