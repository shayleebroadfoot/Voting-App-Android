package com.example.votingappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity { // Hey you!!
    //private EditText textUser;
    //private EditText textPw;
    private Button bContinue;
    //private TextView loginStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //textUser =(EditText) findViewById(R.id.userName);
        //textPw = (EditText) findViewById(R.id.password);
        bContinue = (Button) findViewById(R.id.btnContinue);
        //loginStatus = (TextView) findViewById(R.id.lgStat);

        bContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
                //finish();
//                String un = "Rsol";
//                String pw = "user";
//
//                if (un.equals(textUser.getText().toString()) && pw.equals(textPw.getText().toString())){
//
//                    Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}