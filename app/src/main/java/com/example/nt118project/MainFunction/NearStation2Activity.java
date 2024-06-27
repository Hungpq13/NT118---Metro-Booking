package com.example.nt118project.MainFunction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
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

import java.util.ArrayList;
import java.util.List;

public class NearStation2Activity extends AppCompatActivity implements OnMapReadyCallback {
     ImageView back ;
     private GoogleMap gMap;
     String stationID;
     String stationName;
     TextView stationIDTextView, stationNameTextView;
     String stationDocumentID;
    private RecyclerView recyclerView;
    private AroundRouteAdapter adapter;
    private List<Route> routeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
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
        routeList.add(new Route("Trạm 1", "Trạm Thảo Điền", "20 phút", "Di chuyển"));
        routeList.add(new Route("Trạm 1", "Trạm Văn Thánh", "10 phút", "Tạm Hoãn"));
        routeList.add(new Route("Trạm 1", "Trạm Ba Son", "5 phút", "Di chuyển"));
        routeList.add(new Route("Trạm 1", "Trạm Suối Tiên", "25 phút", "Di chuyển"));
        adapter = new AroundRouteAdapter(routeList);
        recyclerView.setAdapter(adapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NearStation2Activity.this, NearStation1Activity.class);
                startActivity(intent);
            }
        });
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        LatLng sydney = new LatLng(-34 , 151);
        gMap.addMarker(new MarkerOptions().position(sydney).title("Hung"));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


}