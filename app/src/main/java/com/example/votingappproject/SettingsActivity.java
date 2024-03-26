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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends AppCompatActivity
{
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        username = getIntent().getStringExtra("ActiveUsername");
    }

    public void backToProfile(View view)
    {
        Intent intent = new Intent(SettingsActivity.this, ProfileActivity.class);
        intent.putExtra("ActiveUsername", username);
        startActivity(intent);
        finish();
    }

    public void verifyPassword(String username, String password) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://votingapp-6475d-default-rtdb.firebaseio.com/");
        DatabaseReference usersRef = database.getReference("Users");

        // Query the database for a user with the given username
        usersRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Check if user exists
                if (dataSnapshot.exists()) {
                    // Get the User object
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null && user.getPassword().equals(password)) {
                        // Passwords match, proceed to login
                        showToast("Password verified");
                        changePassword();
                    }
                    else {
                        // Passwords do not match
                        showToast("Incorrect password");
                    }
                } else {
                    // User does not exist
                    showToast("Invalid user");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting User failed, log a message
                Log.w("LoginActivity", "loadUser:onCancelled", databaseError.toException());
                showToast("Database error, login failed.");
            }
        });
    }

    public void changePassword()
    {
        EditText newPasswordTextView = findViewById(R.id.newPasswordEditText);
        String newPassword = newPasswordTextView.getText().toString();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(username);
        userRef.child("password").setValue(newPassword)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Password updated successfully in the database
                        showToast("Password updated successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to update password in the database
                        showToast("Failed to update password. Please try again.");
                    }
                });

    }

    public void submitNewPassword(View view)
    {
        EditText currentPasswordTextView = findViewById(R.id.currentPasswordEditText);
        String currentPassword = currentPasswordTextView.getText().toString();

        verifyPassword(username, currentPassword);
    }

    public void showToast(String message)
    {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, message, duration);
        toast.show();
    }
}