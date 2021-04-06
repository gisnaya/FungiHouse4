package com.example.fungihouse;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Map;

public class GrafikActivity extends AppCompatActivity {

    LineChart mpLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafik);

        mpLineChart=(LineChart) findViewById(R.id.grafik);
        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(),"Suhu");
        LineDataSet lineDataSet2 = new LineDataSet(dataValues2(),"Kelembapan");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);

        mpLineChart.setDrawBorders(true);

        lineDataSet1.setColor(Color.BLUE);
        lineDataSet2.setColor(Color.RED);
        lineDataSet1.setCircleHoleColor(Color.BLUE);
        lineDataSet2.setCircleHoleColor(Color.RED);
        lineDataSet1.setValueTextSize(15);
        lineDataSet2.setValueTextSize(15);
        lineDataSet1.setValueTextColor(Color.BLUE);
        lineDataSet2.setValueTextColor(Color.RED);

        Legend legend = mpLineChart.getLegend();
        legend.setEnabled(true);
        legend.setTextSize(15);

        LineData data = new LineData(dataSets);
        mpLineChart.setData(data);
        mpLineChart.invalidate();

        Description description = new Description();
        description.setText("Fungi House");
        mpLineChart.setDescription(description);

    }
    private ArrayList<Entry> dataValues1(){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry((float)7.11, 31));
        dataVals.add(new Entry((float)7.22, 27));
        dataVals.add(new Entry((float)9.24, 30));

        return dataVals;
    }
    private ArrayList<Entry> dataValues2(){
        ArrayList<Entry> dataVals = new ArrayList<>();
        dataVals.add(new Entry((float)8.21, 80));
        dataVals.add(new Entry((float)9.13, 78));
        dataVals.add(new Entry((float)10.41, 83));

        return dataVals;
    }
}