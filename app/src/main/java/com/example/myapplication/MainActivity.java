package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;





public class MainActivity extends AppCompatActivity {
    private boolean isMainLayout = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize button
        Button continueButton = findViewById(R.id.continueButton);

        // Set button click listener
        continueButton.setOnClickListener(v -> {
            if (isMainLayout) {
                // Show a toast message
                Toast.makeText(MainActivity.this, "Continue button clicked!", Toast.LENGTH_SHORT).show();

                // Switch to the second layout

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            } else {
                // Optionally, you can add logic to go back to the main layout if needed
                setContentView(R.layout.activity_main);
                isMainLayout = true; // Reset flag to indicate main layout is active again
            }


        });
    }
    }
