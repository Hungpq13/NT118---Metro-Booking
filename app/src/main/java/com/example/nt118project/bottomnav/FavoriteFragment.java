package com.example.nt118project.bottomnav;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.Auth.SharedPreferenceHelper;
import com.example.nt118project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoriteFragment extends Fragment {

    private Spinner stationSpinner;
    private RecyclerView favoriteStationsRecyclerView;
    private FavoriteStationsAdapter favoriteStationsAdapter;
    private List<FavoriteStation> favoriteStations;
    private List<FavoriteStation> allStations;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private SharedPreferenceHelper sharedPreferenceHelper;

    RelativeLayout favoritestation;

    public FavoriteFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        stationSpinner = view.findViewById(R.id.station_spinner);
        favoriteStationsRecyclerView = view.findViewById(R.id.favorite_stations_recyclerview);
        favoriteStations = new ArrayList<>();
        sharedPreferenceHelper = new SharedPreferenceHelper(getActivity().getApplicationContext());

        firebaseFirestore.collection("FavoriteStation").whereEqualTo("UserId", sharedPreferenceHelper.getUserId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        favoriteStations.add(new FavoriteStation(document.getString("StationId"), document.getString("name")));
                    }
                    favoriteStationsAdapter.notifyDataSetChanged();
                }
            }
        });

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
            firebaseFirestore.collection("FavoriteStation").whereEqualTo("UserId", sharedPreferenceHelper.getUserId()).whereEqualTo("name", station.getName()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            Toast.makeText(getActivity().getApplicationContext(), "Trạm đã được yêu thích", Toast.LENGTH_SHORT).show();
                        } else {

                            Map<String, Object> data = new HashMap<>();
                            data.put("UserId", sharedPreferenceHelper.getUserId());
                            data.put("StationId", station.getId());
                            data.put("name", station.getName());

                            firebaseFirestore.collection("FavoriteStation").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @SuppressLint("NotifyDataSetChanged")
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Thêm trạm thành công", Toast.LENGTH_SHORT).show();
                                    favoriteStations.add(station);
                                    favoriteStationsAdapter.notifyDataSetChanged();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Lỗi khi thêm trạm", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Lỗi khi kiểm tra trạm", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private void removeStation(FavoriteStation station) {
        if (favoriteStations.contains(station)) {
            firebaseFirestore.collection("FavoriteStation")
                    .whereEqualTo("UserId", sharedPreferenceHelper.getUserId())
                    .whereEqualTo("name", station.getName())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    firebaseFirestore.collection("FavoriteStation")
                                            .document(document.getId())
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    favoriteStations.remove(station);
                                                    favoriteStationsAdapter.notifyDataSetChanged();
                                                    updateSpinnerAdapter();
                                                    Toast.makeText(getActivity().getApplicationContext(), "Xóa trạm thành công", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getActivity().getApplicationContext(), "Lỗi khi xóa trạm", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "Không tìm thấy trạm để xóa", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Trạm không tồn tại trong danh sách yêu thích", Toast.LENGTH_SHORT).show();
        }
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
