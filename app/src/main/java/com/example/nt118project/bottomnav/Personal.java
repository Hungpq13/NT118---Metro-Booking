package com.example.nt118project.bottomnav;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.nt118project.BookingHistory.BookingHistory;
import com.example.nt118project.MainFunction.NearStation1Activity;
import com.example.nt118project.R;

public class Personal extends AppCompatActivity {
Button infor , btn_updatedata ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal);
        infor = findViewById(R.id.btn_info);
        btn_updatedata = findViewById(R.id.btn_updatedata);

        infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Personal.this, information_personal.class);
                startActivity(intent);
            }
        });
        btn_updatedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Personal.this, BookingHistory.class);
                startActivity(intent);
            }
        });
    }
}