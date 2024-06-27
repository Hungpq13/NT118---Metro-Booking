package com.example.nt118project.AdminSystem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TicketActivity extends AppCompatActivity {
    private Button btnViewAllTickets;
    private Button btnDatePicker;
    private RecyclerView recyclerViewTickets;
    private TicketAdapter ticketAdapter;
    private List<Ticket> ticketList;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        btnViewAllTickets = findViewById(R.id.btnViewAllTickets);
        btnDatePicker = findViewById(R.id.btnDatePicker);
        back = findViewById(R.id.back);
        recyclerViewTickets = findViewById(R.id.recyclerViewTickets);
        recyclerViewTickets.setLayoutManager(new LinearLayoutManager(this));
        btnViewAllTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(TicketActivity.this, "Xem tất cả vé", Toast.LENGTH_SHORT).show();

            }
        });
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


    ticketList = new ArrayList<Ticket>();
        ticketList.add(new Ticket("Nguyen Van A", "10:00 AM", "Vé đi", "ABC123"));
        ticketList.add(new Ticket("Tran Thi B", "11:00 AM", "Vé về", "XYZ456"));
        // Thêm dữ liệu mẫu khác nếu cần

        ticketAdapter = new TicketAdapter(ticketList);
        recyclerViewTickets.setAdapter(ticketAdapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                TicketActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Toast.makeText(TicketActivity.this, "Chọn ngày: " + dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();

                    }
                }, year, month, day);

        datePickerDialog.show();
    }

}