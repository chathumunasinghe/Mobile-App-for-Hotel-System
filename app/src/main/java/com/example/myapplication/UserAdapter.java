package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> userList;
    private Context context;

    public UserAdapter(List<User> usersList, Context context) {
        this.userList = usersList;
        this.context = context; // Assign the received context
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<User> newUsersList) {
        this. userList = newUsersList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup
                                                 parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User User = userList.get(position);
        holder.usersHolder.setText(User.name);
        holder.usersEmail.setText(User.email);
        holder.usersPassword.setText(User.password);

    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView
                usersHolder, usersEmail,usersPassword;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usersHolder = itemView.findViewById(R.id.name);
            usersEmail= itemView.findViewById(R.id.email);
            usersPassword = itemView.findViewById(R.id.password);
        }
    }
}

