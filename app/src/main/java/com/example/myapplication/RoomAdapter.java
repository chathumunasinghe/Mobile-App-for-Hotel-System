package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private List<Room> roomList;
    private Context context;

    public RoomAdapter(List<Room> roomList, Context context) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.roomName.setText(room.name);

        holder.roomPrice.setText(room.Price);
        holder.roomDetails.setText(room.details);
        Glide.with(holder.itemView.getContext()).load(room.image).into(holder.roomImage);

        holder.bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle book now button click
                Intent intent = new Intent(context, TenActivity.class); // Replace with your next interface class name

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
        Button bookNowButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomName = itemView.findViewById(R.id.room_name);
            roomPrice = itemView.findViewById(R.id.room_price);
            roomDetails = itemView.findViewById(R.id.room_details);
            roomImage = itemView.findViewById(R.id.room_image);
            bookNowButton = itemView.findViewById(R.id.BookButton);
        }
    }
}
