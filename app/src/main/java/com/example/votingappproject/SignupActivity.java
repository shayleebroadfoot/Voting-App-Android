package com.example.votingappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.votingappproject.Model.User;

public class SignupActivity extends AppCompatActivity {

    //TextView txtSignIn;
    FirebaseDatabase database;
    int initialId = 0;

    DatabaseReference databaseReference_user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
    public void createUser(View view)
    {
        EditText usernameEditText = findViewById(R.id.text_username);
        EditText passwordEditText = findViewById(R.id.text_password);
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        User newUser = new User(username, password);

        database = FirebaseDatabase.getInstance("https://votingapp-6475d-default-rtdb.firebaseio.com/");
        databaseReference_user = database.getReference("Users");
        databaseReference_user.child(newUser.toString()).setValue(newUser);

        displayDashboard();
    }

    public void displayDashboard()
    {
        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}