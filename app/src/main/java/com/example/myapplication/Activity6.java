package com.example.myapplication;

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

public class Activity6 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ServiAdapter serviAdapter;
    private List<Service> serviceList;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servi);

        recyclerView = findViewById(R.id.service_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        serviceList = new ArrayList<>();
        serviAdapter = new ServiAdapter(serviceList, this);

        recyclerView.setAdapter(serviAdapter);

        searchView = findViewById(R.id.search_view);
        searchView.setQueryHint("Search Services");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return
                        false; // Handle submit if needed, otherwise return false
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterServices(newText);
                return false;
            }
        });

        DatabaseReference servicesRef = FirebaseDatabase.getInstance().getReference("Services");

        servicesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                serviceList.clear();
                for (DataSnapshot serviceSnapshot : dataSnapshot.getChildren()) {
                    Service service = serviceSnapshot.getValue(Service.class);
                    if (service != null) {
                        serviceList.add(service);
                    }
                }
                serviAdapter.updateData(serviceList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Activity6.this, "Error fetching room data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void filterServices(String newText) {
        List<Service> filteredList = new ArrayList<>();
        if (newText.isEmpty()) {
            filteredList.addAll(serviceList);
        } else {
            for (Service service : serviceList) {
                if (service.name.toLowerCase().contains(newText.toLowerCase())) {
                    filteredList.add(service);
                }
            }
        }
        serviAdapter.updateData(filteredList);
    }
}
