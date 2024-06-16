package com.example.nt118project.AdminSystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nt118project.Auth.Login;
import com.example.nt118project.MainFunction.NearStation1Activity;
import com.example.nt118project.R;

public class AdminActivity extends AppCompatActivity {
    LinearLayout customer;
    LinearLayout routes;
    LinearLayout ticket ;
    LinearLayout logout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, Login.class));
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
    }
}
