package com.example.fungihouse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String dateNow = dateFormat.format(new Date());
        databaseReference = FirebaseDatabase.getInstance().getReference("history/" +dateNow);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    try {
                        Double temp = postSnapshot.child("temp").getValue(Double.class);
                        Long timestamp = postSnapshot.child("timestamp").getValue(long.class);
                        Date date = new Date(timestamp * 1000L);
                        dayFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
                        hourFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
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
                    tv_suhu.setText(showData.getSuhu().toString()+"\u2109");
                    tv_time.setText(showData.getTime());
                    tv_date.setText(showData.getDate());
                    tv_day.setText(showData.getDate());
                    gauge.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    tv_date.setText(dayFormat.format(new Date()));
                    tv_day.setText("Data Belum Tersedia");
                    tv_time.setText("-");
                    tv_suhu.setText("-");
                    gauge.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                }

//                DateFormat dateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
//                Date date = new Date();
//                String strDate = dateFormat.getDateInstance(dateFormat.FULL).format(date);
//                databaseReference.child("day").setValue(strDate);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//                    try {
//                        Double temp = postSnapshot.child("temp").getValue(Double.class);
//                        String hour = postSnapshot.child("hour").getValue(String.class);
//                        String day = postSnapshot.child("day").getValue(String.class);
//
////                        DateFormat dateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
////                        Date date = new Date();
////                        String strDate = dateFormat.getDateInstance(dateFormat.FULL).format(date);
////                        databaseReference.child("day").setValue(strDate);
//
//                        Log.d(TAG, " temp : "+temp+" hour : "+hour+" day : "+day);
//                        fetchData.add(new FetchData(temp, hour, day));
//                    }catch (Exception e){
//                        Log.d(TAG, "exception: "+e);
//                    }
//
//                }
//                Collections.reverse(fetchData);
//                helperAdapter = new HelperAdapter(fetchData);
//                recyclerView.setAdapter(helperAdapter);
//                FetchData showData = fetchData.get(0);
//                tv_suhu.setText(showData.getSuhu().toString());
//                tv_time.setText(showData.getTime());
//                tv_date.setText(showData.getDate());
//                tv_day.setText(showData.getDate());
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        img_chevron_left = findViewById(R.id.img_chevron_left);
        img_chevron_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BerandaActivity.class);
                startActivity(intent);
            }
        });

    }
}
