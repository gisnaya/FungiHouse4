package com.example.fungihouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static com.example.fungihouse.DataInterface.dayFormat;
import static com.example.fungihouse.DataInterface.englishDateFormat;
import static com.example.fungihouse.DataInterface.hourFormat;

public class KelembapanActivity extends AppCompatActivity {

    private static final String TAG = "fungi";
    TextView tv_hum, tv_time, tv_day, tv_date;
    List<FetchDataHum> fetchDataHum;
    RecyclerView recyclerView;
    HelperAdapterHum helperAdapterHum;
    DatabaseReference databaseReference;
    String username;
    SharedPreferences sharedPreferences;
    ImageView img_chevron_left, gauge;
    Handler handler = new Handler();
    Runnable refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelembapan);

        gauge = findViewById(R.id.gaugemeter);
        tv_hum=(TextView)findViewById(R.id.tv_hum);
        tv_time=(TextView)findViewById(R.id.tv_time);
        tv_day=(TextView)findViewById(R.id.tv_day);
        tv_date=(TextView)findViewById(R.id.tv_date);

        sharedPreferences = getSharedPreferences("data_user", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username",null);

        recyclerView=findViewById(R.id.rv_hum);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchDataHum=new ArrayList<>();

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
                Locale.getDefault());
        Date currentLocalTime = calendar.getTime();
        DateFormat date = new SimpleDateFormat("yyyyMMdd");
        String localTime = date.format(currentLocalTime);

        img_chevron_left = findViewById(R.id.img_chevron_left);
        img_chevron_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KelembapanActivity.this, BerandaActivity.class);
                startActivity(intent);
            }
        });
        refresh = new Runnable() {
            public void run() {
                // Do something
                hourFormat.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
                dayFormat.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
                String dateNow = englishDateFormat.format(new Date());
                String timeNow = hourFormat.format(new Date());
                String dateShow  = dayFormat.format(new Date());
                tv_date.setText(dateShow);
                tv_time.setText(timeNow);

                databaseReference = FirebaseDatabase.getInstance().getReference("history/" +dateNow);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            try {
                                Double hum = postSnapshot.child("hum").getValue(Double.class);
                                Long timestamp = postSnapshot.child("timestamp").getValue(long.class);
                                Date date = new Date(timestamp * 1000L);
                                hourFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
                                dayFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
                                String hour = hourFormat.format(date);
                                String day = dayFormat.format(date);
                                Log.d(TAG, "hum : "+hum+" hour : "+hour+" day : "+day);
                                fetchDataHum.add(new FetchDataHum(hum, hour, day));
                            }catch (Exception e){
                                Log.d(TAG, "exception: "+e);
                            }
                        }
                        Collections.reverse(fetchDataHum);
                        helperAdapterHum = new HelperAdapterHum(fetchDataHum);
                        recyclerView.setAdapter(helperAdapterHum);
                        Log.i("sqw", String.valueOf(fetchDataHum.size()));
                        if (fetchDataHum.size() > 0) {
                            FetchDataHum showData = fetchDataHum.get(0);
                            tv_hum.setText(showData.getHum().toString()+"%");
                            tv_day.setText(showData.getDate());
                            gauge.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                        } else {
                            tv_day.setText("Data Belum Tersedia");
                            tv_hum.setText("-");
                            gauge.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                handler.postDelayed(refresh, 1000);
            }
        };
        handler.post(refresh);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(KelembapanActivity.this, BerandaActivity.class);
        startActivity(intent);
    }
}