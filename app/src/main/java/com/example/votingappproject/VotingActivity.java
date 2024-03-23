package com.example.votingappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class VotingActivity extends AppCompatActivity {

        TextView Votes1, Votes2, Votes3, Votes4;
        Button Button1, Button2, Button3, Button4;
        int count1, count2, count3, count4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

        Votes1 = (TextView) findViewById(R.id.VoteText1);
        Votes2 = (TextView) findViewById(R.id.VoteText2);
        Votes3 = (TextView) findViewById(R.id.VoteText3);
        Votes4 = (TextView) findViewById(R.id.VoteText4);

        Button1 = (Button) findViewById(R.id.VoteButton1);
        Button2 = (Button) findViewById(R.id.VoteButton2);
        Button3 = (Button) findViewById(R.id.VoteButton3);
        Button4 = (Button) findViewById(R.id.VoteButton4);

        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count1++;
                Votes1.setText(String.valueOf(count1));
            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count2++;
                Votes2.setText(String.valueOf(count2));
            }
        });
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count3++;
                Votes3.setText(String.valueOf(count3));
            }
        });
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count4++;
                Votes4.setText(String.valueOf(count4));
            }
        });
    }
}