package com.example.hackduke;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddFriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        EditText inputFriends = findViewById(R.id.inputEmail);
        String friendName = inputFriends.getText().toString();

        TextView name = findViewById(R.id.Name);
        name.setText(friendName);
    }
}