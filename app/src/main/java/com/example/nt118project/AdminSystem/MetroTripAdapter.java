package com.example.nt118project.AdminSystem;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nt118project.R;
import java.util.List;

public class MetroTripAdapter extends RecyclerView.Adapter<MetroTripAdapter.MetroTripViewHolder> {

    private List<MetroTrip> metroTripList;
    private OnMetroTripClickListener onMetroTripClickListener;

    public MetroTripAdapter(List<MetroTrip> metroTripList, OnMetroTripClickListener onMetroTripClickListener) {
        this.metroTripList = metroTripList;
        this.onMetroTripClickListener = onMetroTripClickListener;
    }

    @NonNull
    @Override
    public MetroTripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_metro_trip, parent, false);
        return new MetroTripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MetroTripViewHolder holder, int position) {
        MetroTrip metroTrip = metroTripList.get(position);
        holder.bind(metroTrip);
    }

    @Override
    public int getItemCount() {
        return metroTripList.size();
    }

    public void updateMetroTripStatus(int position, String newStatus) {
        metroTripList.get(position).setStatus(newStatus);
        notifyItemChanged(position);
    }

    class MetroTripViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvStationName;
        TextView tvStatus;

        MetroTripViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStationName = itemView.findViewById(R.id.tvTripName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            itemView.setOnClickListener(this);
        }

        void bind(MetroTrip metroTrip) {
            tvStationName.setText(metroTrip.getTripName());
            tvStatus.setText(metroTrip.getStatus());

            if (metroTrip.getStatus().equals("Di chuyển")) {
                tvStatus.setTextColor(Color.GREEN);
            } else if (metroTrip.getStatus().equals("Tạm hoãn")) {
                tvStatus.setTextColor(Color.RED);
            }
        }

        @Override
        public void onClick(View v) {
            if (onMetroTripClickListener != null) {
                onMetroTripClickListener.onMetroTripClick(getAdapterPosition());
            }
        }
    }

    public interface OnMetroTripClickListener {
        void onMetroTripClick(int position);
    }
}
