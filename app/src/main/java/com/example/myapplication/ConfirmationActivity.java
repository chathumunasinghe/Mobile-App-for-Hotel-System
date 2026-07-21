package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

public class ConfirmationActivity extends AppCompatActivity {
    private Button bookNowButton;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_confirm);

        bookNowButton = findViewById(R.id.btn1);
        cancel =findViewById(R.id.btn2);


        bookNowButton.setOnClickListener(v -> {

            Intent intent = new Intent(ConfirmationActivity.this, TenActivity.class);
            startActivity(intent);
        });
        cancel.setOnClickListener(v -> {

            Intent intent = new Intent(ConfirmationActivity.this, FouthActivity.class);
            startActivity(intent);
        });
    }


    }

