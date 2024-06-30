package com.example.nt118project.bottomnav;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.nt118project.Auth.Login;
import com.example.nt118project.Auth.SharedPreferenceHelper;
import com.example.nt118project.Personal.BookedHistory;
import com.example.nt118project.Personal.information_personal;
import com.example.nt118project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class PersonalFragment extends Fragment {
    Button infor, btn_updatedata;
    SharedPreferenceHelper sharedPreferenceHelper;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public PersonalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        infor = view.findViewById(R.id.btn_userinfo);
        sharedPreferenceHelper = new SharedPreferenceHelper(getActivity().getApplicationContext());
        btn_updatedata = view.findViewById(R.id.btn_ticket);
        Button btn_logout = view.findViewById(R.id.btn_logout);

        firebaseFirestore.collection("Users").whereEqualTo("UserId", sharedPreferenceHelper.getUserId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        String imageUrl = document.getString("ImageURL");
                        ImageView avatarImageView = view.findViewById(R.id.avatarImageView);
                        Picasso.get().load(imageUrl).into(avatarImageView);
                    }
                }
            }
        });

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

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferenceHelper.clear();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finishAffinity();
            }
        });

        return view;
    }
}
