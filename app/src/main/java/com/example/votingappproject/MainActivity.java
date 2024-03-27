package com.example.votingappproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.votingappproject.Model.Choice;
import com.example.votingappproject.Model.Topic;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private String username;
    private final ArrayList<Topic> topicsList = new ArrayList<>();
    private ArrayAdapter<Topic> topicsAdapter;
    private DatabaseReference databaseRef;
    private EditText newTopicEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = getIntent().getStringExtra("ActiveUsername");

        newTopicEditText = findViewById(R.id.newTopicEditText);
        Button addTopicButton = findViewById(R.id.addTopicButton);
        ListView topicsListView = findViewById(R.id.topicsListView);

        topicsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, topicsList);
        topicsListView.setAdapter(topicsAdapter);

        databaseRef = FirebaseDatabase.getInstance().getReference("Topics");

        loadTopics();

        addTopicButton.setOnClickListener(view -> {
            String topicDescription = newTopicEditText.getText().toString().trim();
            if (!TextUtils.isEmpty(topicDescription)) {
                addNewTopic(topicDescription);

            } else {
                Toast.makeText(MainActivity.this, "Topic description cannot be empty.", Toast.LENGTH_SHORT).show();
            }

        });

        topicsListView.setOnItemClickListener((parent, view, position, id) -> {
            Topic selectedTopic = topicsList.get(position);
            if (selectedTopic.getTopicID() != null)
            {
                Intent intent = new Intent(MainActivity.this, TopicsActivity.class);
                intent.putExtra("topicID", selectedTopic.getTopicID());
                intent.putExtra("topicDescription", selectedTopic.getDescription());
                intent.putExtra("ActiveUsername", username);
                startActivity(intent);
            }
            else {
                Toast.makeText(MainActivity.this, "Error: Topic ID is missing.", Toast.LENGTH_SHORT).show();
            }
        });

        FirebaseDatabase.getInstance().getReference("Admins").child(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // If the snapshot exists, the user is an admin
                        if (snapshot.exists()) {
                            // Set up a long click listener on the ListView to delete topics
                            topicsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                @Override
                                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                    Topic selectedTopic = topicsList.get(position);
                                    showDeleteDialog(selectedTopic);
                                    return true; // Indicate that the click was handled
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle possible errors
                    }
                });
    }

    private void loadTopics() {
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                topicsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Create a new Topic object
                    Topic topic = new Topic();
                    ArrayList<Choice> choices = new ArrayList<>();
                    topic.setTopicID(snapshot.getKey());
                    topic.setDescription(snapshot.child("description").getValue(String.class));
                    topic.setChoicesList(choices);
                    int ChoiceCount = 0;
                    int TopChoiceCount = 0;
                    Choice TopChoice = null;

                    // Deserialize choicesList from HashMap to ArrayList

                    for (DataSnapshot choiceSnapshot : snapshot.child("choicesList").getChildren()) {
                        Choice choice = choiceSnapshot.getValue(Choice.class);

                        ChoiceCount = choice.getCount();
                        if (ChoiceCount > TopChoiceCount)
                        {
                            TopChoiceCount = ChoiceCount;
                            TopChoice = choice;
                        }
                        if (choice != null) {
                            choices.add(choice);
                        }
                    }

                    //topic.setChoicesList(choices);
                    topic.setChoicesList1(choices, TopChoice, TopChoiceCount);
                    topicsList.add(topic);
                }
                topicsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("MainActivity", "loadTopic:onCancelled", error.toException());
                Toast.makeText(MainActivity.this, "Failed to load topics.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addNewTopic(String topicDescription)
    {
        String topicID = databaseRef.push().getKey();
        if (topicID != null) {
            Topic newTopic = new Topic(topicID, topicDescription, new ArrayList<>());
            databaseRef.child(topicID).setValue(newTopic)
                    .addOnSuccessListener(aVoid -> {
                        newTopicEditText.setText(""); // Clear input field
                        Toast.makeText(MainActivity.this, "New topic added.", Toast.LENGTH_SHORT).show();
                        loadTopics(); // Refresh the topics list
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(MainActivity.this, "Failed to add new topic.", Toast.LENGTH_SHORT).show();
                        Log.e("MainActivity", "Failed to add topic: " + e.getMessage());
                    });
            Intent intent = new Intent(MainActivity.this, TopicsActivity.class);
            intent.putExtra("topicID", topicID);
            intent.putExtra("topicDescription", topicDescription);
            intent.putExtra("ActiveUsername", username);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Couldn't get a unique ID for the topic.", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to show a confirmation dialog for deleting a topic
    private void showDeleteDialog(Topic topic) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Topic")
                .setMessage("Are you sure you want to delete this topic?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteTopic(topic);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    // Method to delete a topic
    private void deleteTopic(Topic topic) {
        if (topic.getTopicID() != null) {
            databaseRef.child(topic.getTopicID()).removeValue()
                    .addOnSuccessListener(aVoid -> Toast.makeText(MainActivity.this, "Topic deleted.", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Failed to delete topic.", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "Error: Topic ID is missing.", Toast.LENGTH_SHORT).show();
        }
    }


    public void logout(View view)
    {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void displayProfile(View view)
    {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        intent.putExtra("ActiveUsername", username);
        startActivity(intent);
        finish();
    }
}