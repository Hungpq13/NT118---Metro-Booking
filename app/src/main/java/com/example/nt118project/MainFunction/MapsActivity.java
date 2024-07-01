package com.example.nt118project.MainFunction;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.nt118project.R;
import com.example.nt118project.bottomnav.MenuActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap; // Đối tượng GoogleMap để tương tác với bản đồ
    private FusedLocationProviderClient fusedLocationClient; // Đối tượng dùng để lấy vị trí của thiết bị
    private boolean locationPermissionGranted; // Biến xác định quyền truy cập vị trí đã được cấp hay chưa
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1; // Mã yêu cầu quyền truy cập vị trí
    ImageView locate; // ImageView để thực hiện chức năng tìm vị trí hiện tại
    ImageView back; // ImageView để quay lại màn hình Menu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps); // Gắn layout activity_maps.xml vào Activity
        locate = findViewById(R.id.locate); // Ánh xạ ImageView tìm vị trí từ id trong layout
        back = findViewById(R.id.back); // Ánh xạ ImageView quay lại từ id trong layout

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map); // Tạo đối tượng SupportMapFragment để lấy GoogleMap từ layout
        mapFragment.getMapAsync(this); // Lấy GoogleMap bất đồng bộ khi sẵn sàng

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this); // Khởi tạo đối tượng để lấy vị trí hiện tại

        getLocationPermission(); // Xác định và yêu cầu quyền truy cập vị trí từ người dùng

        back.setOnClickListener(new View.OnClickListener() { // Xử lý sự kiện khi click vào ImageView back
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, MenuActivity.class); // Tạo Intent để chuyển đến MenuActivity
                startActivity(intent); // Khởi chạy Intent
            }
        });

        locate.setOnClickListener(new View.OnClickListener() { // Xử lý sự kiện khi click vào ImageView locate
            @Override
            public void onClick(View v) {
                getDeviceLocation(); // Lấy vị trí hiện tại của thiết bị
            }
        });
    }

    private void getLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true; // Nếu đã có quyền truy cập vị trí, thiết lập biến true
            getDeviceLocation(); // Lấy vị trí hiện tại của thiết bị
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION); // Yêu cầu người dùng cấp quyền truy cập vị trí
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationPermissionGranted = false; // Thiết lập mặc định là không có quyền truy cập vị trí

        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true; // Nếu người dùng cấp quyền, thiết lập biến true
            }
        }
        getDeviceLocation(); // Lấy vị trí hiện tại của thiết bị
    }

    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) { // Nếu đã có quyền truy cập vị trí
                Task<Location> locationResult = fusedLocationClient.getLastLocation(); // Lấy vị trí cuối cùng của thiết bị
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) { // Nếu lấy vị trí thành công
                            Location lastKnownLocation = task.getResult(); // Lấy vị trí cuối cùng
                            if (lastKnownLocation != null) {
                                LatLng currentLocation = new LatLng(lastKnownLocation.getLatitude(),
                                        lastKnownLocation.getLongitude()); // Tạo LatLng từ vị trí lấy được
                                mMap.addMarker(new MarkerOptions().position(currentLocation).title("Bạn")); // Thêm Marker cho vị trí hiện tại
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15)); // Di chuyển camera đến vị trí hiện tại và zoom
                            }
                        } else {
                            Toast.makeText(MapsActivity.this, "Không thể lấy được vị trí.", Toast.LENGTH_SHORT).show(); // Thông báo nếu không thể lấy được vị trí
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            e.printStackTrace(); // Xử lý nếu có lỗi bảo mật
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap; // Khởi tạo GoogleMap

        // Thêm các Marker cho các trạm
        LatLng benthanh = new LatLng(10.776530, 106.700980);
        mMap.addMarker(new MarkerOptions().position(benthanh).title("Trạm Bến Thành").snippet("Trạm Bến Thành").icon(bitmapDescriptor(this, R.drawable.markericon))).setTag("1");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(benthanh));

        LatLng nhahat = new LatLng(10.775235, 106.701868);
        mMap.addMarker(new MarkerOptions().position(nhahat).title("Trạm Nhà Hát").snippet("Trạm Nhà Hát").icon(bitmapDescriptor(this, R.drawable.markericon))).setTag("2");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nhahat));

        LatLng bason = new LatLng(10.781788, 106.708189);
        mMap.addMarker(new MarkerOptions().position(bason).title("Trạm Ba Son").snippet("Trạm Ba Son").icon(bitmapDescriptor(this, R.drawable.markericon))).setTag("3");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bason));

        LatLng vanthanh = new LatLng(10.796030, 106.715512);
        mMap.addMarker(new MarkerOptions().position(vanthanh).title("Trạm Văn Thánh").snippet("Trạm Văn Thánh").icon(bitmapDescriptor(this, R.drawable.markericon))).setTag("4");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(vanthanh));

        LatLng tancang = new LatLng(10.798547, 106.723238);
        mMap.addMarker(new MarkerOptions().position(tancang).title("Trạm Tân Cảng ").snippet("Trạm Tân Cảng").icon(bitmapDescriptor(this, R.drawable.markericon))).setTag("5");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tancang));

        LatLng thaodien = new LatLng(10.800447, 106.733660);
        mMap.addMarker(new MarkerOptions().position(thaodien).title("Trạm Thảo Điền").snippet("Trạm Thảo Điền").icon(bitmapDescriptor(this, R.drawable.markericon))).setTag("6");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(thaodien));

        LatLng anphu = new LatLng(10.802107, 106.742253);
        mMap.addMarker(new MarkerOptions().position(anphu).title("Trạm An Phú").snippet("Trạm An Phú").icon(bitmapDescriptor(this, R.drawable.markericon))).setTag("7");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(anphu));

        LatLng rachchiec = new LatLng(10.808542, 106.755284);
        mMap.addMarker(new MarkerOptions().position(rachchiec).title("Trạm Rạch chiếc").snippet("Trạm Rạch chiếc").icon(bitmapDescriptor(this, R.drawable.markericon))).setTag("8");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(rachchiec));

        LatLng phuoclong = new LatLng(10.821388, 106.758194);
        mMap.addMarker(new MarkerOptions().position(phuoclong).title("Trạm Phước Long").snippet("Trạm Phước Long").icon(bitmapDescriptor(this, R.drawable.markericon))).setTag("9");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(phuoclong));

        LatLng binhthai = new LatLng(10.832635, 106.763904);
        mMap.addMarker(new MarkerOptions().position(binhthai).title("Trạm Bình Thái").snippet("Trạm Bình Thái").icon(bitmapDescriptor(this, R.drawable.markericon))).setTag("10");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(binhthai));

        LatLng thuduc = new LatLng(10.846389, 106.771659);
        mMap.addMarker(new MarkerOptions().position(thuduc).title("Trạm Thủ Đức").snippet("Trạm Thủ Đức").icon(bitmapDescriptor(this, R.drawable.markericon))).setTag("11");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(thuduc));

        LatLng cnc = new LatLng(10.858992, 106.788830);
        mMap.addMarker(new MarkerOptions().position(cnc).title("Trạm Khu CNC").snippet("Trạm Khu Công Nghệ cao").icon(bitmapDescriptor(this, R.drawable.markericon))).setTag("12");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cnc));

        LatLng dhqg = new LatLng(10.866278, 106.801196);
        mMap.addMarker(new MarkerOptions().position(dhqg).title("Trạm ĐHQG").snippet("Trạm Đại học quốc gia").icon(bitmapDescriptor(this, R.drawable.markericon))).setTag("13");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(dhqg));

        LatLng gst = new LatLng(10.879520, 106.814104);
        mMap.addMarker(new MarkerOptions().position(gst).title("Trạm Suối Tiên").snippet("Trạm Suối Tiên").icon(bitmapDescriptor(this, R.drawable.markericon))).setTag("14");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(gst));

        // Thiết lập sự kiện khi người dùng click vào Marker
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String stationID = marker.getTag().toString(); // Lấy thông tin tag của Marker (ID trạm)
                String stationName = marker.getTitle(); // Lấy tiêu đề của Marker (tên trạm)

                // Tạo Intent để chuyển đến NearStation2Activity và truyền dữ liệu
                Intent intent = new Intent(MapsActivity.this, NearStation2Activity.class);
                intent.putExtra("stationID", stationID);
                intent.putExtra("stationName", stationName);
                startActivity(intent); // Khởi chạy Intent

                return true;
            }
        });

        getDeviceLocation(); // Lấy vị trí hiện tại của thiết bị
    }

    // Phương thức để chuyển đổi Drawable thành BitmapDescriptor
    private BitmapDescriptor bitmapDescriptor(Context context, int vectorResid) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResid);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap); // Trả về BitmapDescriptor
    }
}
