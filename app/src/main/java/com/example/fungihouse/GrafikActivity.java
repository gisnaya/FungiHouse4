package com.example.fungihouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class GrafikActivity extends AppCompatActivity {

    private LineChart Linechart;
    TextView tv_day;
    ArrayList<Entry> xData, yData;
    DatabaseReference mPostReference;
    ValueEventListener valueEventListener;
    String username;
    SharedPreferences sharedPreferences;
    ImageView img_chevron_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafik);

        Linechart = (LineChart) findViewById(R.id.grafik);
        tv_day=(TextView)findViewById(R.id.tv_day);

        mPostReference = FirebaseDatabase.getInstance().getReference("history");
        mPostReference.addValueEventListener(valueEventListener= new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                xData = new ArrayList<>();
                yData = new ArrayList<>();
                float i =0;
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    i=i+1;
                    String sV = ds.child("temp").getValue().toString();
                    String sV2 = ds.child("hum").getValue().toString();
                    String day=ds.child("day").getValue().toString();
                    Float sensorValue = Float.parseFloat(sV);
                    Float sensorValue2 = Float.parseFloat(sV2);
                    xData.add(new Entry(i,sensorValue));
                    yData.add(new Entry(i,sensorValue2));
                    tv_day.setText(day);

                }
                LineData chartData = new LineData();
                LineDataSet dataX = new LineDataSet(xData, "Suhu");
                LineDataSet dataY = new LineDataSet(yData, "Kelembapan");
                dataX.setColor(Color.BLUE);
                dataY.setColor(Color.RED);
                dataX.setCircleHoleColor(Color.BLUE);
                dataY.setCircleHoleColor(Color.RED);
                dataX.setValueTextSize(15);
                dataY.setValueTextSize(15);
                dataX.setValueTextColor(Color.BLUE);
                dataY.setValueTextColor(Color.RED);
                chartData.addDataSet(dataX);
                chartData.addDataSet(dataY);

                Linechart.setData(chartData);
                Linechart.notifyDataSetChanged();
                Linechart.invalidate();

                sharedPreferences = getSharedPreferences("data_user", Context.MODE_PRIVATE);
                username = sharedPreferences.getString("username",null);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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