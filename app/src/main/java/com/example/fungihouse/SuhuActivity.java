package com.example.fungihouse;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SuhuActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suhu);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_suhu);
        new FirebaseDatabaseHelper().readSuhu(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Suhu> suhus, List<String> keys) {
                new RecyclerView_config().setConfig(mRecyclerView,SuhuActivity.this, suhus, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }
}



