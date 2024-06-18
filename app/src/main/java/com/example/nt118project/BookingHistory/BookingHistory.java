package com.example.nt118project.BookingHistory;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.R;

import java.util.ArrayList;
import java.util.List;

public class BookingHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        RecyclerView recyclerView = findViewById(R.id.history_recyclerview);

        List<Item> items = new ArrayList<Item>();
        items.add(new Item(R.drawable.qrcode, "123", "đi", "12:36", " 23/4/2024"));
        items.add(new Item(R.drawable.qrcode, "134", "về" , "12:48" , " 23/4/2024"));



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter_BookingHistory(getApplicationContext(), items));
    }
}
