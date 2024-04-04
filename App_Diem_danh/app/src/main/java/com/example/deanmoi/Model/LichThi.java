package com.example.deanmoi.Model;

public class LichThi {
    String time ;
    String name ;
    String room ;
    String date ;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LichThi(String time, String name, String room , String date) {
        this.time = time;
        this.name = name;
        this.room = room;
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
}
