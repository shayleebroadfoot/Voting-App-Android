package com.example.votingappproject;

import static com.example.votingappproject.R.id.txtlogs;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//Test 1
public class LoginActivity extends AppCompatActivity {

    TextView txtSignUp;
    private EditText textUser;
    private EditText textPw;
    private Button lgIn;
    private TextView loginStatus;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtSignUp = findViewById(R.id.txtlogs);
        textUser =(EditText) findViewById(R.id.userName);
        textPw = (EditText) findViewById(R.id.password);

        lgIn = (Button) findViewById(R.id.btnDisplay);

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                //startActivity(intent);
                //finish();
                String un = "Rsol";
                String pw = "user";

                if (un.equals(textUser.getText().toString()) && pw.equals(textPw.getText().toString())){

                    Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}