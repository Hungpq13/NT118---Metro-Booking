package com.example.nt118project.bottomnav;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.MainFunction.NearStation1Activity;
import com.example.nt118project.MainFunction.NearStation2Activity;
import com.example.nt118project.R;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private Spinner stationSpinner;
    private RecyclerView favoriteStationsRecyclerView;
    private FavoriteStationsAdapter favoriteStationsAdapter;
    private List<FavoriteStation> favoriteStations;
    private List<FavoriteStation> allStations;

    RelativeLayout favoritestation ;
    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        stationSpinner = view.findViewById(R.id.station_spinner);
        favoriteStationsRecyclerView = view.findViewById(R.id.favorite_stations_recyclerview);
        // Initialize favorite stations list
        favoriteStations = new ArrayList<>();

        // Initialize all stations list
        allStations = getStations();

        // Set up RecyclerView
        favoriteStationsAdapter = new FavoriteStationsAdapter(favoriteStations, this::removeStation);
        favoriteStationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteStationsRecyclerView.setAdapter(favoriteStationsAdapter);

        // Set up Spinner
        updateSpinnerAdapter();

        // Handle Spinner item selection
        stationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FavoriteStation selectedStation = (FavoriteStation) parent.getItemAtPosition(position);
                if (!selectedStation.getId().equals(getString(R.string.spinner_prompt))) {
                    addStation(selectedStation);
                    updateSpinnerAdapter();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        return view;
    }

    private List<FavoriteStation> getStations() {
        // This method should return the list of stations
        // For demonstration, we use a static list
        List<FavoriteStation> stations = new ArrayList<>();
        stations.add(new FavoriteStation("1", "Bến Thành - Suối Tiên"));
        stations.add(new FavoriteStation("2", "Nhà Hát - Suối Tiên || Nhà Hát - Bến Thành"));
        stations.add(new FavoriteStation("3", "Ba Son - Suối Tiên || Ba Son - Bến Thành"));
        stations.add(new FavoriteStation("4", "Văn Thánh - Suối Tiên || Văn Thánh - Bến Thành"));
        stations.add(new FavoriteStation("5", "Tân Cảng - Suối Tiên || Tân Cảng - Bến Thành"));
        stations.add(new FavoriteStation("6", "Thảo Điền - Suối Tiên || Thảo Điền - Bến Thành "));
        stations.add(new FavoriteStation("7", "An Phú - Suối Tiên || An Phú - Bến Thành "));
        stations.add(new FavoriteStation("8", "Rạch Chiếc - Suối Tiên || Rạch Chiếc - Bến Thành"));
        stations.add(new FavoriteStation("9", "Phước Long - Suối Tiên || Phước Long - Bến Thành"));
        stations.add(new FavoriteStation("10", "Bình Thái - Suối Tiên || Bình Thái - Bến Thành"));
        stations.add(new FavoriteStation("11", "Thủ Đức - Suối Tiên || Thủ Đức - Bến Thành "));
        stations.add(new FavoriteStation("12", "Khu CNC - Suối Tiên || Khu CNC - Bến Thành"));
        stations.add(new FavoriteStation("13", "ĐHQG - Suối Tiên || ĐHQG - Bến Thành"));
        stations.add(new FavoriteStation("14", "Suối Tiên - Bến Thành"));
        return stations;
    }

    private void addStation(FavoriteStation station) {
        if (!favoriteStations.contains(station)) {
            favoriteStations.add(station);
            favoriteStationsAdapter.notifyDataSetChanged();
        }
    }

    private void removeStation(FavoriteStation station) {
        favoriteStations.remove(station);
        favoriteStationsAdapter.notifyDataSetChanged();
        updateSpinnerAdapter();
    }

    private void updateSpinnerAdapter() {
        List<FavoriteStation> availableStations = new ArrayList<>();
        availableStations.add(new FavoriteStation(getString(R.string.spinner_prompt), getString(R.string.spinner_prompt)));
        for (FavoriteStation station : allStations) {
            if (!favoriteStations.contains(station)) {
                availableStations.add(station);
            }
        }
        ArrayAdapter<FavoriteStation> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, availableStations);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stationSpinner.setAdapter(spinnerAdapter);
    }
}
