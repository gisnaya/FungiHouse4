package com.example.fungihouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KelembapanActivity extends AppCompatActivity {

    private static final String TAG = "fungi";
    TextView tv_hum, tv_time, tv_day, tv_date;
    List<FetchDataHum> fetchDataHum;
    RecyclerView recyclerView;
    HelperAdapterHum helperAdapterHum;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelembapan);

        tv_hum=(TextView)findViewById(R.id.tv_hum);
        tv_time=(TextView)findViewById(R.id.tv_time);
        tv_day=(TextView)findViewById(R.id.tv_day);
        tv_date=(TextView)findViewById(R.id.tv_date);

        recyclerView=findViewById(R.id.rv_hum);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchDataHum=new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("history");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    try {
                        Double hum = postSnapshot.child("hum").getValue(Double.class);
                        String hour = postSnapshot.child("hour").getValue(String.class);
                        String day = postSnapshot.child("day").getValue(String.class);
                        Log.d(TAG, "hum : "+hum+" hour : "+hour+" day : "+day);
                        fetchDataHum.add(new FetchDataHum(hum, hour, day));
                    }catch (Exception e){
                        Log.d(TAG, "exception: "+e);
                    }
                }
                Collections.reverse(fetchDataHum);
                helperAdapterHum = new HelperAdapterHum(fetchDataHum);
                recyclerView.setAdapter(helperAdapterHum);
                FetchDataHum showData = fetchDataHum.get(0);
                tv_hum.setText(showData.getHum().toString());
                tv_time.setText(showData.getTime());
                tv_date.setText(showData.getDate());
                tv_day.setText(showData.getDate());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}