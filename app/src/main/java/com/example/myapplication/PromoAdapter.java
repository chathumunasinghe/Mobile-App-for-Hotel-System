package com.example.myapplication;

import static androidx.core.content.ContextCompat.startActivity;

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

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.ViewHolder> {

    private List<Promotion> promotionList;
    private Context context;
    public PromoAdapter(List<Promotion> promotionList) {
        this.promotionList = promotionList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Promotion> promotionList) {
        this. promotionList = promotionList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup
                                                 parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_promotion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Promotion promotion = promotionList.get(position);
        holder.roomName.setText(promotion.name);


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Promotion promotion = promotionList.get(position);

                // Get a reference to the specific room in the database
                DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference("Promotions").child(promotion.name);

                // Delete the room from the database
                roomRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Room deleted successfully, remove from local list and notify adapter
                            promotionList.remove(position);
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
                Promotion promotion = promotionList.get(holder.getAdapterPosition());
                Intent intent = new Intent(context, EditPromotionActivity.class);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return promotionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView
                roomName;

        Button deleteButton,editButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomName = itemView.findViewById(R.id.promotion_name);
            deleteButton = itemView.findViewById(R.id.delete);
            editButton =itemView.findViewById(R.id.edit);
        }
    }
}
