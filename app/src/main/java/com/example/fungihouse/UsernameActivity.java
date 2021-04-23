package com.example.fungihouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UsernameActivity extends AppCompatActivity {
    Button btnSelesai;
    EditText username;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    UsernameInfo usernameInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

//        final EditText username = findViewById(R.id.username);
        username = findViewById(R.id.username);

//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference("Username");
//
//        String name = username.getText().toString();
//
//        databaseReference.child(name).setValue(usernameInfo);
//
//        usernameInfo = new UsernameInfo(name);

        btnSelesai = findViewById(R.id.btn_selesai);
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Username");

                String name = username.getText().toString();

                usernameInfo = new UsernameInfo(name);

                if (username.getText().toString().trim().isEmpty()){
                    Toast.makeText(UsernameActivity.this, "Masukkan Username", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    addDataOnFirebase(name);
                    databaseReference.child(name).setValue(usernameInfo);
                }


                Intent intent = new Intent(getApplicationContext(), BerandaActivity.class);
                startActivity(intent);
            }

            private void addDataOnFirebase(String name) {
                usernameInfo.setUsername(name);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.setValue(name);
                        Toast.makeText(UsernameActivity.this, "Username ditambahkan", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(UsernameActivity.this, "Username gagal ditambahkan" + error, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}
