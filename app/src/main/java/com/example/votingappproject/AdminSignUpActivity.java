package com.example.votingappproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.votingappproject.Model.Admin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class AdminSignUpActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference adminRef;
    private String verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_up);

        // Initialize Firebase database reference
        database = FirebaseDatabase.getInstance("https://votingapp-6475d-default-rtdb.firebaseio.com/");
        adminRef = database.getReference("Admins");
    }

    public void createAdmin(View view) {
        EditText emailEditText = findViewById(R.id.text_email);
        EditText usernameEditText = findViewById(R.id.text_username);
        EditText passwordEditText = findViewById(R.id.text_password);

        final String email = emailEditText.getText().toString().trim();
        final String username = usernameEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString();

        if (!email.endsWith("@tru.com")) {
            showToast("Please enter a valid TRU email address.");
            return;
        }

        if (username.isEmpty()) {
            showToast("Username cannot be empty.");
            return;
        }

        if (password.isEmpty() || password.length() < 4) {
            showToast("Password must be at least 4 characters long.");
            return;
        }

        adminRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    showToast("Username already exists.");
                } else {
                    verificationCode = generateVerificationCode();
                    sendVerificationEmail(email); // Updated to not include username or password
                    promptForVerificationCode(email, username, password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("AdminSignupActivity", "Database error: " + databaseError.getMessage());
                showToast("Database error. Please try again.");
            }
        });
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Generates a 6-digit code
        return String.valueOf(code);
    }

    private void sendVerificationEmail(String recipientEmail) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipientEmail});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Verify Your Admin Account");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Your verification code is: " + verificationCode + "\nPlease enter this code in the app to verify your account.");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            showToast("There are no email clients installed.");
        }
    }

    private void promptForVerificationCode(final String email, final String username, final String password) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Verification Code");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("Verify", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String enteredCode = input.getText().toString();
                if (verificationCode.equals(enteredCode)) {
                    Admin newAdmin = new Admin(email, username, password);
                    adminRef.child(username).setValue(newAdmin).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            showToast("Admin account created successfully.");
                            goToDashboard(username);
                        } else {
                            showToast("Failed to create admin account. Please try again.");
                        }
                    });
                } else {
                    showToast("Incorrect verification code.");
                }
            }
        });
        builder.show();
    }

    private void goToDashboard(String username) {
        Intent intent = new Intent(AdminSignUpActivity.this, MainActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
