package com.example.fungihouse;

public class FetchDataHum {
    Double hum;
    String time;
    String date;


    public FetchDataHum(Double hum, String time, String date) {
        this.hum = hum;
        this.time = time;
        this.date = date;
    }


    public Double getHum() {
        return hum;
    }

    public void setHum(Double hum) {
        this.hum = hum;
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
