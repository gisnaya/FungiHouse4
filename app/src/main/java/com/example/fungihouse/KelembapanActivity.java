package com.example.fungihouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class KelembapanActivity extends AppCompatActivity {

    TextView tv_hum;
    List<FetchDataHum> fetchDataHum;
    RecyclerView recyclerView;
    HelperAdapterHum helperAdapterHum;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelembapan);

        tv_hum=(TextView)findViewById(R.id.tv_hum);
        recyclerView=findViewById(R.id.rv_hum);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchDataHum=new ArrayList<>();

        databaseReference= FirebaseDatabase.getInstance().getReference("kelem");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String hum=snapshot.child("hum").getValue().toString();
                tv_hum.setText(hum);
                FetchDataHum kelem = snapshot.getValue(FetchDataHum.class);
                fetchDataHum.add(kelem);
                helperAdapterHum=new HelperAdapterHum(fetchDataHum);
                recyclerView.setAdapter(helperAdapterHum);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}