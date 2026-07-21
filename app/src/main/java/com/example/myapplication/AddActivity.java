package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    private Button btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity); // Inflate the XML layout

        // Find view references by their IDs
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);

        // Set click listeners for buttons (optional)
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button 1 click (e.g., navigate to Add Services screen)
                Intent intent = new Intent(AddActivity.this, AdminActivity1.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button 1 click (e.g., navigate to Add Services screen)
                Intent intent = new Intent(AddActivity.this, AdminActivity2.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button 1 click (e.g., navigate to Add Services screen)
                Intent intent = new Intent(AddActivity.this, AdminActivity3.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button 1 click (e.g., navigate to Add Services screen)
                Intent intent = new Intent(AddActivity.this, AdminActivity4.class);
                startActivity(intent);
            }
        });
        // ... (set listeners for btn3 and btn4)
    }
}
