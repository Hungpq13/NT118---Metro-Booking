package com.example.nt118project.MainFunction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nt118project.Auth.SharedPreferenceHelper;
import com.example.nt118project.R;
import com.example.nt118project.bottomnav.MenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Main_payment extends AppCompatActivity {
    private Spinner Loaive;
    private Spinner sThanhtoan;
    private TextView Giatien;
    Button paymentBtn;
    ImageView back;
    SharedPreferenceHelper sharedPreferenceHelper;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_paymentt);
        sharedPreferenceHelper = new SharedPreferenceHelper(this);

        EditText emailEdt = findViewById(R.id.hienEmail);
        EditText nameEdt = findViewById(R.id.hienName);
        EditText phoneEdt = findViewById(R.id.hienSDT);
        paymentBtn = findViewById(R.id.btn_payment);

        emailEdt.setText(sharedPreferenceHelper.getUserEmail());
        nameEdt.setText(sharedPreferenceHelper.getUserName());
        phoneEdt.setText(sharedPreferenceHelper.getUserPhone());

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

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ticketType = Loaive.getSelectedItem().toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String date = dateFormat.format(new Date());

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String time = timeFormat.format(new Date());

                if(ticketType.equals("Chọn loại vé")) {
                    Toast.makeText(Main_payment.this, "Vui lòng chọn loại vé", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> data = new HashMap<>();
                data.put("UserId", sharedPreferenceHelper.getUserId());
                data.put("buyerName", sharedPreferenceHelper.getUserName());
                data.put("date", date);
                data.put("time", time);
                data.put("turn", ticketType);

                firebaseFirestore.collection("Ticket").add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Main_payment.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Main_payment.this, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
