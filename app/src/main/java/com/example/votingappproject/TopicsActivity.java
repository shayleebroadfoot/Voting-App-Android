package com.example.votingappproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.votingappproject.Model.Choice;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TopicsActivity extends AppCompatActivity {

    private DatabaseReference databaseReference_choices;
    private DatabaseReference databaseReference_users;
    private String topicID;
    private String topicDescription;
    private String username;
    private ArrayAdapter<Choice> adapter;
    private ArrayList<Choice> choicesList = new ArrayList<>();
    private EditText choiceDescriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        topicID = getIntent().getStringExtra("topicID");
        topicDescription = getIntent().getStringExtra("topicDescription");
        username = getIntent().getStringExtra("ActiveUsername");

        if (topicID == null || topicID.trim().isEmpty()) {
            Toast.makeText(this, "No topic ID found.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        TextView topicDescriptionTextView = findViewById(R.id.topicDescriptionTextView);
        topicDescriptionTextView.setText(topicDescription);

        databaseReference_choices = FirebaseDatabase.getInstance().getReference("Topics").child(topicID).child("choicesList");
        databaseReference_users = FirebaseDatabase.getInstance().getReference("Users").child(username);

        ListView choicesListView = findViewById(R.id.choicesListView);
        choiceDescriptionEditText = findViewById(R.id.newChoiceEditText);
        Button addChoiceButton = findViewById(R.id.addChoiceButton);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, choicesList);
        choicesListView.setAdapter(adapter);

        loadChoices();

        addChoiceButton.setOnClickListener(view -> addNewChoice());

        choicesListView.setOnItemClickListener((parent, view, position, id) -> {
            Choice selectedChoice = choicesList.get(position);
            String choiceID = selectedChoice.getChoiceID();

            DatabaseReference userRef = databaseReference_users.child("selectedTopicsIds").child(topicID);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        // The user has not voted on this topic yet, proceed with updating
                        voteOnChoice(choiceID, selectedChoice);
                    } else {
                        // The user has already voted on this topic
                        Toast.makeText(TopicsActivity.this, "You have already voted on this topic.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(TopicsActivity.this, "Failed to check if user has voted.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void voteOnChoice(String choiceID, Choice selectedChoice)
    {
        int newCount = selectedChoice.getCount() + 1;
        // Increment choice count
        databaseReference_choices.child(choiceID).child("count").setValue(newCount)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(TopicsActivity.this, "Voted for " + selectedChoice.getDescription(), Toast.LENGTH_SHORT).show();
                    loadChoices(); // Refresh data to display updated count

                    // Add topic ID to user's voted topics list
                    databaseReference_users.child("selectedTopicsIds").child(topicID).setValue(true)
                            .addOnSuccessListener(aVoid2 -> Toast.makeText(TopicsActivity.this, "Thanks for voting!", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(TopicsActivity.this, "Error updating your voted topics.", Toast.LENGTH_SHORT).show());
                })
                .addOnFailureListener(e -> Toast.makeText(TopicsActivity.this, "Error updating choice count.", Toast.LENGTH_SHORT).show());
    }

    private void loadChoices() {
        databaseReference_choices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                choicesList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Choice choice = snapshot.getValue(Choice.class);
                    if (choice != null) {
                        choicesList.add(choice);
                    }
                }
                adapter.notifyDataSetChanged(); // Refresh ListView with the updated list
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TopicsActivity.this, "Failed to load choices.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addNewChoice() {
        String description = choiceDescriptionEditText.getText().toString();
        if (!description.isEmpty()) {
            String choiceID = databaseReference_choices.push().getKey();
            Choice newChoice = new Choice(0, choiceID, description);
            if (choiceID != null) {
                databaseReference_choices.child(choiceID).setValue(newChoice)
                        .addOnSuccessListener(aVoid -> {
                            choiceDescriptionEditText.setText("");
                            loadChoices(); // Refresh choices list to include the new choice
                        })
                        .addOnFailureListener(e -> Toast.makeText(TopicsActivity.this, "Failed to add choice.", Toast.LENGTH_SHORT).show());
            }
        } else {
            Toast.makeText(TopicsActivity.this, "Description cannot be empty.", Toast.LENGTH_SHORT).show();
        }
    }

    public void displayDashboard(View view) {
        Intent intent = new Intent(TopicsActivity.this, MainActivity.class);
        intent.putExtra("ActiveUsername", username);
        startActivity(intent);
        finish();
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
