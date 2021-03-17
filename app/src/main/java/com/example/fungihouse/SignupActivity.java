package com.example.fungihouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    Button btnSignup;
    TextView txtSignup;
    EditText mUsername,mNohp;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mUsername   = findViewById(R.id.username);
        mNohp       = findViewById(R.id.nohp);
        fAuth  = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar2);

        btnSignup = findViewById(R.id.btn_signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString().trim();
                String nohp = mNohp.getText().toString().trim();
                Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
                startActivity(intent);

                if (TextUtils.isEmpty(username)){
                    mUsername.setError("Username telah digunakan.");
                    return;
                }

                if (TextUtils.isEmpty(nohp)){
                    mNohp.setError("Nomor Handphone telah digunakan");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

            }
        });
        txtSignup = findViewById(R.id.txt_signup);
        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}