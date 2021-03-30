package com.example.fungihouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_config {
    private Context mContext;
    private SuhuAdapter mSuhuAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Suhu> suhus, List<String> keys){
        mContext = context;
        mSuhuAdapter = new SuhuAdapter(suhus, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mSuhuAdapter);

    }

    class ItemSuhuView extends RecyclerView.ViewHolder {
        private TextView mTemp;
        private TextView mTime;

        private String key;

        public ItemSuhuView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.item_suhu, parent, false));

            mTemp = (TextView) itemView.findViewById(R.id.txt_temp);
            mTime = (TextView) itemView.findViewById(R.id.txt_time);

        }
        public void bind(Suhu suhu, String key){
            mTemp.setText(suhu.getTemp());
            mTime.setText(suhu.getTime());
            this.key = key;
        }
    }
    class SuhuAdapter extends RecyclerView.Adapter<ItemSuhuView>{
        private List<Suhu> mSuhuList;
        private List<String> mKeys;

        public SuhuAdapter(List<Suhu> mSuhuList, List<String> mKeys) {
            this.mSuhuList = mSuhuList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public ItemSuhuView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemSuhuView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemSuhuView holder, int position) {
            holder.bind(mSuhuList.get(position), mKeys.get(position));

        }

        @Override
        public int getItemCount() {
            return mSuhuList.size();
        }
    }
}
