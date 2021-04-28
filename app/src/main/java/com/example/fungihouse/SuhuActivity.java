package com.example.fungihouse;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class SuhuActivity extends AppCompatActivity {

    private static final String TAG = "fungi";
    TextView tv_suhu, tv_time, tv_day, tv_date;
    List<FetchData> fetchData;
    RecyclerView recyclerView;
    HelperAdapter helperAdapter;
    DatabaseReference databaseReference;
    String username;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suhu);

        tv_suhu=(TextView)findViewById(R.id.tv_suhu);
        tv_time=(TextView)findViewById(R.id.tv_time);
        tv_day=(TextView)findViewById(R.id.tv_day);
        tv_date=(TextView)findViewById(R.id.tv_date);

        sharedPreferences = getSharedPreferences("data_user", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username",null);

        recyclerView = findViewById(R.id.rv_suhu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchData = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("history");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    try {
                        Double temp = postSnapshot.child("temp").getValue(Double.class);
                        String hour = postSnapshot.child("hour").getValue(String.class);
                        String day = postSnapshot.child("day").getValue(String.class);

//                        DateFormat dateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
//                        Date date = new Date();
//                        String strDate = dateFormat.getDateInstance(dateFormat.FULL).format(date);
//                        databaseReference.child("day").setValue(strDate);

                        Log.d(TAG, " temp : "+temp+" hour : "+hour+" day : "+day);
                        fetchData.add(new FetchData(temp, hour, day));
                    }catch (Exception e){
                        Log.d(TAG, "exception: "+e);
                    }

                }
                Collections.reverse(fetchData);
                helperAdapter = new HelperAdapter(fetchData);
                recyclerView.setAdapter(helperAdapter);
                FetchData showData = fetchData.get(0);
                tv_suhu.setText(showData.getSuhu().toString());
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
