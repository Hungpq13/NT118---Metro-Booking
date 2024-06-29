package com.example.nt118project.MainFunction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NearStation2Activity extends AppCompatActivity implements OnMapReadyCallback {
    ImageView back;
    private GoogleMap gMap;
    String stationID;
    String stationName;
    TextView stationIDTextView, stationNameTextView;
    String stationDocumentID;
    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private AroundRouteAdapter adapter;
    private List<Route> routeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.near_station2);

        Intent intent = getIntent();
        stationName = intent.getStringExtra("stationName");
        stationDocumentID = intent.getStringExtra("stationDocumentID");
        stationID = intent.getStringExtra("stationID");

        back = findViewById(R.id.back);
        stationIDTextView = findViewById(R.id.Tram);
        stationNameTextView = findViewById(R.id.chuyen);

        stationIDTextView.setText("Trạm " + stationID);
        stationNameTextView.setText(stationName);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        routeList = new ArrayList<>();
        adapter = new AroundRouteAdapter(routeList);
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
        firebaseFirestore.collection("Destination").whereEqualTo("StationID", stationDocumentID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        firebaseFirestore.collection("Station").whereEqualTo("StationName", documentSnapshot.getString("Destination")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                                if (task2.isSuccessful()) {
                                    for (DocumentSnapshot documentSnapshot1 : task2.getResult()) {
                                        routeList.add(new Route("Trạm " + documentSnapshot1.getString("StationID"), documentSnapshot.getString("StationName"), documentSnapshot.getString("Time"), documentSnapshot1.getString("StationStatus")));
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        LatLng stationLocation = new LatLng(-34, 151); // Thay đổi vị trí này thành vị trí thực tế của trạm
        gMap.addMarker(new MarkerOptions().position(stationLocation).title(stationName));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stationLocation, 15));
    }
}