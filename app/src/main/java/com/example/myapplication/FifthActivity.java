package com.example.myapplication;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class FifthActivity extends AppCompatActivity {

    private ImageView seaViewImageView, familyLuxuryImageView, honeymoonLuxuryImageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);

        // Find references to the ImageViews
        seaViewImageView = findViewById(R.id.sea_view_image); // Replace with your actual ID
        familyLuxuryImageView = findViewById(R.id.family_luxury_image); // Replace with your actual ID
        honeymoonLuxuryImageView = findViewById(R.id.honeymoon_luxury_image); // Replace with your actual ID

        seaViewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FifthActivity.this, SixthActivity.class);
                startActivity(intent);

            }
        });
        familyLuxuryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FifthActivity.this, SeventhActivity.class);
                startActivity(intent);
            }
        });
        honeymoonLuxuryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FifthActivity.this, EightActivity.class);
                startActivity(intent);
            }
        });
    }


}