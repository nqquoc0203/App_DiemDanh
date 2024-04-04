package com.example.deanmoi.Model;

public class LichSu {
    String date;
    String name;
    String room;
    String time;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public LichSu(String date, String name, String room, String time) {
        this.date = date;
        this.name = name;
        this.room = room;
        this.time = time;
    }
}
