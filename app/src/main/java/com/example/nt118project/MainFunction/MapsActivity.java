package com.example.nt118project.MainFunction;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.example.nt118project.R;
import com.example.nt118project.bottomnav.MenuActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.nt118project.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static final int Request_CODE=101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
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