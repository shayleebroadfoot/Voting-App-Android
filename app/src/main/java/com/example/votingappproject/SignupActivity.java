package com.example.votingappproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.votingappproject.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void createUser(View view) {
        EditText usernameEditText = findViewById(R.id.text_username);
        EditText passwordEditText = findViewById(R.id.text_password);
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (username.equals("")) {
            showToast("Username cannot be empty");
        } else if (password.length() < 4) {
            showToast("Password must contain a minimum of 4 characters");
        } else {
            verifyNewUsername(username);
        }
    }

    private void verifyNewUsername(String username) {
        database = FirebaseDatabase.getInstance("https://votingapp-6475d-default-rtdb.firebaseio.com/");
        databaseReference_user = database.getReference("Users");

        databaseReference_user.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Username already exists
                    showToast("Username already taken");
                } else {
                    // Username does not exist, proceed to create user
                    createUserInDatabase(username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error occurred
                Log.w("SignupActivity", "loadUser:onCancelled", databaseError.toException());
                showToast("Database error, signup failed.");
            }
        });
    }

    private void createUserInDatabase(String username) {
        EditText passwordEditText = findViewById(R.id.text_password);
        String password = passwordEditText.getText().toString();

        User newUser = new User(username, password);
        databaseReference_user.child(username).setValue(newUser)
                .addOnSuccessListener(aVoid -> {
                    showToast("New user created successfully");
                    displayDashboard(username);
                })
                .addOnFailureListener(e -> {
                    showToast("Failed to add user to database :(" + e.getMessage());
                    Log.e("SignupActivity", "Failed to add user to database", e);
                });
    }

    private void displayDashboard(String username) {
        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        intent.putExtra("ActiveUsername", username);
        startActivity(intent);
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void displayLogin(View view) {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void displayAdminSignUp(View view)
    {
        Intent intent = new Intent(SignupActivity.this, AdminSignUpActivity.class);
        startActivity(intent);
        finish();
    }
}
