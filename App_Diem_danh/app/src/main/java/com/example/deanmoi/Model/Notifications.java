package com.example.deanmoi.Model;

import android.os.Parcel;

public class Notifications {
    String title;
    String desc;
    String date;

    public Notifications(String title, String desc, String date) {
        this.title = title;
        this.desc = desc;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getDate() {
        return date;
    }
}
