package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NinethActivity extends AppCompatActivity {

    private Button bookNowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nineth);


        bookNowButton = findViewById(R.id.bookNowButton);



        bookNowButton.setOnClickListener(v -> {
            // When "Book Now" button is clicked, start FifthActivity
            Intent intent = new Intent(NinethActivity.this, ConfirmationActivity.class);
            startActivity(intent);
        });
    }
}


