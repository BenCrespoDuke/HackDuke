package com.example.hackduke;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
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

    public ArrayList<meal> meals = new ArrayList<meal>();
    public  ArrayList<Friend> friends = new ArrayList<Friend>();
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

    public ArrayList<meal> getFiendMeals(String Uid){
        ArrayList<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        List<Friend> friends = this.getFriends(Uid);
        List<String> friendUids = new ArrayList<String>();
        for (Friend item:friends) {
            if(item.getFriendData().containsKey("Uid")){
                friendUids.add((String) item.getFriendData().get("Uid"));
            }

        }

        db.collection("Meals").whereIn("Uid", Arrays.asList(friends)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
            }
        });
        ArrayList<meal> finalResult = new ArrayList<meal>();
        for (Map<String,Object> map:result) {
            finalResult.add(new meal(map));
        }

        return finalResult;
    }

    public List<Friend> getFriends(String Uid){
       List<String> friends = new ArrayList<String>();
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
                            friends.add(string);
                        }

                    }
                }
                else{

                    Log.w("Cloud Activty","ERROR GETTING USER MEALS",task.getException());
                }


                db.collection("users").whereIn("Uid",friends).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()==true){
                            for(DocumentSnapshot document: task.getResult()){
                                finalFriend.add(new Friend(document.getData()));
                            }
                        }
                    }
                });

            }

        });
        return finalFriend;
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

    public void getFiendMeals(List<Friend> friends){
        ArrayList<Map<String,Object>> result = new ArrayList<Map<String,Object>>();

        List<String> friendUids = new ArrayList<String>();
        for (Friend item:friends) {
            if(item.getFriendData().containsKey("Uid")){
                friendUids.add((String) item.getFriendData().get("Uid"));
            }

        }

        db.collection("Meals").whereIn("Uid", Arrays.asList(friends)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                meals = finalResult;
            }
        });



    }





}