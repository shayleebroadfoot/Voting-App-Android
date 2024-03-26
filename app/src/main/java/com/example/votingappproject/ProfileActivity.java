package com.example.votingappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity
{
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = getIntent().getStringExtra("ActiveUsername");

        TextView usernameTextView = findViewById(R.id.usernameTextView);

        // Set the TextView to display the username
        usernameTextView.setText(username);
    }
}