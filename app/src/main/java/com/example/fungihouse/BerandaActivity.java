package com.example.fungihouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.fungihouse.DataInterface.dayFormat;
import static com.example.fungihouse.DataInterface.englishDateFormat;
import static com.example.fungihouse.DataInterface.formateDateFromstring;

public class BerandaActivity extends AppCompatActivity {
    TextView tv_suhu, tv_kelembapan, tv_hour, tv_day, tv_nama;
    DatabaseReference reff;
    Button btnLanjut;
    LinearLayout lnSuhu, lnKelembapan;
    ImageView img_off, img_info;
    String username;
    SharedPreferences sharedPreferences;
    Calendar mCalendar;
    public String getDate;
    Button pilihTanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        tv_suhu = (TextView) findViewById(R.id.tv_suhu);
        tv_kelembapan = (TextView) findViewById(R.id.tv_kelembapan);
        tv_day = (TextView) findViewById(R.id.tv_day);
        tv_hour = (TextView) findViewById(R.id.tv_hour);
        tv_nama = (TextView) findViewById(R.id.tv_username);
        pilihTanggal = findViewById(R.id.pilihTanggal);

        sharedPreferences = getSharedPreferences("data_user", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", null);
        tv_nama.setText(username);

        reff = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("dht22");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String temp = snapshot.child("temp").getValue().toString();
                String hum = snapshot.child("hum").getValue().toString();
                String day = formateDateFromstring(englishDateFormat, dayFormat, snapshot.child("day").getValue().toString());
                String hour = snapshot.child("hour").getValue().toString();

//                DateFormat dateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
//                Date date = new Date();
//                String strDate = dateFormat.getDateInstance(dateFormat.FULL).format(date);
//                reff.child("day").setValue(strDate);

                tv_suhu.setText(temp + "\u2103");
                tv_kelembapan.setText(hum + "%");
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
        pilihTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(BerandaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mCalendar.set(Calendar.YEAR, year);
                        mCalendar.set(Calendar.MONTH, month);
                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        getDate = englishDateFormat.format(mCalendar.getTime());
                    }
                },
                        mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnLanjut = findViewById(R.id.btn_lanjut);
        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getDate == null){
                    Toast.makeText(BerandaActivity.this, "Silakan pilih tanggal\nterlebih dahulu!", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(BerandaActivity.this, GrafikActivity.class);
                    intent.putExtra("date", getDate);
                    startActivity(intent);
                }
            }
        });
        mCalendar = Calendar.getInstance();
        img_off = findViewById(R.id.img_off);
        img_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
        img_info = findViewById(R.id.img_info);
        img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
            }
        });
    }
}