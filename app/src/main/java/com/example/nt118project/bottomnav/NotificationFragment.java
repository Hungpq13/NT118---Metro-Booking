package com.example.nt118project.bottomnav;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.Auth.SharedPreferenceHelper;
import com.example.nt118project.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private RecyclerView notificationsRecyclerView;
    private TextView noNotificationsText;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notifications;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private SharedPreferenceHelper sharedPreferenceHelper;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        notificationsRecyclerView = view.findViewById(R.id.notifications_recyclerview);
        noNotificationsText = view.findViewById(R.id.no_notifications_text);
        sharedPreferenceHelper = new SharedPreferenceHelper(requireContext());

        // Initialize an empty list for notifications
        notifications = new ArrayList<>();

        // Set up RecyclerView
        notificationAdapter = new NotificationAdapter(notifications);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationsRecyclerView.setAdapter(notificationAdapter);

        // Show or hide the "no notifications" message
        updateNoNotificationsText();

        // Listen for changes in the "Notifications" collection
        CollectionReference documentsRef = firebaseFirestore.collection("Notifications");

        documentsRef.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                Log.w("TAG", "Listen failed.", e);
                return;
            }

            if (snapshot != null) {
                for (DocumentChange dc : snapshot.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            DocumentSnapshot addedDocument = dc.getDocument();
                            if (addedDocument.getString("UserId").equals(sharedPreferenceHelper.getUserId())) {
                                Notification newNotification = new Notification(
                                        R.drawable.iconbell,
                                        addedDocument.getString("Title"),
                                        addedDocument.getString("Message")
                                );
                                notifications.add(newNotification);
                                // Notify adapter on the main thread
                                requireActivity().runOnUiThread(() -> {
                                    notificationAdapter.notifyDataSetChanged();
                                    updateNoNotificationsText();
                                });
                            }
                            break;
                        case MODIFIED:
                            break;
                        case REMOVED:
                            break;
                    }
                }
            } else {
                Log.d("TAG", "Current data: null");
            }
        });

        return view;
    }

    private void updateNoNotificationsText() {
        if (notifications.isEmpty()) {
            noNotificationsText.setVisibility(View.VISIBLE);
        } else {
            noNotificationsText.setVisibility(View.GONE);
        }
    }
}
