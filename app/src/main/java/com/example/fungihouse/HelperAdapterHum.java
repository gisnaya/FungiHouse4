package com.example.fungihouse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        viewHolderClass.time.setText(fetchData.getTime());
        if (fetchData.getHum()<79){
            viewHolderClass.siram.setVisibility(View.VISIBLE);
        }
        else {
            viewHolderClass.siram.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return fetchDataList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView hum, time;
        ImageView siram;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            hum=itemView.findViewById(R.id.txt_hum);
            time=itemView.findViewById(R.id.txt_time);
            siram=itemView.findViewById(R.id.ic_hose);
        }
    }
}
