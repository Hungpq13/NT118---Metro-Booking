package com.example.metro_booking_project;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Main_payment extends AppCompatActivity {
    Spinner Loaive;
    Adapter_payment customer;
    Spinner sThanhtoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_payment);
        Loaive = (Spinner)findViewById(R.id.sLoaive);
        sThanhtoan = (Spinner)findViewById(R.id.sThanhtoan);


        ArrayList<String> arrayLoaiVe = new ArrayList<String>();
        arrayLoaiVe.add("Vé 1 chiều");
        arrayLoaiVe.add("Vé 2 chiều");
        arrayLoaiVe.add("Vé tháng");

        ArrayList<String> arraysThanhtoan = new ArrayList<String>();
        arraysThanhtoan.add("Thanh toán momo");
        arraysThanhtoan.add("Thanh toán ngân hàng");


        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, arrayLoaiVe);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Loaive.setAdapter(arrayAdapter);

        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item,arraysThanhtoan);
        sThanhtoan.setAdapter(arrayAdapter1);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}
