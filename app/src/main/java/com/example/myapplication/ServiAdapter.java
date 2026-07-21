package com.example.myapplication;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ServiAdapter extends RecyclerView.Adapter<ServiAdapter.ViewHolder> {

    private List<Service> serviceList;
    private Context context;

    public ServiAdapter(List<Service> serviceList, Context context) {
        this.serviceList = serviceList;
        this.context = context;
    }
    public void updateData(List<Service> newServiceList) {
        this.serviceList = newServiceList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup
                                                 parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.servi_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service = serviceList.get(position);
        holder.serviceName.setText(service.name);

        holder.servicePrice.setText(service.Price);
        holder.serviceDetails.setText(service.details);
        Glide.with(holder.itemView.getContext()).load(service.image).into(holder.serviceImage);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Service room = serviceList.get(position);

                // Get a reference to the specific room in the database
                DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference("Services").child(room.name);

                // Delete the room from the database
                roomRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Room deleted successfully, remove from local list and notify adapter
                            serviceList.remove(position);
                            notifyItemRemoved(position);
                        } else {
                            // Handle deletion failure (e.g., show error message)
                            Log.e("Firebase", "Error deleting service: " + task.getException());
                        }
                    }
                });
            }
        });
        // Inside your RecyclerView adapter
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current room data from the adapter position
                Service room = serviceList.get(holder.getAdapterPosition());
                Intent intent = new Intent(context, EditServiceActivity.class);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView
                serviceName, servicePrice, serviceDetails;
        ImageView serviceImage;
        Button deleteButton,editButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.service_name);
            servicePrice = itemView.findViewById(R.id.service_price);
            serviceDetails = itemView.findViewById(R.id.service_details);
            serviceImage = itemView.findViewById(R.id.service_image);
            deleteButton = itemView.findViewById(R.id.delete);
            editButton =itemView.findViewById(R.id.edit);

        }
    }
}

