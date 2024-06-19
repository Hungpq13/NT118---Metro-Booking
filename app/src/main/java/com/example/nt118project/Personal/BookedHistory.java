package com.example.nt118project.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.R;
import com.example.nt118project.bottomnav.Personal;

import java.util.ArrayList;
import java.util.List;

public class BookedHistory extends AppCompatActivity {

    private RecyclerView recyclerViewTickets;
    private TicketAdapter ticketAdapter;
    private List<Ticket> ticketList;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_history);
        back = findViewById(R.id.back);
        recyclerViewTickets = findViewById(R.id.recyclerViewTickets);
        recyclerViewTickets.setLayoutManager(new LinearLayoutManager(this));

        ticketList = new ArrayList<Ticket>();
        ticketList.add(new Ticket("MTRO123", "Lược đi", "10:00AM", "19/06/2024"));
        ticketList.add(new Ticket("MTRO124", "Lược về", "11:00AM", "23/05/2024"));

        // Thêm dữ liệu mẫu khác nếu cần

        ticketAdapter = new TicketAdapter(ticketList);
        recyclerViewTickets.setAdapter(ticketAdapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookedHistory.this, Personal.class);
                startActivity(intent);
            }
        });
    }
}