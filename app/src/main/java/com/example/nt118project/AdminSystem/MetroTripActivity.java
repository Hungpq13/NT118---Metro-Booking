package com.example.nt118project.AdminSystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.nt118project.R;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MetroTripActivity extends AppCompatActivity implements MetroTripAdapter.OnMetroTripClickListener {

    private RecyclerView recyclerViewMetroTrips;
    private MetroTripAdapter metroTripAdapter;
    private List<MetroTrip> metroTripList;
    ImageView back ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro_trip);
        back = findViewById(R.id.back);
        recyclerViewMetroTrips = findViewById(R.id.recyclerViewMetroTrips);
        recyclerViewMetroTrips.setLayoutManager(new LinearLayoutManager(this));

        metroTripList = new ArrayList<>();
        metroTripList.add(new MetroTrip("Trạm Bến Thành", "Di chuyển"));
        metroTripList.add(new MetroTrip("Trạm Ba Son", "Tạm hoãn"));

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
