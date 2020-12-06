package com.example.hackduke;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Transaction;

import java.util.HashMap;
import java.util.Map;

public class UploadData {
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public UploadData(){

    }

    public void uploadMeal(String Uid, String date, double carbon, String[] foodStuff, boolean isVisible){
        Map<String,Object> meal = new HashMap<>();
        meal.put("Uid",Uid);
        meal.put("date",date);
        meal.put("carbonAmount",carbon);
        meal.put("Food Stuff", foodStuff);
        meal.put("isVisible", isVisible);
        db.collection("meals").add(meal).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.d("Cloud Activity","The meal upload with Doc ID "+documentReference.getId());
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.w("Cloud Activity", "ERROR adding document",e);
                    }
                });

    }


    public void createUser(String Uid, String name, String email){
        Map<String,Object> user = new HashMap<>();
        user.put("Uid",Uid);
        user.put("email", email);
        user.put("name",name);
        db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("Cloud Activity","The User upload with Doc ID "+documentReference.getId());
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Cloud Activity", "ERROR adding document",e);
                    }
                });

    }

    public void makeFriendRequest(String gmail){
        final Query reff = db.collection("users").whereEqualTo("email",gmail);

                db.runTransaction(new Transaction.Function<Object>() {
                    @Override
                    public Object apply(Transaction transaction) throws FirebaseFirestoreException{


                        return null;
                    }
                });
                }

}


