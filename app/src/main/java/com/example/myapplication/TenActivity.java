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

public class TenActivity extends AppCompatActivity {

    // Declare UI elements
    private EditText serviceNameEditText, servicePriceEditText, cardNumberEditText, expirationDateEditText, cvvEditText, cardHolderNameEditText;
    private Button confirmPaymentButton;
    private DatabaseReference databaseReference;

    // Firebase Database reference


    private static final String CHANNEL_ID = "payment_notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ten);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("payments");

        serviceNameEditText = findViewById(R.id.service_name);
        servicePriceEditText = findViewById(R.id.service_price);
        cardNumberEditText = findViewById(R.id.cardNumberEditText);
        expirationDateEditText = findViewById(R.id.expirationDateEditText);
        cvvEditText = findViewById(R.id.cvvEditText);
        cardHolderNameEditText = findViewById(R.id.cardHolderNameEditText);
        confirmPaymentButton = findViewById(R.id.confirmPaymentButton);
        View backButton = findViewById(R.id.backBtn);

        // Set click listener for the Add Movie button
        confirmPaymentButton.setOnClickListener(v -> addPaymentToDatabase());

        // Set click listener for the Back button

        backButton.setOnClickListener(v -> finish()); // Close the current activity

        // Ensure notification permission is requested if needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        // Create a notification channel for Android 8.0 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Payment Notifications", NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Notifications for new payment added");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void addPaymentToDatabase() {
        // Get user input
        String serviceName = serviceNameEditText.getText().toString().trim();
        String servicePrice = servicePriceEditText.getText().toString().trim();
        String cardNumber = cardNumberEditText.getText().toString().trim();
        String expirationDate = expirationDateEditText.getText().toString().trim();
        String cvv = cvvEditText.getText().toString().trim();
        String cardHolderName = cardHolderNameEditText.getText().toString().trim();

        // Validate input
        if (TextUtils.isEmpty(serviceName)) {
            Toast.makeText(this, "Please enter a service name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(servicePrice)) {
            Toast.makeText(this, "Please enter the service price", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(cardNumber)) {
            Toast.makeText(this, "Please enter the card number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty( expirationDate)) {
            Toast.makeText(this, "Please enter the expiry date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(cvv)) {
            Toast.makeText(this, "Please enter the cvv", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(cardHolderName)) {
            Toast.makeText(this, "Please enter the cardHolderName", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate a unique key for each movie
        String movieId = databaseReference.push().getKey();

        // Create a movie object
        PaymentData payment = new PaymentData(serviceName, servicePrice, cardNumber, expirationDate,cvv,cardHolderName);

        // Add to Firebase Database
        databaseReference.child(serviceName).setValue(payment)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Payment added successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TenActivity.this, FouthActivity.class);
                    startActivity(intent);
                    sendNotification(serviceName, servicePrice); // Send notification when the movie is added
                    clearInputs(); // Clear inputs after movie is added
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to add payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void sendNotification(String serviceName, String  servicePrice) {
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
                    .setContentTitle("New Payment Added: " + serviceName)
                    .setContentText("Price: $" + servicePrice)
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
        serviceNameEditText.setText("");
        servicePriceEditText.setText("");
        cardNumberEditText.setText("");
        expirationDateEditText.setText("");
        cvvEditText.setText("");
        cardHolderNameEditText.setText("");
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

