package com.example.nt118project.AdminSystem;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TicketActivity extends AppCompatActivity {
    private Button btnViewAllTickets;
    private Button btnDatePicker;
    private RecyclerView recyclerViewTickets;
    private TicketAdapter ticketAdapter;
    private List<Ticket> ticketList;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        // Initialize views
        btnViewAllTickets = findViewById(R.id.btnViewAllTickets);
        btnDatePicker = findViewById(R.id.btnDatePicker);
        back = findViewById(R.id.back);
        recyclerViewTickets = findViewById(R.id.recyclerViewTickets);
        recyclerViewTickets.setLayoutManager(new LinearLayoutManager(this));

        // Initialize ticket list
        ticketList = new ArrayList<>();

        // Set up click listeners
        btnViewAllTickets.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                Toast.makeText(TicketActivity.this, "Xem tất cả vé", Toast.LENGTH_SHORT).show();

                // Clear existing data in ticketList and notify adapter
                ticketList.clear();
                if (ticketAdapter != null) {
                    ticketAdapter.notifyDataSetChanged();
                }

                // Fetch new tickets from Firestore
                fetchTicketsFromFirestore();
            }
        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        fetchTicketsFromFirestore();
    }

    private void fetchTicketsFromFirestore() {
        firebaseFirestore.collection("Ticket").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ticketList.clear(); // Clear existing data
                    for (DocumentSnapshot document : task.getResult()) {
                        ticketList.add(new Ticket(document.getString("buyerName"), document.getString("time"), document.getString("turn"), document.getId()));
                    }
                    if (ticketAdapter == null) {
                        ticketAdapter = new TicketAdapter(ticketList);
                        recyclerViewTickets.setAdapter(ticketAdapter);
                    } else {
                        ticketAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(TicketActivity.this, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(TicketActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Format selected date
                @SuppressLint("DefaultLocale") String selectedDate = String.format("%02d", dayOfMonth) + "/" + String.format("%02d", (month + 1)) + "/" + year;

                Toast.makeText(TicketActivity.this, "Chọn ngày: " + selectedDate, Toast.LENGTH_SHORT).show();

                fetchTicketsFromFirestore(selectedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }


    private void fetchTicketsFromFirestore(String selectedDate) {
        firebaseFirestore.collection("Ticket").whereEqualTo("date", selectedDate).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ticketList.clear();
                    for (DocumentSnapshot document : task.getResult()) {
                        ticketList.add(new Ticket(document.getString("buyerName"), document.getString("time"), document.getString("turn"), document.getId()));
                    }
                    if (ticketAdapter == null) {
                        ticketAdapter = new TicketAdapter(ticketList);
                        recyclerViewTickets.setAdapter(ticketAdapter);
                    } else {
                        ticketAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(TicketActivity.this, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
