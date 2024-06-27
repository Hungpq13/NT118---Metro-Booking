package com.example.nt118project.MainFunction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.R;

import java.util.List;

public class AroundRouteAdapter extends RecyclerView.Adapter<AroundRouteAdapter.ViewHolder> {

    private List<Route> routeList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView StationNameStarting;
        public TextView stationName;
        public TextView time;
        public TextView StationStatus;

        public ViewHolder(View view) {
            super(view);
            StationNameStarting = view.findViewById(R.id.StationNameStarting);
            stationName = view.findViewById(R.id.StationNameDestination);
            time = view.findViewById(R.id.time);
            StationStatus = view.findViewById(R.id.StationStatus);
        }
    }

    public AroundRouteAdapter(List<Route> routeList) {
        this.routeList = routeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_aroundroute, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Route route = routeList.get(position);
        holder.StationNameStarting.setText(route.getStationNameStarting());
        holder.stationName.setText(route.getStationNameDestination());
        holder.time.setText(route.getTime());
        holder.StationStatus.setText(route.getStationStatus());
        // Set StationStatus background and text color based on StationStatus
        if (route.getStationStatus().equals("Di chuyển")) {
            holder.StationStatus.setBackgroundResource(R.drawable.tram);
            holder.StationStatus.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.green));
        } else if (route.getStationStatus().equals("Tạm Hoãn")) {
            holder.StationStatus.setBackgroundResource(R.drawable.cam);
            holder.StationStatus.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }
}


