package com.example.nt118project.AdminSystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MetroTripActivity extends AppCompatActivity implements MetroTripAdapter.OnMetroTripClickListener {

    private RecyclerView recyclerViewMetroTrips;
    private MetroTripAdapter metroTripAdapter;
    private List<MetroTrip> metroTripList;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro_trip);

        back = findViewById(R.id.back);
        recyclerViewMetroTrips = findViewById(R.id.recyclerViewMetroTrips);
        recyclerViewMetroTrips.setLayoutManager(new LinearLayoutManager(this));

        metroTripList = new ArrayList<>();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MetroTripActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        fetchMetroTripsFromFirestore(); // Thêm hàm này để lấy dữ liệu từ Firestore
    }

    private void fetchMetroTripsFromFirestore() {
        firebaseFirestore.collection("Station").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    metroTripList.clear();

                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        metroTripList.add(new MetroTrip(documentSnapshot.getId(), documentSnapshot.getString("StationName"), documentSnapshot.getString("StationStatus"), documentSnapshot.getString("Reason")));
                    }

                    // Initialize adapter with updated metroTripList
                    metroTripAdapter = new MetroTripAdapter(metroTripList, MetroTripActivity.this);
                    recyclerViewMetroTrips.setAdapter(metroTripAdapter);
                } else {
                    Toast.makeText(MetroTripActivity.this, "Error fetching metro trips: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onMetroTripClick(int position) {
        showChangeStatusDialog(position);
    }

    private void showChangeStatusDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_change_status, null);

        RadioGroup rgStatus = view.findViewById(R.id.rgStatus);
        EditText tvReason = view.findViewById(R.id.tv_reason);

        MetroTrip metroTrip = metroTripList.get(position);
        if (metroTrip.getStatus().equals("Di chuyển")) {
            rgStatus.check(R.id.rbMoving);
        } else {
            rgStatus.check(R.id.rbDelayed);
            tvReason.setText(metroTrip.getReason());
        }


        rgStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbMoving) {
                    tvReason.setText("");
                }
            }
        });

        builder.setView(view)
                .setTitle("Thay đổi trạng thái")
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String newStatus = ((RadioButton) view.findViewById(rgStatus.getCheckedRadioButtonId())).getText().toString();
                    String newReason = tvReason.getText().toString();

                    if (newStatus.equals("Di chuyển")) {
                        firebaseFirestore.collection("Station").document(metroTrip.getId()).update("StationStatus", newStatus);
                        firebaseFirestore.collection("Station").document(metroTrip.getId()).update("Reason", "");
                    } else {
                        firebaseFirestore.collection("Station").document(metroTrip.getId()).update("StationStatus", newStatus);
                        firebaseFirestore.collection("Station").document(metroTrip.getId()).update("Reason", newReason);
                    }

                    metroTripAdapter.updateMetroTripStatus(position, newStatus);
                })
                .setNegativeButton("Hủy", null);

        builder.create().show();
    }

}

