package com.example.fungihouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BerandaActivity extends AppCompatActivity {
    TextView tv_suhu, tv_kelembapan, tv_hour, tv_day;
    DatabaseReference reff;
    Button btnLanjut;
    LinearLayout lnSuhu, lnKelembapan;
    ImageView img_off;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        tv_suhu=(TextView)findViewById(R.id.tv_suhu);
        tv_kelembapan=(TextView)findViewById(R.id.tv_kelembapan);
        tv_day=(TextView)findViewById(R.id.tv_day);
        tv_hour=(TextView)findViewById(R.id.tv_hour);


        reff= (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("dht22");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String temp=snapshot.child("temp").getValue().toString();
                String hum=snapshot.child("hum").getValue().toString();
                String day=snapshot.child("day").getValue().toString();
                String hour=snapshot.child("hour").getValue().toString();

                tv_suhu.setText(temp);
                tv_kelembapan.setText(hum);
                tv_day.setText(day);
                tv_hour.setText(hour);

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
        img_off = findViewById(R.id.img_off);
        img_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

    }
}