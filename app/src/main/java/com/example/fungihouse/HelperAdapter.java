package com.example.fungihouse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HelperAdapter extends RecyclerView.Adapter {

    List<FetchData> fetchDataList;

    public HelperAdapter(List<FetchData> fetchDataList) {
        this.fetchDataList = fetchDataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suhu,parent, false);
        ViewHolderClass viewHolderClass=new ViewHolderClass(view);

        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolderClass viewHolderClass=(ViewHolderClass)holder;

        FetchData fetchData=fetchDataList.get(position);
        viewHolderClass.suhu.setText(fetchData.getSuhu().toString());
        viewHolderClass.time.setText(fetchData.getTime());
        if (fetchData.getSuhu()>30){
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
        TextView suhu, time;
        ImageView siram;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            suhu=itemView.findViewById(R.id.txt_temp);
            time=itemView.findViewById(R.id.txt_time);
            siram=itemView.findViewById(R.id.ic_hose);
        }
    }
}
