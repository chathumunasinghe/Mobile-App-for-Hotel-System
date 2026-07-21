package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FouthActivity extends AppCompatActivity {
    private boolean isMainLayout = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fouth);

        // Initialize button
        Button seeAllButton = findViewById(R.id.see_all);

// Initialize Firebase Authentication
        seeAllButton.setOnClickListener(v -> {

            if (isMainLayout) {

                // Show a toast message

                Toast.makeText(FouthActivity.this, "See All button clicked!", Toast.LENGTH_SHORT).show();

                // Switch to the second layout

                Intent intent = new Intent(FouthActivity.this, FifthActivity.class);

                startActivity(intent);

            } else {

                // Optionally, you can add logic to go back to the main layout if needed

                setContentView(R.layout.activity_fouth);

                isMainLayout = true; // Reset flag to indicate main layout is active again

            }

        });
        ImageView hotelImageView = findViewById(R.id.hotelImg);

        hotelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FouthActivity.this, NinethActivity.class);
                startActivity(intent);

            }
        });
        ImageView userImageView = findViewById(R.id.user);

        userImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FouthActivity.this, UserActivity.class);
                startActivity(intent);

            }
        });
        ImageView smsImageView = findViewById(R.id.sms);

        smsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FouthActivity.this, SixteenActivity.class);
                startActivity(intent);

            }
        });
        ImageView calenderImageView = findViewById(R.id.calender);

        calenderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FouthActivity.this, EighteenteenActivity.class);
                startActivity(intent);

            }
        });


        Button roombutton = findViewById(R.id.room);
        roombutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FouthActivity.this, ThirteenActivity.class);
                startActivity(intent);

            }
        });
        Button servicebutton = findViewById(R.id.Services);
        servicebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FouthActivity.this, FourteenActivity.class);
                startActivity(intent);

            }
        });
        Button offerbutton = findViewById(R.id.Offers);
        offerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FouthActivity.this, FifteenActivity.class);
                startActivity(intent);

            }
        });
    }
}