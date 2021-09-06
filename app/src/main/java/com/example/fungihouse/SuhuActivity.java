package com.example.fungihouse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static com.example.fungihouse.DataInterface.dayFormat;
import static com.example.fungihouse.DataInterface.englishDateFormat;
import static com.example.fungihouse.DataInterface.hourFormat;

public class SuhuActivity extends AppCompatActivity {

    private static final String TAG = "fungi";
    TextView tv_suhu, tv_time, tv_day, tv_date;
    List<FetchData> fetchData;
    RecyclerView recyclerView;
    HelperAdapter helperAdapter;
    DatabaseReference databaseReference;
    String username;
    SharedPreferences sharedPreferences;
    ImageView img_chevron_left,gauge;
    Handler handler = new Handler();
    Runnable refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suhu);

        gauge = findViewById(R.id.gaugemeter);
        tv_suhu=(TextView)findViewById(R.id.tv_suhu);
        tv_time=(TextView)findViewById(R.id.tv_time);
        tv_day=(TextView)findViewById(R.id.tv_day);
        tv_date=(TextView)findViewById(R.id.tv_date);

        sharedPreferences = getSharedPreferences("data_user", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username",null);

        recyclerView = findViewById(R.id.rv_suhu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchData = new ArrayList<>();


        img_chevron_left = findViewById(R.id.img_chevron_left);
        img_chevron_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuhuActivity.this, BerandaActivity.class);
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
                                Double temp = postSnapshot.child("temp").getValue(Double.class);
                                Long timestamp = postSnapshot.child("timestamp").getValue(long.class);
                                Date date = new Date(timestamp * 1000L);
                                hourFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
                                dayFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
                                String hour = hourFormat.format(date);
                                String day = dayFormat.format(date);
                                Log.d(TAG, "hum : "+temp+" hour : "+hour+" day : "+day);
                                fetchData.add(new FetchData(temp, hour, day));
                            }catch (Exception e){
                                Log.d(TAG, "exception: "+e);
                            }
                        }
                        Collections.reverse(fetchData);
                        helperAdapter = new HelperAdapter(fetchData);
                        recyclerView.setAdapter(helperAdapter);
                        Log.i("sqw", String.valueOf(fetchData.size()));
                        if (fetchData.size() > 0) {
                            FetchData showData = fetchData.get(0);
                            tv_suhu.setText(showData.getSuhu().toString()+"\u2103");
                            tv_day.setText(showData.getDate());
                            gauge.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                        } else {
                            tv_day.setText("Data Belum Tersedia");
                            tv_suhu.setText("-");
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
        Intent intent = new Intent(SuhuActivity.this, BerandaActivity.class);
        startActivity(intent);
    }
}
