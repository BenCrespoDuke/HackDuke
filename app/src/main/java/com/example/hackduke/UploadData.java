package com.example.hackduke;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadData {
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String docID = "";
    String DocID = "";
    public UploadData(){

    }

    public void uploadMeal(String Uid, String date, double carbon, String foodStuff, boolean isVisible){
        Map<String,Object> meal = new HashMap<>();
        meal.put("Uid",Uid);
        meal.put("date",date);
        meal.put("carbonAmount",carbon);
        meal.put("Food Stuff", foodStuff);
        meal.put("isVisible", isVisible);
        Date dateAssist = new Date();
        long time = dateAssist.getTime();
        meal.put("time",time);
        db.collection("Meals").add(meal).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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

        Query reff = db.collection("users").whereEqualTo("Uid",Uid);
        reff.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()==true){

                    for(DocumentSnapshot document: task.getResult()){
                        docID= document.getId();
                    }
                    db.runTransaction(new Transaction.Function<Void>() {
                        @Nullable
                        @Override
                        public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                            DocumentSnapshot snap = transaction.get(db.collection("users").document(docID));
                            double origanalNumb = (double)snap.get("number of meals");
                            double currentCarbon = (double)snap.get("carbonAverage");
                            double newAverage = (currentCarbon*origanalNumb+carbon)/(origanalNumb+1);
                            origanalNumb++;
                            transaction.update(db.collection("users").document(docID),"number of meals",origanalNumb);
                            transaction.update(db.collection("users").document(docID),"carbonAverage",newAverage);


                            return null;
                        }
                    });
                }
            }
        });



    }


    public void createUser(String Uid, String name, String email){
        Map<String,Object> user = new HashMap<>();
        user.put("Uid",Uid);
        user.put("email", email);
        user.put("name",name);
        user.put("carbonAverage",0.0);
        user.put("number of meals", 0.0);
        user.put("friends", new ArrayList<String>());
        user.put("friend requests", new ArrayList<String>());
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
        Task reff = db.collection("users").whereEqualTo("email",gmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() == true) {

                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        DocID = documentSnapshot.getId();
                    }
                    db.runTransaction(new Transaction.Function<Void>() {
                        @Nullable
                        @Override
                        public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                            DocumentSnapshot snap = transaction.get(db.collection("users").document(DocID));

                            List<String> friendReqs = ((List<String>)snap.get("friend requests"));
                            friendReqs.add(gmail);
                            transaction.update(db.collection("users").document(DocID),"friend requests",friendReqs);

                            return null;
                        }
                    });

                }
            }
        });

        }

        public void AcceptorDenyFriendRequest(String gmail, String CurrentUserUID, boolean AorD){
        Query query = db.collection("users").whereEqualTo("Uid",CurrentUserUID);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful() == true) {

                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        DocID = documentSnapshot.getId();
                    }
                }
                db.runTransaction(new Transaction.Function<Void>() {
                    @Nullable
                    @Override
                    public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                        DocumentSnapshot snapshot = transaction.get(db.collection("users").document(DocID));
                        Map<String,Object> results = snapshot.getData();
                        ArrayList<String> friends = (ArrayList<String>)results.get("friends");
                        ArrayList<String> friendreq = (ArrayList<String>)results.get("friend requests");
                        if (AorD == true){
                            friendreq.remove(friendreq.indexOf(gmail));
                            friends.add(gmail);
                        }
                        else {
                            friendreq.remove(friendreq.indexOf(gmail));
                        }
                        transaction.update(db.collection("users").document(DocID),"friend requests",friendreq);
                        transaction.update(db.collection("users").document(DocID),"friends",friends);


                        return null;
                    }
                });

            }
        });


        }


        public void getFriendRequests(String currentUserID){
            ;
            Query query = db.collection("user").whereEqualTo("Uid",currentUserID);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful() == true) {
                        ArrayList<String> friendreq = new ArrayList<String>();

                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            DocID = documentSnapshot.getId();
                            friendreq = (ArrayList<String>) documentSnapshot.get("friend requests");
                        }
                        Query query1 = db.collection("users").whereArrayContains("email",friendreq);
                        query1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                ArrayList<Friend> friendsRequesting = new  ArrayList<Friend>();
                                if(task.isSuccessful()==true){
                                    for (DocumentSnapshot documentSnapshot:task.getResult()) {
                                        friendsRequesting.add(new Friend(documentSnapshot.getData()));
                                    }
                                }
                            }
                        });
                    }

                }
            });



        }





}


