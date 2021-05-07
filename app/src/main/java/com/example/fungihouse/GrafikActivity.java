package com.example.fungihouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static com.example.fungihouse.DataInterface.formateDateFromstring;
import static com.example.fungihouse.DataInterface.formatwaktu;
import static com.example.fungihouse.DataInterface.grafikurl;
import static com.example.fungihouse.DataInterface.myDateFormat;

public class GrafikActivity extends AppCompatActivity {

    private LineChart Linechart;
    TextView tv_day;
    ArrayList<Entry> xData, yData;
    DatabaseReference mPostReference;
    ValueEventListener valueEventListener;
    String username;
    SharedPreferences sharedPreferences;
    ImageView img_chevron_left;

    LineChart lineChart;
    LineDataSet lineDataSet = new LineDataSet(null,null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    float yvalue;
    String extra;
    LimitLine upper, lower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafik);

        lineChart = (LineChart) findViewById(R.id.grafik);
        tv_day = (TextView) findViewById(R.id.tv_day);
        lineChart.setNoDataText("Data Grafik Tidak Tersedia.");

        mPostReference = FirebaseDatabase.getInstance().getReference("history");
//        mPostReference.addValueEventListener(valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                xData = new ArrayList<>();
//                yData = new ArrayList<>();
//                float i = 0;
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    i = i + 1;
//                    String sV = ds.child("temp").getValue().toString();
//                    String sV2 = ds.child("hum").getValue().toString();
//                    String day = ds.child("day").getValue().toString();
//                    Float sensorValue = Float.parseFloat(sV);
//                    Float sensorValue2 = Float.parseFloat(sV2);
//                    xData.add(new Entry(i, sensorValue));
//                    yData.add(new Entry(i, sensorValue2));
//                    tv_day.setText(day);
//
//                }
//                LineData chartData = new LineData();
//                LineDataSet dataX = new LineDataSet(xData, "Suhu");
//                LineDataSet dataY = new LineDataSet(yData, "Kelembapan");
//                dataX.setColor(Color.BLUE);
//                dataY.setColor(Color.RED);
//                dataX.setCircleHoleColor(Color.BLUE);
//                dataY.setCircleHoleColor(Color.RED);
//                dataX.setValueTextSize(15);
//                dataY.setValueTextSize(15);
//                dataX.setValueTextColor(Color.BLUE);
//                dataY.setValueTextColor(Color.RED);
//                chartData.addDataSet(dataX);
//                chartData.addDataSet(dataY);
//
//                Linechart.setData(chartData);
//                Linechart.notifyDataSetChanged();
//                Linechart.invalidate();
//
//                sharedPreferences = getSharedPreferences("data_user", Context.MODE_PRIVATE);
//                username = sharedPreferences.getString("username", null);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        mPostReference.addValueEventListener(valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Entry> DataVals = new ArrayList<Entry>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("day").getValue().toString() != null){
                        String sV = ds.child("temp").getValue().toString();
                        String sV2 = ds.child("hum").getValue().toString();
                        String day = ds.child("day").getValue().toString().replace("/", "");
                        Long newDay = Long.parseLong(day);
                        Float sensorValue = Float.parseFloat(sV);
                        Float sensorValue2 = Float.parseFloat(sV2);
                        yvalue = sensorValue;
//                        yvalue = sensorValue2;
                        Date newDate = Calendar.getInstance().getTime();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
                        try {
                            newDate = simpleDateFormat.parse(day.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        DataVals.add(new Entry(newDay, yvalue));

                        tv_day.setText(newDate.toString());
                        Log.i("hiya", "iki : "+day);
                    }
                    else {
                        DataVals.add(null);
                    }
                    ShowChart(DataVals);
                }

                sharedPreferences = getSharedPreferences("data_user", Context.MODE_PRIVATE);
                username = sharedPreferences.getString("username", null);
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
    private void ShowChart (ArrayList < Entry > DataVals) {
//        MyMarkerView mv = new MyMarkerView(GrafikActivity.this, R.layout.my_marker_view);
//        lineChart.setMarkerView(mv);

        YAxis leftaxisy = lineChart.getAxisLeft();
        leftaxisy.removeAllLimitLines();
//        leftaxisy.addLimitLine(upper);
//        leftaxisy.addLimitLine(lower);

        leftaxisy.enableGridDashedLine(10f, 10f, 0f);
        leftaxisy.setDrawZeroLine(true);
        leftaxisy.setDrawLimitLinesBehindData(false);
        leftaxisy.setLabelCount(7, true);
        leftaxisy.setDrawGridLines(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setLabelCount(10,true);
        xAxis.setLabelRotationAngle(30f);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(2f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                Date date = new Date((long)value);
                return DataInterface.DateFormat.format(date);
            }
        });

        lineDataSet.setValues(DataVals);
        lineDataSet.setDrawIcons(false);
        lineDataSet.setColor(Color.rgb(3, 169, 244));
        lineDataSet.setCircleColor(Color.rgb(3, 169, 244));
        lineDataSet.setLineWidth(2f);
        lineDataSet.setCircleRadius(4f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(0f);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setMode(LineDataSet.Mode.LINEAR);
        lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        lineDataSet.setFormSize(15.f);
        lineDataSet.setFillColor(Color.rgb(3, 169, 244));

        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
//        String tanggal = formateDateFromstring("yyyy-MM-dd", "dd, MMM yyyy", grafikurl);
//        Description description = lineChart.getDescription();
//        description.setText("Waktu (" + tanggal + ")");
//        description.setTextSize(12f);

        lineChart.clear();
        lineChart.setData(lineData);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(false);
        lineChart.setDrawGridBackground(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.animateX(2000, Easing.EaseInOutBounce);
        lineChart.invalidate();
    }
}