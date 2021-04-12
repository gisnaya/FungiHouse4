package com.example.fungihouse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HelperAdapterHum extends RecyclerView.Adapter {

    List<FetchDataHum> fetchDataList;

    public HelperAdapterHum(List<FetchDataHum> fetchDataList) {
        this.fetchDataList = fetchDataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hum,parent, false);
        ViewHolderClass viewHolderClass=new ViewHolderClass(view);

        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolderClass viewHolderClass=(ViewHolderClass)holder;

        FetchDataHum fetchData=fetchDataList.get(position);
        viewHolderClass.hum.setText(fetchData.getHum().toString());
        viewHolderClass.waktu.setText(fetchData.getWaktu());

    }

    @Override
    public int getItemCount() {
        return fetchDataList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView hum,waktu;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            hum=itemView.findViewById(R.id.txt_hum);
            waktu=itemView.findViewById(R.id.txt_time);
        }
    }
}
