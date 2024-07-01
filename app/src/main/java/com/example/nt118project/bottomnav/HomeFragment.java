package com.example.nt118project.bottomnav;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.nt118project.AdminSystem.AdminActivity;
import com.example.nt118project.Auth.SharedPreferenceHelper;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;


import java.io.IOException;
import java.util.List;

public class HomeFragment extends Fragment implements OnMapReadyCallback {
    private Button metro;
    private Button search;
    private Button payment;

    private GoogleMap mMap;
    private SearchView searchView;
    private SharedPreferenceHelper sharedPreferenceHelper;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize views
        payment = view.findViewById(R.id.payment);
        metro = view.findViewById(R.id.metro);
        search = view.findViewById(R.id.search);
        sharedPreferenceHelper = new SharedPreferenceHelper(requireContext());

        firebaseFirestore.collection("Users").whereEqualTo("UserId", sharedPreferenceHelper.getUserId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        String imageUrl = document.getString("ImageURL");
                        ImageView avatarImageView = view.findViewById(R.id.avatarImageView);
                        Picasso.get().load(imageUrl).into(avatarImageView);
                    }
                }
            }
        });

        searchView = view.findViewById(R.id.searchView);

        // Set up click listeners
        setClickListeners();

        TextView nameTv = view.findViewById(R.id.usernameTextView);
        nameTv.setText(sharedPreferenceHelper.getUserName());

        // Set up Google Map
        setUpMap();

        // Make the SearchView auto-open
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();

        // Set up SearchView listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if (!location.isEmpty()) {
                    Geocoder geocoder = new Geocoder(getActivity());
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (addressList != null && !addressList.isEmpty()) {
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    } else {
                        Toast.makeText(getActivity(), "Vị trí không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }

    private void setClickListeners() {
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Main_payment.class);
                startActivity(intent);
            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NearStation1Activity.class);
                startActivity(intent);
            }
        });

        metro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng benthanh = new LatLng(10.776530, 106.700980);
        mMap.addMarker(new MarkerOptions().position(benthanh).title("Trạm 1 - Bến Thành").icon(BitmapDescriptorFactory.fromResource(R.drawable.markericon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(benthanh));
        LatLng nhahat = new LatLng(10.775235, 106.701868);
        mMap.addMarker(new MarkerOptions().position(nhahat).title("Trạm 2 - Nhà Hát").icon(BitmapDescriptorFactory.fromResource(R.drawable.markericon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nhahat));
        LatLng bason = new LatLng(10.781788, 106.708189);
        mMap.addMarker(new MarkerOptions().position(bason).title("Trạm 3 - Ba Son").icon(BitmapDescriptorFactory.fromResource(R.drawable.markericon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bason));
        LatLng vanthanh = new LatLng(10.796030, 106.715512);
        mMap.addMarker(new MarkerOptions().position(vanthanh).title("Trạm 4 - Văn Thánh").icon(BitmapDescriptorFactory.fromResource(R.drawable.markericon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(vanthanh));
        LatLng tancang = new LatLng(10.798547, 106.723238);
        mMap.addMarker(new MarkerOptions().position(tancang).title("Trạm 5 - Tân Cảng ").icon(BitmapDescriptorFactory.fromResource(R.drawable.markericon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tancang));
        LatLng thaodien = new LatLng(10.800447, 106.733660);
        mMap.addMarker(new MarkerOptions().position(thaodien).title("Trạm 6 - Thảo Điền").icon(BitmapDescriptorFactory.fromResource(R.drawable.markericon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(thaodien));
        LatLng anphu = new LatLng(10.802107, 106.742253);
        mMap.addMarker(new MarkerOptions().position(anphu).title("Trạm 7 - An Phú").icon(BitmapDescriptorFactory.fromResource(R.drawable.markericon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(anphu));
        LatLng rachchiec = new LatLng(10.808542, 106.755284);
        mMap.addMarker(new MarkerOptions().position(rachchiec).title("Trạm 8 - Rạch chiếc").icon(BitmapDescriptorFactory.fromResource(R.drawable.markericon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(rachchiec));
        LatLng phuoclong = new LatLng(10.821388, 106.758194);
        mMap.addMarker(new MarkerOptions().position(phuoclong).title("Trạm 9 - Phước Long").icon(BitmapDescriptorFactory.fromResource(R.drawable.markericon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(phuoclong));
        LatLng binhthai = new LatLng(10.832635, 106.763904);
        mMap.addMarker(new MarkerOptions().position(binhthai).title("Trạm 10 - Bình Thái").icon(BitmapDescriptorFactory.fromResource(R.drawable.markericon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(binhthai));
        LatLng thuduc = new LatLng(10.846389, 106.771659);
        mMap.addMarker(new MarkerOptions().position(thuduc).title("Trạm 11 - Thủ Đức").icon(BitmapDescriptorFactory.fromResource(R.drawable.markericon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(anphu));
        LatLng cnc = new LatLng(10.858992, 106.788830);
        mMap.addMarker(new MarkerOptions().position(cnc).title("Trạm 12 - Khu Công Nghệ cao").icon(BitmapDescriptorFactory.fromResource(R.drawable.markericon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cnc));
        LatLng dhqg = new LatLng(10.866278, 106.801196);
        mMap.addMarker(new MarkerOptions().position(dhqg).title("Trạm 13 - Đại học quốc gia").icon(BitmapDescriptorFactory.fromResource(R.drawable.markericon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(dhqg));
        LatLng gst = new LatLng(10.879520, 106.814104);
        mMap.addMarker(new MarkerOptions().position(gst).title("Trạm 14 - Ga Suối Tiên ").icon(BitmapDescriptorFactory.fromResource(R.drawable.markericon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(gst));
        LatLng defaultLocation = new LatLng(10.8700, 106.8032);
        mMap.addMarker(new MarkerOptions().position(defaultLocation).title("Bạn"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15));
    }

    private BitmapDescriptor bitmapDescriptor(Context context, int vectorResid) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResid);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
