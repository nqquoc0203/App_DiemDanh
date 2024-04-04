package com.example.deanmoi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deanmoi.Model.LichSu;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    ArrayList<LichSu> lichSuArrayList;

    public HistoryAdapter(ArrayList<LichSu> lichSuArrayList) {
        this.lichSuArrayList = lichSuArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LichSu lichSu = lichSuArrayList.get(position);
        holder.tvTime.setText(lichSu.getTime());
        holder.tvDate.setText(lichSu.getDate());
        holder.tvRoom.setText(lichSu.getRoom());
        holder.tvName.setText(lichSu.getName());
    }

    @Override
    public int getItemCount() {
        return lichSuArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        TextView tvDate;
        TextView tvRoom;
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvRoom = itemView.findViewById(R.id.tvRoom);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
