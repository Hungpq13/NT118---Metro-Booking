package com.example.nt118project.AdminSystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
import java.util.List;

public class TicketActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTickets;
    private TicketAdapter ticketAdapter;
    private List<Ticket> ticketList;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        back = findViewById(R.id.back);
        recyclerViewTickets = findViewById(R.id.recyclerViewTickets);
        recyclerViewTickets.setLayoutManager(new LinearLayoutManager(this));

        ticketList = new ArrayList<>();

        firebaseFirestore.collection("Ticket").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Clear existing list
                    ticketList.clear();

                    // Populate ticketList with data from Firestore
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        Ticket ticket = new Ticket(
                                documentSnapshot.getString("buyerName"),
                                documentSnapshot.getString("time"),
                                documentSnapshot.getString("turn"),
                                documentSnapshot.getId()
                        );
                        ticketList.add(ticket);
                    }

                    // Initialize adapter with updated ticketList
                    ticketAdapter = new TicketAdapter(ticketList);
                    recyclerViewTickets.setAdapter(ticketAdapter);
                } else {
                    // Handle error
                    // For example, show a toast or log the error
                    // Toast.makeText(TicketActivity.this, "Error getting tickets: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
    }
}
