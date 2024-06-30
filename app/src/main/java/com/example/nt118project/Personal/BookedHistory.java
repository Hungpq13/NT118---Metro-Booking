package com.example.nt118project.Personal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.Auth.SharedPreferenceHelper;
import com.example.nt118project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BookedHistory extends AppCompatActivity {

    private RecyclerView recyclerViewTickets;
    private TicketAdapter ticketAdapter;
    private List<Ticket> ticketList;
    private FirebaseFirestore firebaseFirestore;
    private SharedPreferenceHelper sharedPreferenceHelper;

    ImageView back;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_history);
        firebaseFirestore = FirebaseFirestore.getInstance();
        sharedPreferenceHelper = new SharedPreferenceHelper(getApplicationContext());
        ticketList = new ArrayList<>();

        recyclerViewTickets = findViewById(R.id.recyclerViewTickets);
        recyclerViewTickets.setLayoutManager(new LinearLayoutManager(this));

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadTickets();
    }

    private void loadTickets() {
        firebaseFirestore.collection("Ticket").whereEqualTo("UserId", sharedPreferenceHelper.getUserId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        ticketList.add(new Ticket(document.getId(), document.getString("turn"), document.getString("time"), document.getString("date")));
                    }

                    Log.v("TAG", ticketList.toString());
                    ticketAdapter = new TicketAdapter(ticketList);
                    recyclerViewTickets.setAdapter(ticketAdapter); // Thiết lập adapter sau khi dữ liệu được nạp
                    ticketAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
