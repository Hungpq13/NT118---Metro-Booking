package com.example.nt118project.MainFunction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nt118project.R;
import com.example.nt118project.bottomnav.MenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class NearStation1Activity extends AppCompatActivity {
    ImageView back ;
    LinearLayout Tram1;
    ArrayList<Station> stationList;
    StationAdapter stationAdapter;
    FirebaseFirestore db;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_near_station1);
        back = findViewById(R.id.back);
        listView = findViewById(R.id.listView);
        db = FirebaseFirestore.getInstance();

        stationList = new ArrayList<>();
        db.collection("Station").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for(DocumentSnapshot documentSnapshot : task.getResult()) {
                        Station station = new Station(documentSnapshot.getString("StationName"), documentSnapshot.getString("StationID"), documentSnapshot.getId());
                        stationList.add(station);
                        stationAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        stationAdapter = new StationAdapter(this, R.layout.item_station_list, stationList);
        listView.setAdapter(stationAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NearStation1Activity.this, MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}