package com.example.votingappproject;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import com.example.votingappproject.Model.Admin;
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

    public void verifyLogin(String username, String password) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://votingapp-6475d-default-rtdb.firebaseio.com/");
        DatabaseReference usersRef = database.getReference("Users");
        DatabaseReference adminsRef = database.getReference("Admins"); // Reference to the Admins

        // First, try to find the user in the Users node
        usersRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User found in Users node, proceed with login
                    validateUser(dataSnapshot, password);
                } else {
                    // User not found in Users, try Admins node
                    adminsRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot adminSnapshot) {
                            if (adminSnapshot.exists()) {
                                // Admin found, proceed with login
                                validateAdmin(adminSnapshot, password);
                            } else {
                                // Admin not found, login failed
                                showToast("Login Failed");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError adminError) {
                            showToast("Database error, login failed.");
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showToast("Database error, login failed.");
            }
        });
    }

    private void validateUser(DataSnapshot dataSnapshot, String password) {
        User user = dataSnapshot.getValue(User.class);
        if (user != null && user.getPassword().equals(password)) {
            showToast("Login Successful");
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("ActiveUsername", user.getUsername());
            startActivity(intent);
            finish();
        } else {
            showToast("Login Failed");
        }
    }

    private void validateAdmin(DataSnapshot dataSnapshot, String password) {
        Admin admin = dataSnapshot.getValue(Admin.class);
        if (admin != null && admin.getPassword().equals(password)) {
            showToast("Login Successful");
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("ActiveUsername", admin.getUsername());
            startActivity(intent);
            finish();
        } else {
            showToast("Login Failed");
        }
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