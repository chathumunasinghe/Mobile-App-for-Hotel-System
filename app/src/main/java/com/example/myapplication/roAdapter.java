package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class roAdapter extends RecyclerView.Adapter<roAdapter.ViewHolder> {

    private List<Room> roomList;
    private Context context;

    public roAdapter(List<Room> roomList, Context context) {
        this.roomList = roomList;
        this.context = context; // Assign the received context
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Room> newRoomList) {
        this. roomList = newRoomList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup
                                                 parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.roomName.setText(room.name);

        holder.roomPrice.setText(room.Price);
        holder.roomDetails.setText(room.details);
        Glide.with(holder.itemView.getContext()).load(room.image).into(holder.roomImage);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Room room = roomList.get(position);

                // Get a reference to the specific room in the database
                DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference("Rooms").child(room.name);

                // Delete the room from the database
                roomRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Room deleted successfully, remove from local list and notify adapter
                            roomList.remove(position);
                            notifyItemRemoved(position);
                        } else {
                            // Handle deletion failure (e.g., show error message)
                            Log.e("Firebase", "Error deleting room: " + task.getException());
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
                Room room = roomList.get(holder.getAdapterPosition());
                Intent intent = new Intent(context, EditRoomActivity.class);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView
                roomName, roomPrice, roomDetails;
        ImageView roomImage;
        Button deleteButton,editButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomName = itemView.findViewById(R.id.room_name);
            roomPrice = itemView.findViewById(R.id.room_price);
            roomDetails = itemView.findViewById(R.id.room_details);
            roomImage = itemView.findViewById(R.id.room_image);
            deleteButton = itemView.findViewById(R.id.delete);
            editButton =itemView.findViewById(R.id.edit);
        }
    }
}
