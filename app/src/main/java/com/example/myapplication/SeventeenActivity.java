package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity; // Import Activity class
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// Consider using a custom User class instead of Firebase Auth's User
// public class User {
//     private String name;
//     private String email;
//     private String password;
//
//     // Getters and setters for name, email, password
// }

public class SeventeenActivity extends AppCompatActivity {

    private EditText nameEditText,  emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seventeen);

        // Get user data (consider using a custom User class for safety)
        Intent intent = getIntent();
        String name = intent.getStringExtra("name"); // Assuming "name" is the key
        String email = intent.getStringExtra("email"); // Assuming "email" is the key
        String password = intent.getStringExtra("password"); // Assuming "password" is the key

        // Initialize views
        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);

        // Pre-populate fields if data is available (handle null cases)
        if (name != null) {
            nameEditText.setText(name);
        }
        if (email != null) {
            emailEditText.setText(email);
        }
        if (password != null) {
            passwordEditText.setText(password);
        }

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update user data
                String updatedName = nameEditText.getText().toString();
                String updatedEmail = emailEditText.getText().toString();
                String updatedPassword = passwordEditText.getText().toString();

                // Validation (consider using input validation library)
                if (updatedName.isEmpty() || updatedEmail.isEmpty() || updatedPassword.isEmpty()) {
                    Toast.makeText(SeventeenActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update data in database or local storage (implementation details omitted)
                // ...

                // Inform the user of success (consider using a more informative message)
                Toast.makeText(SeventeenActivity.this, "User data updated successfully", Toast.LENGTH_SHORT).show();

                // Finish or navigate back based on your logic
                finish();
            }
        });
    }
}