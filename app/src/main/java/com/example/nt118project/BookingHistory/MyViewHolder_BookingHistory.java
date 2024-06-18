

package com.example.nt118project.BookingHistory;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.R;

public class MyViewHolder_BookingHistory extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView ticketView,turnView , timeView, dateView;

    public MyViewHolder_BookingHistory(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.car_imageview);
        ticketView = itemView.findViewById(R.id.ticketid);
        turnView = itemView.findViewById(R.id.turn);
        timeView = itemView.findViewById(R.id.time);
        dateView = itemView.findViewById(R.id.date);
    }
}



