package com.example.nt118project.bottomnav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private RecyclerView notificationsRecyclerView;
    private TextView noNotificationsText;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notifications;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        notificationsRecyclerView = view.findViewById(R.id.notifications_recyclerview);
        noNotificationsText = view.findViewById(R.id.no_notifications_text);

        // Initialize notification list
        notifications = getNotifications();

        // Set up RecyclerView
        notificationAdapter = new NotificationAdapter(notifications);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationsRecyclerView.setAdapter(notificationAdapter);

        // Show or hide the "no notifications" message
        updateNoNotificationsText();

        return view;
    }

    private List<Notification> getNotifications() {
        // This method should return the list of notifications
        // For demonstration, we use a static list
        List<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification(R.drawable.iconbell, "MetroVN xin chào", "Chào mừng bạn đã đến với app"));
        firebaseFirestore.collection("Notifications").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(DocumentSnapshot document : task.getResult()) {
                        notifications.add(new Notification(R.drawable.iconbell, document.getString("Title"), document.getString("Message")));
                    }
                }
            }
        });

        return notifications;
    }

    private void updateNoNotificationsText() {
        if (notifications.isEmpty()) {
            noNotificationsText.setVisibility(View.VISIBLE);
        } else {
            noNotificationsText.setVisibility(View.GONE);
        }
    }
}
