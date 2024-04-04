package com.example.deanmoi.UI.Notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deanmoi.Model.Notifications;
import com.example.deanmoi.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    ArrayList<Notifications> notifications;
    public NotificationAdapter(ArrayList<Notifications> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_noti, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notifications notifications1 = notifications.get(position);
        holder.tvDate.setText(notifications1.getDate());
        holder.tvTitle.setText(notifications1.getTitle());
        holder.tvDesc.setText(notifications1.getDesc());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvTitle, tvDesc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
        }
    }
}
