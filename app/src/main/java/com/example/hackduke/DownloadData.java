package com.example.hackduke;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DownloadData {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public DownloadData(){

    }
    public ArrayList<Map<String,Object>> getMeals(String Uid){

        ArrayList<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        CollectionReference reff = db.collection("Meals");
        reff.whereEqualTo("Uid",Uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()==true) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        result.add(document.getData());
                        Log.d("Cloud Activity", document.getId() + " : " + document.getData());
                    }
                }
                else{

                    Log.w("Cloud Activty","ERROR GETTING USER MEALS",task.getException());
                    }

            }
        });
        return result;
    }

    public ArrayList<Map<String,Object>> getFiendMeals(String Uid){
        ArrayList<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        List<String> friends = this.getFriends(Uid);
        db.collection("Meals").whereIn("Uid", Arrays.asList(friends)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()==true){
                    for (QueryDocumentSnapshot document: task.getResult()) {
                        result.add(document.getData());
                    }
                }
                else{
                    Log.w("Cloud Activty","ERROR GETTING USER MEALS",task.getException());
                }
            }
        });

        return result;
    }

    public List<String> getFriends(String Uid){
       List<String> friends = new ArrayList<String>();

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
                            friends.add(string);
                        }

                    }
                }
                else{

                    Log.w("Cloud Activty","ERROR GETTING USER MEALS",task.getException());
                }
            }
        });
        return friends;
    }
    public List<String> getFriendRequest(String Gmail){
        List<String> friends = new ArrayList<String>();

        db.collection("user").whereEqualTo("email", Gmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() == true) {
                    for (QueryDocumentSnapshot documents : task.getResult()) {
                        String[] temp =(String[])(documents.getData().get("friends requests"));
                        for(String string: temp){
                            friends.add(string);
                        }
                    }
                }
            }
        });

        return friends;
    }


}