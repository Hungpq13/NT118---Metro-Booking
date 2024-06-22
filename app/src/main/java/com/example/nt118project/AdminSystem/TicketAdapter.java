package com.example.nt118project.AdminSystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.nt118project.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private List<Ticket> ticketList;

    public TicketAdapter(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket ticket = ticketList.get(position);
        holder.tvBuyerName.setText(ticket.getBuyerName());
        holder.tvPurchaseTime.setText(ticket.gettime());
        holder.tvTicketType.setText(ticket.getturn());
        holder.tvticketid.setText(ticket.getticketid());
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {

        TextView tvBuyerName;
        TextView tvPurchaseTime;
        TextView tvTicketType;
        TextView tvticketid;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBuyerName = itemView.findViewById(R.id.tvBuyerName);
            tvPurchaseTime = itemView.findViewById(R.id.tvPurchaseTime);
            tvTicketType = itemView.findViewById(R.id.tvTicketType);
            tvticketid = itemView.findViewById(R.id.tvTicketCode);
        }
    }
}

