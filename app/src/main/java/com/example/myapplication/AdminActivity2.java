package com.example.myapplication;



import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity2 extends AppCompatActivity {

    // Declare UI elements
    private EditText nameEditText, priceEditText, imageEditText, detailsEditText;
    private Button btnAdd;
    private DatabaseReference databaseReference;

    // Firebase Database reference


    private static final String CHANNEL_ID = "Service_notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_ac2);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Rooms");

        nameEditText = findViewById(R.id.name);
        priceEditText = findViewById(R.id.price);
        imageEditText = findViewById(R.id.image);
        detailsEditText = findViewById(R.id.details);
        btnAdd = findViewById(R.id.btnAdd);


        // Set click listener for the Add Movie button
        btnAdd.setOnClickListener(v -> addServiceToDatabase());

        // Set click listener for the Back button



        // Ensure notification permission is requested if needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        // Create a notification channel for Android 8.0 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Room Notifications", NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Notifications for new room added");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void addServiceToDatabase() {
        // Get user input
        String name = nameEditText.getText().toString().trim();
        String Price = priceEditText.getText().toString().trim();
        String details = detailsEditText.getText().toString().trim();
        String image = imageEditText.getText().toString().trim();

        // Validate input
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter a  name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Price)) {
            Toast.makeText(this, "Please enter the  price", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(details)) {
            Toast.makeText(this, "Please enter the details", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(image)) {
            Toast.makeText(this, "Please enter the image", Toast.LENGTH_SHORT).show();
            return;
        }


        // Generate a unique key for each
        String Id = databaseReference.push().getKey();

        // Create a movie object
        ServiceData Service = new ServiceData(name, Price, details, image);

        // Add to Firebase Database
        databaseReference.child(name).setValue(Service)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Room added successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminActivity2.this, AdminActivity.class);
                    startActivity(intent);
                    sendNotification(name, Price); // Send notification when the movie is added
                    clearInputs(); // Clear inputs after movie is added
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to add room: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void sendNotification(String name, String  Price) {
        // Check if permission is granted before sending the notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // If permission is not granted, skip sending the notification and log it
                Toast.makeText(this, "Notification permission denied. No notification sent.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        try {
            // Build the notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.noti) // Replace with your app icon
                    .setContentTitle("New Room Added: " + name)
                    .setContentText("Price: $" + Price)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            // Show the notification
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(0, builder.build());

        } catch (SecurityException e) {
            // Handle the case where the permission is denied
            Toast.makeText(this, "Error sending notification: Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearInputs() {
        // Clear the input fields after payment is added
        nameEditText.setText("");
        priceEditText.setText("");
        detailsEditText.setText("");
        imageEditText.setText("");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, show a success message
                Toast.makeText(this, "Notification permission granted.", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied, show a message
                Toast.makeText(this, "Notification permission denied. You will not receive notifications.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

