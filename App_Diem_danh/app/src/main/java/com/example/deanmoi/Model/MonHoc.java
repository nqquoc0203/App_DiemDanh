package com.example.deanmoi.Model;

public class MonHoc {
    String time;
    String name;
    String teacher;
    String room;

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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public MonHoc(String time, String name, String teacher, String room) {
        this.time = time;
        this.name = name;
        this.teacher = teacher;
        this.room = room;
    }
}
