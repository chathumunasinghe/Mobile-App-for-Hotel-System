package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnLogout;
    private Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Reference the Logout button
        btnLogout = findViewById(R.id.btnLogout);

        // Set OnClickListener for the Logout button
        btnLogout.setOnClickListener(v -> {
            // Log out the user
            if (mAuth != null) {
                mAuth.signOut();
                Toast.makeText(UserActivity.this, "User Logged Out", Toast.LENGTH_SHORT).show();

                // Navigate to LoginActivity
                Intent intent = new Intent(UserActivity.this, SecondActivity.class);
                startActivity(intent);
                finish(); // Close MainActivity
            } else {
                Toast.makeText(UserActivity.this, "Error: FirebaseAuth not initialized", Toast.LENGTH_SHORT).show();
            }
        });

        editButton = findViewById(R.id.edit_profile_button);

        editButton.setOnClickListener(v -> {

            Intent intent = new Intent(UserActivity.this, SeventeenActivity.class);
            startActivity(intent);
        });
    }
}
