package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

public class FifteenActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OffersAdapter offersAdapter;
    private List<Offers> offersList;
    private SearchView searchView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifteen);

        recyclerView = findViewById(R.id.offers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        offersList = new ArrayList<>();
        offersAdapter = new OffersAdapter(offersList, this);
        recyclerView.setAdapter(offersAdapter);
        searchView = findViewById(R.id.search_view);
        searchView.setQueryHint("Search Offers");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // Handle submit if needed, otherwise return false
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterRooms(newText);
                return false;
            }
        });

        DatabaseReference offersRef = FirebaseDatabase.getInstance().getReference("Offers");

        offersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                offersList.clear();
                for (DataSnapshot offerSnapshot : dataSnapshot.getChildren()) {
                    Offers offers = offerSnapshot.getValue(Offers.class);
                    if (offers != null) {
                        offersList.add(offers);
                    } else {
                        Toast.makeText(FifteenActivity.this, "Error fetching room data", Toast.LENGTH_SHORT).show();
                    }
                }
                offersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FifteenActivity.this, "Error fetching room data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void filterRooms(String newText) {
        List<Offers> filteredList = new ArrayList<>();
        if (newText.isEmpty()) {
            filteredList.addAll(offersList); // Add all rooms back if search text is empty
        } else {
            for (Offers offer : offersList) {
                if (offer.name.toLowerCase().contains(newText.toLowerCase())) {
                    filteredList.add(offer);
                }
            }
        }
        offersAdapter.updateData(filteredList);
    }
}
