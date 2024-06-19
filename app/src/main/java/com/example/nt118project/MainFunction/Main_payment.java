package com.example.nt118project.MainFunction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nt118project.R;
import com.example.nt118project.bottomnav.MenuActivity;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Main_payment extends AppCompatActivity {
    private Spinner Loaive;
    private Spinner sThanhtoan;
    private TextView Giatien;
    ImageView back ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_paymentt);

        Loaive = findViewById(R.id.sLoaive);
        sThanhtoan = findViewById(R.id.sThanhtoan);
        Giatien = findViewById(R.id.Giatien);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_payment.this, MenuActivity.class);
                startActivity(intent);
            }
        });
        ArrayList<String> arrayLoaiVe = new ArrayList<>();
        arrayLoaiVe.add("Chọn loại vé");
        arrayLoaiVe.add("Vé đi");
        arrayLoaiVe.add("Vé về");
        arrayLoaiVe.add("Vé 2 chiều");

        ArrayList<String> arraysThanhtoan = new ArrayList<>();
        arraysThanhtoan.add("Thanh toán momo");
        arraysThanhtoan.add("Thanh toán ngân hàng");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayLoaiVe);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Loaive.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arraysThanhtoan);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sThanhtoan.setAdapter(arrayAdapter1);

        Loaive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                if (selectedItem.equals("Vé đi")|| selectedItem.equals("Vé về") ) {
                    Giatien.setText("20.000 VND");
                } else if (selectedItem.equals("Vé 2 chiều")) {
                    Giatien.setText("40.000 VND");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
