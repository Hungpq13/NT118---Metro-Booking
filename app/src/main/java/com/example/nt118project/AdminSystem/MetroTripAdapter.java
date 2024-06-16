package com.example.nt118project.AdminSystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.nt118project.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MetroTripAdapter extends RecyclerView.Adapter<MetroTripAdapter.MetroTripViewHolder> {

    private List<MetroTrip> metroTripList;
    private OnMetroTripClickListener onMetroTripClickListener;

    public interface OnMetroTripClickListener {
        void onMetroTripClick(int position);
    }

    public MetroTripAdapter(List<MetroTrip> metroTripList, OnMetroTripClickListener onMetroTripClickListener) {
        this.metroTripList = metroTripList;
        this.onMetroTripClickListener = onMetroTripClickListener;
    }

    @NonNull
    @Override
    public MetroTripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_metro_trip, parent, false);
        return new MetroTripViewHolder(view, onMetroTripClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MetroTripViewHolder holder, int position) {
        MetroTrip metroTrip = metroTripList.get(position);
        holder.tvTripName.setText(metroTrip.getTripName());
        holder.tvStatus.setText(metroTrip.getStatus());
    }

    @Override
    public int getItemCount() {
        return metroTripList.size();
    }

    public void updateMetroTripStatus(int position, String newStatus) {
        metroTripList.get(position).setStatus(newStatus);
        notifyItemChanged(position);
    }

    public static class MetroTripViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTripName;
        TextView tvStatus;
        OnMetroTripClickListener onMetroTripClickListener;

        public MetroTripViewHolder(@NonNull View itemView, OnMetroTripClickListener onMetroTripClickListener) {
            super(itemView);
            tvTripName = itemView.findViewById(R.id.tvTripName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            this.onMetroTripClickListener = onMetroTripClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onMetroTripClickListener.onMetroTripClick(getAdapterPosition());
        }
    }
}

