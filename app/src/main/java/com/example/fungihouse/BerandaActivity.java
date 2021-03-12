package com.example.fungihouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BerandaActivity extends AppCompatActivity {
    Button btnLanjut;
    LinearLayout lnSuhu, lnKelembapan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        lnKelembapan = findViewById(R.id.btn_kelembapan);
        lnSuhu = findViewById(R.id.btn_suhu);
        lnKelembapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KelembapanActivity.class);
                startActivity(intent);
            }
        });
        lnSuhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SuhuActivity.class);
                startActivity(intent);
            }
        });
        btnLanjut = findViewById(R.id.btn_lanjut);
        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GrafikActivity.class);
                startActivity(intent);
            }
        });

    }
}