package com.example.fungihouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BerandaActivity extends AppCompatActivity {
    TextView tv_suhu, tv_kelembapan, tv_waktu;
    DatabaseReference reff;
    Button btnLanjut;
    LinearLayout lnSuhu, lnKelembapan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        tv_suhu=(TextView)findViewById(R.id.tv_suhu);
        tv_kelembapan=(TextView)findViewById(R.id.tv_kelembapan);
        tv_waktu=(TextView)findViewById(R.id.tv_waktu);

        reff=FirebaseDatabase.getInstance().getReference().child("dht22");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String temp=snapshot.child("temp").getValue().toString();
                String hum=snapshot.child("hum").getValue().toString();
                String time=snapshot.child("time").getValue().toString();
                tv_suhu.setText(temp);
                tv_kelembapan.setText(hum);
                tv_waktu.setText(time);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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