package com.example.fungihouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

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

import java.util.ArrayList;
import java.util.Map;

public class GrafikActivity extends AppCompatActivity {

    private LineChart Linechart;
    ArrayList<Entry> xData, yData;
    DatabaseReference mPostReference;
    ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafik);

        Linechart = (LineChart) findViewById(R.id.grafik);

        mPostReference = FirebaseDatabase.getInstance().getReference("chart");
        mPostReference.addValueEventListener(valueEventListener= new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                xData = new ArrayList<>();
                yData = new ArrayList<>();
                float i =0;
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    i=i+1;
                    String sV = ds.child("x").getValue().toString();
                    String sV2 = ds.child("y").getValue().toString();
                    Float sensorValue = Float.parseFloat(sV);
                    Float sensorValue2 = Float.parseFloat(sV2);
                    xData.add(new Entry(i,sensorValue));
                    yData.add(new Entry(i,sensorValue2));
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

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}