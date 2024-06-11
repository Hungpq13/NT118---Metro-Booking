package com.example.nt118project.MainFunction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nt118project.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class NearStation2Activity extends AppCompatActivity implements OnMapReadyCallback {
     ImageView back ;
     private GoogleMap gMap;
     String stationID;
     String stationName;
     TextView stationIDTextView, stationNameTextView;
     String stationDocumentID;
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