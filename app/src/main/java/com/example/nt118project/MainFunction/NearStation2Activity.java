package com.example.nt118project.MainFunction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NearStation2Activity extends AppCompatActivity implements OnMapReadyCallback {
    ImageView back;
    private GoogleMap mMap;
    String stationID;
    String stationName;
    TextView stationIDTextView, stationNameTextView;
    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private AroundRouteAdapter adapter;
    private List<Route> routeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.near_station2);

        Intent intent = getIntent();
        stationID = intent.getStringExtra("stationID");
        stationName = intent.getStringExtra("stationName");

        back = findViewById(R.id.back);
        stationIDTextView = findViewById(R.id.Tram);
        stationNameTextView = findViewById(R.id.chuyen);

        stationIDTextView.setText("Trạm " + stationID);
        stationNameTextView.setText(stationName);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        routeList = new ArrayList<>();
        adapter = new AroundRouteAdapter(routeList , this);
        recyclerView.setAdapter(adapter);

        loadRoutes();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void loadRoutes() {
        int stationIDInt = Integer.parseInt(stationID);

        Query query = firebaseFirestore.collection("Station")
                .whereNotEqualTo("StationID", stationID);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        int destinationID = Integer.parseInt(documentSnapshot.getString("StationID"));
                        String stationStatus = documentSnapshot.getString("StationStatus");
                        String stationNameDestination = documentSnapshot.getString("StationName");
                        String delayReason = documentSnapshot.getString("Reason"); // Retrieve delay reason from Firestore
                        String time = String.valueOf(Math.abs(destinationID - stationIDInt) * 5) + " phút";
                        boolean isGreenText = destinationID < stationIDInt;
                        Route route = new Route(
                                "Trạm " + stationID,
                                stationNameDestination,
                                time,
                                stationStatus,
                                isGreenText,
                                delayReason
                        );

                        routeList.add(route);

                        // Check if station status is "Tạm hoãn"
                        if (stationStatus.equals("Tạm hoãn")) {
                            // Append station status next to station name
                            String displayedStationName = stationName + " (" + stationStatus + ")";
                            stationNameTextView.setText(displayedStationName);
                        }
                    }

                    // Sort routeList by time ascending
                    Collections.sort(routeList, new Comparator<Route>() {
                        @Override
                        public int compare(Route route1, Route route2) {
                            int time1 = parseTime(route1.getTime());
                            int time2 = parseTime(route2.getTime());
                            return Integer.compare(time1, time2);
                        }

                        private int parseTime(String timeStr) {
                            // Example: "15 phút" -> 15
                            return Integer.parseInt(timeStr.replaceAll("\\D+", ""));
                        }
                    });

                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("Firestore", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // List of station coordinates
        LatLng[] stationCoordinates = {
                new LatLng(10.776530, 106.700980), // Bến Thành
                new LatLng(10.775235, 106.701868), // Nhà Hát
                new LatLng(10.781788, 106.708189), // Ba Son
                new LatLng(10.796030, 106.715512), // Văn Thánh
                new LatLng(10.798547, 106.723238), // Tân Cảng
                new LatLng(10.800447, 106.733660), // Thảo Điền
                new LatLng(10.802107, 106.742253), // An Phú
                new LatLng(10.808542, 106.755284), // Rạch chiếc
                new LatLng(10.821388, 106.758194), // Phước Long
                new LatLng(10.832635, 106.763904), // Bình Thái
                new LatLng(10.846389, 106.771659), // Thủ Đức
                new LatLng(10.858992, 106.788830), // Khu CNC
                new LatLng(10.866278, 106.801196), // ĐHQG
                new LatLng(10.879520, 106.814104)  // Suối Tiên
        };

        // Map of station names to their coordinates
        String[] stationNames = {
                "Trạm Bến Thành",
                "Trạm Nhà Hát",
                "Trạm Ba Son",
                "Trạm Văn Thánh",
                "Trạm Tân Cảng",
                "Trạm Thảo Điền",
                "Trạm An Phú",
                "Trạm Rạch chiếc",
                "Trạm Phước Long",
                "Trạm 10 Bình Thái",
                "Trạm 11 Thủ Đức",
                "Trạm Khu CNC",
                "Trạm ĐHQG",
                "Trạm Suối Tiên"
        };

        // Add markers to the map
        for (int i = 0; i < stationCoordinates.length; i++) {
            LatLng position = stationCoordinates[i];
            String title = stationNames[i];
            MarkerOptions markerOptions = new MarkerOptions().position(position).title(title);

            if (title.equals(stationName)  ) {
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                if (title.equals(stationName)) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
                }
            } else {
                int destinationID = i + 1;
                if (destinationID < Integer.parseInt(stationID)) {
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                } else {
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }
            }

            mMap.addMarker(markerOptions);
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stationCoordinates[Integer.parseInt(stationID) - 1], 15));
    }
}
