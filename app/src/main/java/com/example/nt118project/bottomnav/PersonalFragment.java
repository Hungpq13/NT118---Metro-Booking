package com.example.nt118project.bottomnav;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nt118project.Personal.BookedHistory;
import com.example.nt118project.Personal.information_personal;
import com.example.nt118project.R;

public class PersonalFragment extends Fragment {
    Button infor, btn_updatedata;

    public PersonalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        infor = view.findViewById(R.id.btn_info);
        btn_updatedata = view.findViewById(R.id.btn_updatedata);

        infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), information_personal.class);
                startActivity(intent);
            }
        });

        btn_updatedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookedHistory.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
