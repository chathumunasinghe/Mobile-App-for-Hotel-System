package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThirdActivity extends AppCompatActivity  {

    private EditText nameEditText, emailEditText, passwordEditText;
    private Button signUpButton;
    private TextView loginLink;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        // Initialize Firebase components
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");


        // Initialize views
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        signUpButton = findViewById(R.id.signUpButton);

        loginLink = findViewById(R.id.loginLink);

        // Set click listeners
        signUpButton.setOnClickListener(v -> createUserAccount());
        loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
            startActivity(intent);
        });
    }


    private void createUserAccount() {
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Validate user input (optional)
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a User object (optional, if you want structured data)
        // User user = new User(name, email, password);

        // Sign up with Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // User creation successful
                        String userId = mAuth.getCurrentUser().getUid();

                        // Save user data to Firebase Realtime Database (optional)
                        // You can store additional user information here
                        databaseReference.child(userId).setValue(name);

                        Toast.makeText(ThirdActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();

                        // After successful sign-up, navigate to the login screen or home screen
                        Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
                        startActivity(intent);
                    } else {
                        // Handle sign up failure
                        Toast.makeText(ThirdActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}