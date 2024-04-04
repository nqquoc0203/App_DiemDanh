package com.example.deanmoi.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deanmoi.R;

import java.time.LocalDate;
import java.util.ArrayList;

class TestAdapter extends RecyclerView.Adapter<TestViewHolder>
{
    private final ArrayList<LocalDate> days;
    private final OnItemClickTest onItemClickTest;

    public TestAdapter(ArrayList<LocalDate> days, OnItemClickTest onItemClickTest)
    {
        this.days = days;
        this.onItemClickTest = onItemClickTest;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(days.size() > 15) //month view
            layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        else // week view
            layoutParams.height = (int) parent.getHeight();

        return new TestViewHolder(view, onItemClickTest, days);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position)
    {
        final LocalDate date = days.get(position);
        if(date == null)
            holder.dayOfMonth.setText("");
        else
        {
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            if(date.equals(CalendarUtils.selectedDate))
                holder.parentView.setBackgroundResource(R.drawable.event_click);
        }
    }

    @Override
    public int getItemCount()
    {
        return days.size();
    }

    public interface  OnItemClickTest
    {
        void onItemClick(int position, LocalDate date );
    }
}

