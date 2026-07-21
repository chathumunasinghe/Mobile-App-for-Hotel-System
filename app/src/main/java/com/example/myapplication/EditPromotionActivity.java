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

public class EditPromotionActivity extends AppCompatActivity {

    private EditText promotionNameEdit;
    private Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_promotion);

        promotionNameEdit = findViewById(R.id.promotionNameEdit);
        saveButton = findViewById(R.id.saveButton);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePromotionDetails();
            }
        });
    }

    private void updatePromotionDetails() {

        String name =  promotionNameEdit.getText().toString().trim();


        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Room Name is required", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Promotions").child(name);

        Map<String, Object> updatedValues = new HashMap<>();
        if (!TextUtils.isEmpty(name)) updatedValues.put("name", name);


        databaseReference.updateChildren(updatedValues).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(EditPromotionActivity.this, "Promotion details updated successfully!", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(EditPromotionActivity.this, "Failed to update: " + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        promotionNameEdit.setText("");

    }
}
