package com.example.fungihouse;

public class ChartModel {
    private float hum;
    private float temp;
    private long timestamp;

    public ChartModel(){

    }

    public ChartModel(float hum, float temp, long timestamp) {
        this.hum = hum;
        this.temp = temp;
        this.timestamp = timestamp;
    }

    public float getHum() {
        return hum;
    }

    public float getTemp() {
        return temp;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
