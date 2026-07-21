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

public class Activity3 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private roAdapter roAdapter;
    private List<Room> roomList;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        recyclerView = findViewById(R.id.room_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        roomList = new ArrayList<>();
        roAdapter = new roAdapter(roomList, this);
        recyclerView.setAdapter(roAdapter);

        searchView = findViewById(R.id.search_view);
        searchView.setQueryHint("Search Rooms");

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

        DatabaseReference roomsRef = FirebaseDatabase.getInstance().getReference("Rooms");

        roomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                roomList.clear();
                for (DataSnapshot roomSnapshot : dataSnapshot.getChildren()) {
                    Room room = roomSnapshot.getValue(Room.class);
                    if (room != null) {
                        roomList.add(room);
                    }
                }
                roAdapter.updateData(roomList); // Update with complete list initially
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Activity3.this, "Error fetching room data: " + databaseError.getMessage(), LENGTH_SHORT).show();
            }
        });

    }

    private void filterRooms(String newText) {
        List<Room> filteredList = new ArrayList<>();
        if (newText.isEmpty()) {
            filteredList.addAll(roomList); // Add all rooms back if search text is empty
        } else {
            for (Room room : roomList) {
                if (room.name.toLowerCase().contains(newText.toLowerCase())) {
                    filteredList.add(room);
                }
            }
        }
        roAdapter.updateData(filteredList);
    }
}
