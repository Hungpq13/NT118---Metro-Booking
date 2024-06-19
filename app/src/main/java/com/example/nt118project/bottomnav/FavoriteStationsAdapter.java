package com.example.nt118project.bottomnav;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.R;

import java.util.List;

public class FavoriteStationsAdapter extends RecyclerView.Adapter<FavoriteStationsAdapter.ViewHolder> {

    private final List<FavoriteStation> favoriteStations;
    private final OnRemoveClickListener onRemoveClickListener;

    public interface OnRemoveClickListener {
        void onRemoveClick(FavoriteStation station);
    }

    public FavoriteStationsAdapter(List<FavoriteStation> favoriteStations, OnRemoveClickListener onRemoveClickListener) {
        this.favoriteStations = favoriteStations;
        this.onRemoveClickListener = onRemoveClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favoritestation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteStation station = favoriteStations.get(position);
        holder.stationID.setText("Trạm " + station.getId());
        holder.stationName.setText(station.getName());
        holder.removeButton.setOnClickListener(v -> onRemoveClickListener.onRemoveClick(station));
    }

    @Override
    public int getItemCount() {
        return favoriteStations.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView stationID;
        TextView stationName;
        ImageButton removeButton;

        ViewHolder(View itemView) {
            super(itemView);
            stationID = itemView.findViewById(R.id.stationID);
            stationName = itemView.findViewById(R.id.stationName);
            removeButton = itemView.findViewById(R.id.remove_button);
        }
    }
}
