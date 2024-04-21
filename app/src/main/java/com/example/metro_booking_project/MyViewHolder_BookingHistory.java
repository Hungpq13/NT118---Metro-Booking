package com.example.metro_booking_project;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder_BookingHistory extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView nameView, positionFromView, positionToView, timeView, dateView;

    public MyViewHolder_BookingHistory(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.car_imageview);
        nameView = itemView.findViewById(R.id.name);
        positionFromView = itemView.findViewById(R.id.position_from);
        positionToView = itemView.findViewById(R.id.position_to);
        timeView = itemView.findViewById(R.id.time);
        dateView = itemView.findViewById(R.id.date);
    }
}
