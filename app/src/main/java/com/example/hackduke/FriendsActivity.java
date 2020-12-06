package com.example.hackduke;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    ArrayList<String> friendUid;
    String Uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uid = FirebaseAuth.getInstance().getUid();
        setContentView(R.layout.activity_friends);




            //FindsList of Current Friends
            List<String> friendUID = new ArrayList<String>();
            List<Friend> finalFriend = new ArrayList<Friend>();
            Query friendQuery = db.collection("users").whereEqualTo("Uid",Uid);
            friendQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()==true){
                        Map<String,Object> tempMap;
                        for (QueryDocumentSnapshot document:task.getResult()) {
                            tempMap = document.getData();
                            String[] temp =(String[])(tempMap.get("friends"));
                            for(String string: temp){
                                friendUID.add(string);
                            }

                        }
                    }
                    else{

                        Log.w("Cloud Activty","ERROR GETTING USER MEALS",task.getException());
                    }


                    db.collection("users").whereIn("Uid",friendUID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()==true){
                                for(DocumentSnapshot document: task.getResult()){
                                    finalFriend.add(new Friend(document.getData()));
                                }
                                // Do any UI setup Using Friend Information
                                friends = (ArrayList<Friend>)finalFriend;






                                // Finds Friend meals
                                ArrayList<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
                                db.collection("Meals").whereIn("Uid", Arrays.asList(friendUID)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()==true){
                                            for (QueryDocumentSnapshot document: task.getResult()) {
                                                if (document.contains("isVisible")==true && (Boolean)document.get("isVisible").equals(new Boolean(false)))
                                                    result.add(document.getData());
                                            }
                                        }
                                        else{
                                            Log.w("Cloud Activty","ERROR GETTING USER MEALS",task.getException());
                                        }
                                        ArrayList<meal> finalResult = new ArrayList<meal>();
                                        for (Map<String,Object> map:result) {
                                            finalResult.add(new meal(map));
                                        }
                                        friendMeals = finalResult;
                                        // Do any UI setup Using Friend Meal Informatiobn


                                    }
                                });







                            }
                        }
                    });

                }

            });































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