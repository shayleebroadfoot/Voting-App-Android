package com.example.votingappproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votingappproject.Model.Topic;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity
{
    private String username;
    private final ArrayList<Topic> topicsList = new ArrayList<>();
    private ArrayAdapter<Topic> topicsAdapter;
    private DatabaseReference databaseRef;
    private ArrayList<String> topicsDescriptions = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // Ensure you have a ListView in your layout with an appropriate ID

        ListView listView = findViewById(R.id.topicsListView); // Replace with your actual ListView ID
        username = getIntent().getStringExtra("ActiveUsername");

        TextView usernameTextView = findViewById(R.id.usernameTextView);

        // Set the TextView to display the username
        usernameTextView.setText(username);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, topicsDescriptions);
        listView.setAdapter(adapter);

        fetchUserSelectedTopics();
    }

    private void fetchUserSelectedTopics()
    {
        databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(username).child("selectedTopicsIds");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    String topicId = snapshot.getKey();
                    fetchTopicDescription(topicId);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(ProfileActivity.this, "Failed to load user's selected topics.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchTopicDescription(String topicId)
    {
        DatabaseReference topicsRef = FirebaseDatabase.getInstance().getReference("Topics").child(topicId);
        topicsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String description = dataSnapshot.child("description").getValue(String.class);
                if (description != null)
                {
                    topicsDescriptions.add(description);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, "Failed to load topic description.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void displayDashboard(View view)
    {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.putExtra("ActiveUsername", username);
        startActivity(intent);
        finish();
    }

    public void logout(View view)
    {
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void displaySettingsActivity(View view)
    {
        Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
        intent.putExtra("ActiveUsername", username);
        startActivity(intent);
        finish();
    }
}
