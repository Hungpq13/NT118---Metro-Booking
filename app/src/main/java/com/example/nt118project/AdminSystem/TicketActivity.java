package com.example.nt118project.AdminSystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.R;

import java.util.ArrayList;
import java.util.List;

public class TicketActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTickets;
    private TicketAdapter ticketAdapter;
    private List<Ticket> ticketList;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        back = findViewById(R.id.back);
        recyclerViewTickets = findViewById(R.id.recyclerViewTickets);
        recyclerViewTickets.setLayoutManager(new LinearLayoutManager(this));

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
}