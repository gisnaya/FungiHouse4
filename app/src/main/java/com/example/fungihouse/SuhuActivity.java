package com.example.fungihouse;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SuhuActivity extends AppCompatActivity {

    TextView tv_suhu;
    List<FetchData> fetchData;
    RecyclerView recyclerView;
    HelperAdapter helperAdapter;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suhu);

        tv_suhu=(TextView)findViewById(R.id.tv_suhu);
        recyclerView=findViewById(R.id.rv_suhu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchData=new ArrayList<>();


        databaseReference= FirebaseDatabase.getInstance().getReference("dht11");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String suhu=snapshot.child("suhu").getValue().toString();
                tv_suhu.setText(suhu);
                FetchData dht11 = snapshot.getValue(FetchData.class);
                fetchData.add(dht11);
                helperAdapter=new HelperAdapter(fetchData);
                recyclerView.setAdapter(helperAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}



