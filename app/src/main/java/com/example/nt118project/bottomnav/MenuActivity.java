package com.example.nt118project.bottomnav;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nt118project.AdminSystem.AdminActivity;
import com.example.nt118project.BookingHistory.BookingHistory;
import com.example.nt118project.MainFunction.Main_payment;
import com.example.nt118project.MainFunction.MapsActivity;
import com.example.nt118project.MainFunction.NearStation1Activity;
import com.example.nt118project.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import java.io.IOException;
import java.util.List;


public class MenuActivity extends AppCompatActivity implements OnMapReadyCallback {

    Button metro ;
    Button Search ;
    Button Payment ;
    TextView usernameTextView;
    ImageView avatarImageView;
    private GoogleMap mMap;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        Payment = findViewById(R.id.payment);
        metro = findViewById(R.id.metro);
        Search = findViewById(R.id.search);
        avatarImageView = findViewById(R.id.avatarImageView);
        usernameTextView = findViewById(R.id.usernameTextView);



        Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, Main_payment.class);
                startActivity(intent);
            }
        });
        avatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, Personal.class);
                startActivity(intent);
            }
        });
        usernameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, NearStation1Activity.class);
                startActivity(intent);
            }
        });
        metro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
        searchView = findViewById(R.id.searchView);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Make the SearchView auto-open
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(MenuActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    } else {
                        Toast.makeText(MenuActivity.this, "Vị trí không hợp lệ !", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Default location
        LatLng defaultLocation = new LatLng(10.8700, 106.8032);
        mMap.addMarker(new MarkerOptions().position(defaultLocation).title("you").icon(BitmapDescriptorFactory.fromResource(R.drawable.markeruser)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation,15));
    }

    private BitmapDescriptor bitmapDescriptor(Context context, int vectorResid)
    {
        Drawable VectorDrawable = ContextCompat.getDrawable(context , vectorResid);
        VectorDrawable.setBounds(0 ,0,VectorDrawable.getIntrinsicWidth(),VectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(VectorDrawable.getIntrinsicWidth(),VectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        VectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}