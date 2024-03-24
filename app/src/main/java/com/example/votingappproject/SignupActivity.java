package com.example.votingappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.votingappproject.Model.User;


public class SignupActivity extends AppCompatActivity
{
    private FirebaseDatabase database;
    private DatabaseReference databaseReference_user;
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
        databaseReference_user.child(newUser.toString()).setValue(newUser)
            .addOnSuccessListener(aVoid -> {
                showToast("New user created successfully");
            })
            .addOnFailureListener(e -> {
                showToast("Failed to add user to database :(" + e.getMessage());
                Log.e("SignupActivity", "Failed to add user to database", e);
            });

        displayDashboard();
    }

    public void displayDashboard()
    {
        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void showToast(String message)
    {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, message, duration);
        toast.show();
    }

    public void displayLogin(View view)
    {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}