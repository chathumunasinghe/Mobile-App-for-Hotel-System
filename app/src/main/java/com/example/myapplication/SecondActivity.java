package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SecondActivity extends AppCompatActivity  {

    private EditText emailEditText, passwordEditText;
    private Button btnLogin;
    private TextView registerLink;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Initialize Firebase components
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        registerLink = findViewById(R.id.registerLink);
        progressBar = findViewById(R.id.progressBar);


        // Set click listeners
        btnLogin.setOnClickListener(v -> userLogin());
        registerLink.setOnClickListener(v -> {
            Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
            startActivity(intent);
        });


    }



    private void userLogin() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Validate user input (optional)
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // Sign in with Firebase Authentication
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login successful
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(SecondActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

                        // Navigate to the main activity or any other screen after successful login
                        // (replace with your desired activity)
                        Intent intent = new Intent(SecondActivity.this, FouthActivity.class);
                        startActivity(intent);
                    } else {
                        // Login failed
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(SecondActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
                    }
                });



    }
}