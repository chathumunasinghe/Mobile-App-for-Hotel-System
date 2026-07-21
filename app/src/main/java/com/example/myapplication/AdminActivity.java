package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdminActivity extends AppCompatActivity {
    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private CardView cardView4;
    private CardView cardView5;
    private CardView cardView6;
    private Button admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_panel); // This line links the XML file

            cardView1 = findViewById(R.id.card_view1);

            cardView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigate to the second activity
                    Intent intent = new Intent(AdminActivity.this, Activity1.class);
                    startActivity(intent);
                }
            });
        cardView2 = findViewById(R.id.card_view2);

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the second activity
                Intent intent = new Intent(AdminActivity.this, Activity2.class);
                startActivity(intent);
            }
        });
        cardView3 = findViewById(R.id.card_view3);

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the second activity
                Intent intent = new Intent(AdminActivity.this, Activity3.class);
                startActivity(intent);
            }
        });
        cardView4 = findViewById(R.id.card_view4);

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the second activity
                Intent intent = new Intent(AdminActivity.this, Activity4.class);
                startActivity(intent);
            }
        });
        cardView5 = findViewById(R.id.card_view5);

        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the second activity
                Intent intent = new Intent(AdminActivity.this, Activity5.class);
                startActivity(intent);
            }
        });
        cardView6 = findViewById(R.id.card_view6);

        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the second activity
                Intent intent = new Intent(AdminActivity.this, Activity6.class);
                startActivity(intent);
            }
        });
        admin = findViewById(R.id.Add);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AddActivity.class);
                startActivity(intent);

            }
        });

    }
}
