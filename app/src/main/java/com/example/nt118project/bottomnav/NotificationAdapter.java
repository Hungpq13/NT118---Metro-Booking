package com.example.nt118project.bottomnav;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.Personal.BookedHistory;
import com.example.nt118project.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<Notification> notifications;

    public NotificationAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.notificationIcon.setImageResource(notification.getIconResId());
        holder.notificationTitle.setText(notification.getTitle());
        holder.notificationMessage.setText(notification.getMessage());
        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BookedHistory.class);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView notificationIcon;
        TextView notificationTitle;
        TextView notificationMessage;
        RelativeLayout rlContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationIcon = itemView.findViewById(R.id.notification_icon);
            notificationTitle = itemView.findViewById(R.id.notification_title);
            notificationMessage = itemView.findViewById(R.id.notification_message);
            rlContainer = itemView.findViewById(R.id.rl_container);
        }
    }
}
