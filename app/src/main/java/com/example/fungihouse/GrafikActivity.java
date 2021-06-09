package com.example.fungihouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.State;

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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static com.example.fungihouse.DataInterface.dateFormat;
import static com.example.fungihouse.DataInterface.dayFormat;
import static com.example.fungihouse.DataInterface.englishDateFormat;
import static com.example.fungihouse.DataInterface.formateDateFromstring;
import static com.example.fungihouse.DataInterface.formatwaktu;
import static com.example.fungihouse.DataInterface.grafikurl;
import static com.example.fungihouse.DataInterface.hourFormat;
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
    LineDataSet lineDataSet2 = new LineDataSet(null,null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    float yvalue;
    String extra;
    LimitLine upper, lower;

    DatabaseReference databaseReference;
    List<FetchData> fetchData;
    List<FetchDataHum> fetchDataHum;
    List<ChartModel> chartModelList;
    HelperAdapter helperAdapter;
    HelperAdapterHum helperAdapterHum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafik);

        lineChart = (LineChart) findViewById(R.id.grafik);
        tv_day = (TextView) findViewById(R.id.tv_day);
        lineChart.setNoDataText("Data Grafik Tidak Tersedia.");

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
        fetchDataHum = new ArrayList<>();
        fetchData = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String dateNow = dateFormat.format(new Date());
        databaseReference = FirebaseDatabase.getInstance().getReference("history/");// +dateNow);
        databaseReference.child("20210531").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Entry> DataVals = new ArrayList<Entry>();
                ArrayList<Entry> DataVals2 = new ArrayList<Entry>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ChartModel chartModel = postSnapshot.getValue(ChartModel.class);
                    try {
                        Float hum = postSnapshot.child("hum").getValue(Float.class);
                        Float temp = postSnapshot.child("temp").getValue(Float.class);
                        Long timestamp = postSnapshot.child("timestamp").getValue(long.class);

                        chartModelList.add(new ChartModel(hum, temp, timestamp));
//                        Date date = new Date(timestamp * 1000L);
//                        dayFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
//                        hourFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
//                        String waktu = hourFormat.format(date);
//                        String day = dayFormat.format(date);
//                        Long hour = Long.parseLong(waktu);

                    }catch (Exception e){
                        Log.d("erorki", "exception: "+e);
                    }

                    if (chartModel != null){
                        yvalue = chartModel.getTemp();
                        DataVals.add(new Entry(chartModel.getTimestamp(), yvalue));
                        DataVals2.add(new Entry(chartModel.getTimestamp(), chartModel.getHum()));

                    } else
                        DataVals.add(null); DataVals2.add(null);
                }
                ShowChart(DataVals, DataVals2);


//                DateFormat dateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
//                Date date = new Date();
//                String strDate = dateFormat.getDateInstance(dateFormat.FULL).format(date);
//                databaseReference.child("day").setValue(strDate);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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
    private void ShowChart (ArrayList <Entry> DataVals, ArrayList <Entry> DataVals2) {
        MyMarkerView mv = new MyMarkerView(GrafikActivity.this, R.layout.my_marker_view);
        lineChart.setMarkerView(mv);

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
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                Date date = new Date((long)value * 1000L);
                formatwaktu.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
                return formatwaktu.format(date);
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
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        lineDataSet.setFormSize(15.f);
        lineDataSet.setFillColor(Color.rgb(4, 129, 222));
        lineDataSet2.setValues(DataVals2);
        lineDataSet2.setDrawIcons(false);
        lineDataSet2.setColor(Color.rgb(4, 129, 222));
        lineDataSet2.setCircleColor(Color.rgb(4, 129, 222));
        lineDataSet2.setLineWidth(2f);
        lineDataSet2.setCircleRadius(4f);
        lineDataSet2.setDrawCircleHole(false);
        lineDataSet2.setValueTextSize(0f);
        lineDataSet2.setDrawFilled(true);
        lineDataSet2.setFormLineWidth(1f);
        lineDataSet2.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        lineDataSet2.setFormSize(15.f);
        lineDataSet2.setFillColor(Color.rgb(4, 129, 222));

        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
//        String tanggal = formateDateFromstring(englishDateFormat, dateFormat);
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