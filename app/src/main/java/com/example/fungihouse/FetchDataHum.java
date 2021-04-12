package com.example.fungihouse;

public class FetchDataHum {
    Long hum;
    String waktu;

    public FetchDataHum() { }

    public FetchDataHum(Long hum, String waktu) {
        this.hum = hum;
        this.waktu = waktu;
    }

    public Long getHum() {
        return hum;
    }

    public void setHum(Long hum) {
        this.hum = hum;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
