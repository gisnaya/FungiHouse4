package com.example.fungihouse;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceSuhu;
    private List<Suhu> suhus = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Suhu> suhus, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }
    public FirebaseDatabaseHelper(){
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceSuhu = mDatabase.getReference("suhu");
    }

    public void readSuhu(final DataStatus dataStatus){
        mReferenceSuhu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                suhus.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : snapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Suhu suhu = keyNode.getValue(Suhu.class);
                    suhus.add(suhu);
                }
                dataStatus.DataIsLoaded(suhus, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
