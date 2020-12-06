package com.example.hackduke;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AddFriendsActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        EditText inputFriends = findViewById(R.id.inputEmail);
        String friendName = inputFriends.getText().toString();

        TextView name = findViewById(R.id.Name);
        name.setText(friendName);

        Query query = db.collection("user").whereEqualTo("Uid",0);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() == true) {
                    ArrayList<String> friendreq = new ArrayList<String>();

                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        //DocID = documentSnapshot.getId();
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