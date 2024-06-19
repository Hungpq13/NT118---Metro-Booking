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

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private RecyclerView notificationsRecyclerView;
    private TextView noNotificationsText;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notifications;

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
