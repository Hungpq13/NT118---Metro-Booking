package com.example.nt118project.bottomnav;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nt118project.AdminSystem.AdminActivity;
import com.example.nt118project.BookingHistory.BookingHistory;
import com.example.nt118project.MainFunction.MapsActivity;
import com.example.nt118project.MainFunction.NearStation1Activity;
import com.example.nt118project.R;


public class MenuActivity extends AppCompatActivity {

    Button metro ;
    Button Search ;
    TextView usernameTextView;
    ImageView avatarImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        metro = findViewById(R.id.metro);
        Search = findViewById(R.id.search);
        avatarImageView = findViewById(R.id.avatarImageView);
        usernameTextView = findViewById(R.id.usernameTextView);
        avatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, BookingHistory.class);
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

    }

}