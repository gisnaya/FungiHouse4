package com.example.fungihouse;

public class FetchData {
    Double suhu;
    String time;
    String date;


    public FetchData(Double suhu, String time, String date) {
        this.suhu = suhu;
        this.time = time;
        this.date = date;
    }


    public Double getSuhu() {
        return suhu;
    }

    public void setSuhu(Double suhu) {
        this.suhu = suhu;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

}
