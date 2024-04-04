package com.example.deanmoi.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.deanmoi.Model.LichThi;
import com.example.deanmoi.Model.MonHoc;
import com.example.deanmoi.R;

import java.util.ArrayList;

public class EventTestAdapter extends BaseAdapter
{

    final ArrayList<LichThi> list;

    public EventTestAdapter(ArrayList<LichThi> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LichThi lichThi = (LichThi) getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_cell, parent, false);

        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);
        String eventTitle = lichThi.getTime().substring(0,5) + " " + lichThi.getName();
        eventCellTV.setText(eventTitle);
        return convertView;
    }
}
