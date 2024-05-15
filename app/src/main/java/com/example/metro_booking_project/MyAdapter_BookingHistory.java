package com.example.metro_booking_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import android.view.View;



public class MyAdapter_BookingHistory extends RecyclerView.Adapter<MyViewHolder_BookingHistory> {
    Context context;
    List<Item> items;
    public MyAdapter_BookingHistory(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder_BookingHistory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*return new MyViewHolder_BookingHistory(LayoutInflater.from(context).inflate(R.layout.item_view_bookinghistory, parent, false));*/
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_bookinghistory, parent, false);
        return new MyViewHolder_BookingHistory(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder_BookingHistory holder, int position) {
        

        holder.nameView.setText(items.get(position).getName());
        holder.positionFromView.setText(items.get(position).getPosition_from());
        holder.positionToView.setText(items.get(position).getPosition_to());
        holder.timeView.setText(items.get(position).getTime());
        holder.dateView.setText(items.get(position).getDate());
        holder.imageView.setImageResource(items.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
