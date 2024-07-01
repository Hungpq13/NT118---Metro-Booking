package com.example.nt118project.MainFunction;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.R;

import java.util.List;

public class AroundRouteAdapter extends RecyclerView.Adapter<AroundRouteAdapter.ViewHolder> {
    private List<Route> routeList;
    private Context context;
    public AroundRouteAdapter(List<Route> routeList , Context context) {
        this.routeList = routeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aroundroute, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Route route = routeList.get(position);

        holder.stationNameStarting.setText(route.getStationNameStarting());
        holder.stationNameDestination.setText(route.getStationNameDestination());
        holder.time.setText(route.getTime());
        holder.stationStatus.setText(route.getStationStatus());

        if (route.Istrue()) {
            holder.stationNameDestination.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.green));
        } else {
            holder.stationNameDestination.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.red_dam));
        }

        // Set màu cho status
        if (route.getStationStatus().equals("Di chuyển")) {
            holder.stationStatus.setBackgroundResource(R.drawable.tram); // Set your custom drawable
            holder.stationStatus.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.green)); // Set your custom color
        } else if (route.getStationStatus().equals("Tạm hoãn")) {
            holder.stationStatus.setBackgroundResource(R.drawable.trang_bo); // Set your custom drawable
            holder.stationStatus.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.red_dam)); // Set your custom color
        } else {
            holder.stationStatus.setBackgroundResource(android.R.color.transparent);
            holder.stationStatus.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.black));
        }
        holder.stationStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (route.getStationStatus().equals("Tạm hoãn")) {
                    showDelayReasonDialog(route.getDelayReason());
                }
            }
        });
    }
    private void showDelayReasonDialog(String delayReason) {
        new AlertDialog.Builder(context)
                .setTitle("Lý do tạm hoãn")
                .setMessage(delayReason)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }

    // Helper method to safely parse station ID from a string
    private int parseStationID(String stationName) {
        try {
            // Replace non-digit characters and parse the integer
            return Integer.parseInt(stationName.replaceAll("\\D+", ""));
        } catch (NumberFormatException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }
        return 0; // Default value or error handling
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView stationNameStarting, stationNameDestination, time, stationStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stationNameStarting = itemView.findViewById(R.id.StationNameStarting);
            stationNameDestination = itemView.findViewById(R.id.StationNameDestination);
            time = itemView.findViewById(R.id.time);
            stationStatus = itemView.findViewById(R.id.StationStatus);
        }
    }
}
