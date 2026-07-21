package com.example.myapplication;

import static android.widget.Toast.LENGTH_SHORT;
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

public class Activity2 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter UserAdapter;
    private List<User> usersList;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        recyclerView = findViewById(R.id.users_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        usersList = new ArrayList<>();
        UserAdapter = new UserAdapter(usersList, this);
        recyclerView.setAdapter(UserAdapter);

        searchView = findViewById(R.id.search_view);
        searchView.setQueryHint("Search users");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // Handle submit if needed, otherwise return false
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterUsers(newText);
                return false;
            }
        });

        DatabaseReference roomsRef = FirebaseDatabase.getInstance().getReference("users");

        roomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User User = userSnapshot.getValue(User.class);
                    if (User != null) {
                        usersList.add(User);
                    }
                }
                UserAdapter.updateData(usersList); // Update with complete list initially
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Activity2.this, "Error fetching room data: " + databaseError.getMessage(), LENGTH_SHORT).show();
            }
        });

    }

    private void filterUsers(String newText) {
        List<User> filteredList = new ArrayList<>();
        if (newText.isEmpty()) {
            filteredList.addAll(usersList); // Add all rooms back if search text is empty
        } else {
            for (User User : usersList) {
                if (User.name.toLowerCase().contains(newText.toLowerCase())) {
                    filteredList.add(User);
                }
            }
        }
        UserAdapter.updateData(filteredList);
    }
}

