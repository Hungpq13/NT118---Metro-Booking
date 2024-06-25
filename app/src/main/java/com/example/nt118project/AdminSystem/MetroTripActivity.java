package com.example.nt118project.AdminSystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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

        // Fetch metro trips from Firestore
        firebaseFirestore.collection("Station").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    metroTripList.clear(); // Clear existing list before adding new data

                    // Populate metroTripList with data from Firestore
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        metroTripList.add(new MetroTrip(documentSnapshot.getString("StationName"), documentSnapshot.getString("StationStatus")));
                    }

                    // Initialize adapter with updated metroTripList
                    metroTripAdapter.notifyDataSetChanged(); // Notify adapter about data changes
                } else {
                    Toast.makeText(MetroTripActivity.this, "Error fetching metro trips: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        metroTripAdapter = new MetroTripAdapter(metroTripList, this);
        recyclerViewMetroTrips.setAdapter(metroTripAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MetroTripActivity.this, AdminActivity.class);
                startActivity(intent);
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

        MetroTrip metroTrip = metroTripList.get(position);
        if (metroTrip.getStatus().equals("Di chuyển")) {
            rgStatus.check(R.id.rbMoving);
        } else {
            rgStatus.check(R.id.rbDelayed);
        }

        builder.setView(view)
                .setTitle("Thay đổi trạng thái")
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String newStatus = ((RadioButton) view.findViewById(rgStatus.getCheckedRadioButtonId())).getText().toString();
                    metroTripAdapter.updateMetroTripStatus(position, newStatus);
                })
                .setNegativeButton("Hủy", null);

        builder.create().show();
    }
}
