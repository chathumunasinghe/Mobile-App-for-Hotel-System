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

public class paymentAdapter extends RecyclerView.Adapter<paymentAdapter.ViewHolder> {

    private List<Payment> paymentList;
    private Context context;

    public paymentAdapter(List<Payment> paymentList, Context context) {
        this.paymentList = paymentList;
        this.context = context; // Assign the received context
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Payment> newPaymentList) {
        this. paymentList = newPaymentList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup
                                                 parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Payment payment = paymentList.get(position);
        holder.paymentHolder.setText(payment.cardHolderName);
        holder.paymentService.setText(payment.serviceName);
        holder.paymentPrice.setText(payment.servicePrice);
        holder.paymentCardNo.setText(payment.cardNumber);



    }


    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView
                paymentHolder, paymentService, paymentPrice,paymentCardNo;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            paymentHolder = itemView.findViewById(R.id.holder_name);
            paymentService= itemView.findViewById(R.id.serve_name);
            paymentPrice = itemView.findViewById(R.id.payment_price);
            paymentCardNo = itemView.findViewById(R.id.payment_card);

        }
    }
}

