package com.example.myapplication;

import static android.widget.Toast.LENGTH_SHORT;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Activity1 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private paymentAdapter paymentAdapter;
    private List<Payment> paymentList;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        recyclerView = findViewById(R.id.payment_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        paymentList = new ArrayList<>();
        paymentAdapter = new paymentAdapter(paymentList, this);
        recyclerView.setAdapter(paymentAdapter);

        searchView = findViewById(R.id.search_view);
        searchView.setQueryHint("Search Payments");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // Handle submit if needed, otherwise return false
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterPayments(newText);
                return false;
            }
        });

        DatabaseReference roomsRef = FirebaseDatabase.getInstance().getReference("payments");

        roomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                paymentList.clear();
                for (DataSnapshot paymentSnapshot : dataSnapshot.getChildren()) {
                    Payment payment = paymentSnapshot.getValue(Payment.class);
                    if (payment != null) {
                        paymentList.add(payment);
                    }
                }
                paymentAdapter.updateData(paymentList); // Update with complete list initially
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Activity1.this, "Error fetching room data: " + databaseError.getMessage(), LENGTH_SHORT).show();
            }
        });

    }

    private void filterPayments(String newText) {
        List<Payment> filteredList = new ArrayList<>();
        if (newText.isEmpty()) {
            filteredList.addAll(paymentList); // Add all rooms back if search text is empty
        } else {
            for (Payment payment : paymentList) {
                if (payment.cardHolderName.toLowerCase().contains(newText.toLowerCase())) {
                    filteredList.add(payment);
                }
            }
        }
        paymentAdapter.updateData(filteredList);
    }
}

