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
        items.add(new Item(R.drawable.train, "Metro", "Ga Suối Tiên", "Ga Bến Thành", "35 phút", "12:36 23/4/2024"));
        items.add(new Item(R.drawable.train, "Metro", "Ga Suối Tiên", "Ga Ba Son", "27 phút", "12:48 23/4/2024"));
        items.add(new Item(R.drawable.train, "Metro", "Ga Suối Tiên", "Ga Nhà Hát Thành phố", "30 phút", "13:00 23/4/2024"));
        items.add(new Item(R.drawable.train, "Metro", "Ga Suối Tiên", "Ga Thủ Đức", " 5 phút", "13:12 23/4/2024"));
        items.add(new Item(R.drawable.train, "Metro", "Ga Suối Tiên", "Ga Rạch Chiếc", "10 phút", "13:24 23/4/2024"));


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter_BookingHistory(getApplicationContext(), items));
    }
}