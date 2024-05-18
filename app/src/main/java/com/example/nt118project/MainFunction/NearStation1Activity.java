package com.example.nt118project.MainFunction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nt118project.R;
import com.example.nt118project.bottomnav.MenuActivity;

public class NearStation1Activity extends AppCompatActivity {
    ImageView back ;
    LinearLayout Tram1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_near_station1);
        back = findViewById(R.id.back);
        Tram1 = findViewById(R.id.metro1);
        Tram1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NearStation1Activity.this, NearStation2Activity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NearStation1Activity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}