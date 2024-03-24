package com.example.votingappproject;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import com.example.votingappproject.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void displayDashboard(View view)
    {
        EditText usernameIn = findViewById(R.id.text_username);
        EditText passwordIn = findViewById(R.id.text_password);

        verifyLogin(usernameIn.getText().toString(), passwordIn.getText().toString());
    }

    public void verifyLogin(String username, String password)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://votingapp-6475d-default-rtdb.firebaseio.com/");
        DatabaseReference usersRef = database.getReference("Users");

        // Query the database for a user with the given username
        usersRef.child(username).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Check if user exists
                if (dataSnapshot.exists()) {
                    // Get the User object
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null && user.getPassword().equals(password)) {
                        // Passwords match, proceed to login
                        showToast("Login Successful");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        // Passwords do not match
                        showToast("Login Failed");
                    }
                } else {
                    // User does not exist
                    showToast("Login Failed");
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

    public void showToast(String message)
    {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, message, duration);
        toast.show();
    }

    public void displaySignUp(View view)
    {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
        finish();
    }
}


