package com.example.nt118project.AdminSystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.R;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.UserViewHolder> {

    private List<Member> userList;
    private OnUserClickListener onUserClickListener;

    public interface OnUserClickListener {
        void onUserClick(int position);
        void onUserLongClick(int position);
    }

    public MemberAdapter(List<Member> userList, OnUserClickListener onUserClickListener) {
        this.userList = userList;
        this.onUserClickListener = onUserClickListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item, parent, false);
        return new UserViewHolder(view, onUserClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Member member = userList.get(position);
        holder.tvUserName.setText(member.getName());
        holder.tvUserEmail.setText(member.getEmail());
        holder.tvUserDoB.setText(member.getDoB());
        holder.tvUserSex.setText(member.getSex());
        holder.tvUserPassword.setText(member.getPassword());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void addUser(Member member) {
        userList.add(member);
        notifyItemInserted(userList.size() - 1);
    }

    public void removeUser(int position) {
        userList.remove(position);
        notifyItemRemoved(position);
    }

    public void updateUser(int position, Member member) {
        userList.set(position, member);
        notifyItemChanged(position);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView tvUserName;
        TextView tvUserEmail;
        TextView tvUserDoB;
        TextView tvUserSex;
        TextView tvUserPassword;
        OnUserClickListener onUserClickListener;

        public UserViewHolder(@NonNull View itemView, OnUserClickListener onUserClickListener) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserEmail = itemView.findViewById(R.id.tvUserEmail);
            tvUserDoB = itemView.findViewById(R.id.tvUserDoB);
            tvUserSex = itemView.findViewById(R.id.tvUserSex);
            tvUserPassword = itemView.findViewById(R.id.tvUserPassword);
            this.onUserClickListener = onUserClickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onUserClickListener.onUserClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onUserClickListener.onUserLongClick(getAdapterPosition());
            return true;
        }
    }
}
