package com.example.votingappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votingappproject.Model.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VotingActivity extends AppCompatActivity {

    FirebaseDatabase database;
    private List<Task> pollList = new ArrayList<Task>();
    ArrayAdapter<Task> itemsAdapter;
    int initialID = 0;
    DatabaseReference databaseReference_polls;
    ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemsAdapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, pollList);
        listView = (ListView) findViewById(R.id.simpleListView);
        listView.setAdapter(itemsAdapter);

        EditText editText = findViewById(R.id.taskDescription);
        editText.setTextColor(getResources().getColor(R.color.alice));
    }

    public void addTask(View view) {
// Read the txt from the input text
        @SuppressLint("WrongViewCast")
        EditText taskEditText = findViewById(R.id.taskDescription);
        String taskDescription = taskEditText.getText().toString();
        int count1 =0;

        initialID++;

        Task newTask = new Task (count1, String.valueOf(initialID), taskDescription);
// Login to Firebase project and get instance of the DB and point to the root node of the DB
        database = FirebaseDatabase.getInstance("https://votingapp-6475d-default-rtdb.firebaseio.com/");
// Set reference to the Tasks table
        databaseReference_polls = database.getReference("Polls");
        databaseReference_polls.child(newTask.toString()).setValue(newTask);

        databaseReference_polls = database.getReference("Polls");
        databaseReference_polls.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //Check if data read correctly and display message
                    Toast.makeText(VotingActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    //Looping over all children
                    for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                        Task task = taskSnapshot.getValue(Task.class);

                        itemsAdapter.add(task);
                        //Logging the data from database
                        Log.d("Voting activity", "Task value is: " + task.getDescription());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Voting activity", "Failed to read value.", error.toException());
            }
        });
    }
}