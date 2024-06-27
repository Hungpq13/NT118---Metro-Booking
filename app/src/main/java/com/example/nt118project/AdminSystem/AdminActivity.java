package com.example.nt118project.AdminSystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nt118project.Auth.Login;
import com.example.nt118project.Auth.SharedPreferenceHelper;
import com.example.nt118project.MainFunction.NearStation1Activity;
import com.example.nt118project.R;

public class AdminActivity extends AppCompatActivity {
    LinearLayout customer;
    LinearLayout routes;
    LinearLayout ticket ;
    LinearLayout logout ;
    SharedPreferenceHelper sharedPreferenceHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        sharedPreferenceHelper = new SharedPreferenceHelper(this);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferenceHelper.clear();
                Intent intent = new Intent(AdminActivity.this, Login.class);
                startActivity(intent);
                finishAffinity();
            }
        });
        customer = findViewById(R.id.customer);
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, MemberPageActivity.class));
            }
        });

        routes = findViewById(R.id.routes);
        routes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, MetroTripActivity.class));
            }
        });
        ticket = findViewById(R.id.ticket);
        ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, TicketActivity.class));
            }
        });

        TextView idNV = findViewById(R.id.idNV);
        idNV.setText("Mã NV : " + sharedPreferenceHelper.getUserId());
        TextView name = findViewById(R.id.name);
        name.setText("Nhân viên : " + sharedPreferenceHelper.getUserName());
    }
}
