package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditRoomActivity extends AppCompatActivity {

    private EditText roomNameEditText, roomPriceEditText, roomDetailsEditText,roomImageEditText;
    private Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);

        roomNameEditText = findViewById(R.id.room_name_edit);
        roomPriceEditText = findViewById(R.id.room_price_edit);
        roomDetailsEditText = findViewById(R.id.room_details_edit);
        roomImageEditText = findViewById(R.id.room_image_edit);
        saveButton = findViewById(R.id.save_button);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRoomDetails();
            }
        });
    }

    private void updateRoomDetails() {

        String roomName =  roomNameEditText.getText().toString().trim();
        String roomPrice = roomPriceEditText.getText().toString().trim();
        String roomDetails = roomDetailsEditText.getText().toString().trim();
        String roomImage = roomImageEditText.getText().toString().trim();



        if (TextUtils.isEmpty(roomName)) {
            Toast.makeText(this, "Room Name is required", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Rooms").child(roomName);

        Map<String, Object> updatedValues = new HashMap<>();
        if (!TextUtils.isEmpty(roomName)) updatedValues.put("name", roomName);
        if (!TextUtils.isEmpty(roomDetails)) updatedValues.put("details", roomDetails);
        if (!TextUtils.isEmpty(roomImage)) updatedValues.put("image", roomImage);
        if (!TextUtils.isEmpty(roomPrice)) updatedValues.put("Price", roomPrice);


        databaseReference.updateChildren(updatedValues).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(EditRoomActivity.this, "Room details updated successfully!", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(EditRoomActivity.this, "Failed to update: " + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        roomNameEditText.setText("");
        roomPriceEditText.setText("");
        roomDetailsEditText.setText("");
        roomImageEditText.setText("");

    }
}