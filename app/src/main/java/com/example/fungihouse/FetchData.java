package com.example.fungihouse;

public class FetchData {
    Long suhu;
    String waktu;

    public FetchData() { }

    public FetchData(Long suhu, String waktu) {
        this.suhu = suhu;
        this.waktu = waktu;
    }

    public Long getSuhu() {
        return suhu;
    }

    public void setSuhu(Long suhu) {
        this.suhu = suhu;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
