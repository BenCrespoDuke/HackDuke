package com.example.hackduke;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class DownloadData {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public DownloadData(){

    }
    public ArrayList<Map<String,Object>> getMeals(String Uid){

        ArrayList<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        CollectionReference reff = db.collection("Uid");
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



}