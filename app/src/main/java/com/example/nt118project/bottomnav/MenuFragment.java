package com.example.nt118project.bottomnav;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nt118project.AdminSystem.AdminActivity;
import com.example.nt118project.BookingHistory.BookingHistory;
import com.example.nt118project.BookingHistory.Personal_Information;
import com.example.nt118project.MainFunction.MapsActivity;
import com.example.nt118project.MainFunction.NearStation1Activity;
import com.example.nt118project.R;

public class MenuFragment extends Fragment {

    private Button metro;
    private Button search;
    private TextView usernameTextView;
    private ImageView avatarImageView;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        metro = view.findViewById(R.id.metro);
        search = view.findViewById(R.id.search);
        avatarImageView = view.findViewById(R.id.avatarImageView);
        usernameTextView = view.findViewById(R.id.usernameTextView);

        avatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Personal_Information.class);
                startActivity(intent);
            }
        });

        usernameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdminActivity.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NearStation1Activity.class);
                startActivity(intent);
            }
        });

        metro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
