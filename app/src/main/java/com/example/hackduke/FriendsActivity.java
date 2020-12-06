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
    ArrayList<String> friends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
         friends = (ArrayList<String>) downloadHelp.getFriends(FirebaseAuth.getInstance().getUid());
        friendMeals = downloadHelp.getFiendMeals(FirebaseAuth.getInstance().getUid());


        db.collection("meals").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!= null){
                    Log.w("Cloud Activity","Listen failed", error);
                    return;
                }
                if (value!=null){
                    List<DocumentChange> changes = value.getDocumentChanges();
                    for(DocumentChange ch: changes){
                        DocumentSnapshot document = ch.getDocument();
                        if(friends.indexOf(document.get("Uid"))!=-1){
                            friendMeals.add(new meal(document.getData()));
                        }
                    }
                }
            }
        });
    }
}