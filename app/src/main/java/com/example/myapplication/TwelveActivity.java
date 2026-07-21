package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TwelveActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twelve); // Replace with your actual layout file name

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText); // Replace with your EditText ID
        passwordEditText = findViewById(R.id.passwordEditText); // Replace with your EditText ID
        loginButton = findViewById(R.id.loginButton); // Replace with your Button ID

        // Initialize Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Admin");

        // Set click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(TwelveActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String  storedEmail = dataSnapshot.child("email").getValue(String.class);
                            String storedPassword = dataSnapshot.child("password").getValue(String.class);

                            if (email.equals(storedEmail) && password.equals(storedPassword)) {
                                // Login successful, navigate to the next screen
                                Intent intent = new Intent(TwelveActivity.this, AdminActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(TwelveActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(TwelveActivity.this, "Admin credentials not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("LoginActivity", "Error retrieving data: " + error.getMessage());
                        Toast.makeText(TwelveActivity.this, "Error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}