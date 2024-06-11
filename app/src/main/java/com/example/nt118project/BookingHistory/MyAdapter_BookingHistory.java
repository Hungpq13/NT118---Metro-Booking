package com.example.nt118project.BookingHistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.R;

import java.util.List;

public class MyAdapter_BookingHistory extends RecyclerView.Adapter<MyViewHolder_BookingHistory> {

    public MyAdapter_BookingHistory(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    Context context;
    List<Item> items;
    @NonNull
    @Override
    public MyViewHolder_BookingHistory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder_BookingHistory(LayoutInflater.from(context).inflate(R.layout.item_view_bookinghistory, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder_BookingHistory holder, int position) {
        holder.nameView.setText(items.get(position).getName());
        holder.positionFromView.setText(items.get(position).getName());
        holder.positionToView.setText(items.get(position).getName());
        holder.timeView.setText(items.get(position).getName());
        holder.dateView.setText(items.get(position).getName());
        holder.imageView.setImageResource(items.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
