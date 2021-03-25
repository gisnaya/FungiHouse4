package com.example.fungihouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    Button btnSignup;
    TextView txtSignup;
    EditText mNohp,mUsername;
    ProgressBar progressBar;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mNohp       = findViewById(R.id.nohp);
        mUsername   = findViewById(R.id.username);
        progressBar = findViewById(R.id.progressBar2);

        btnSignup = findViewById(R.id.btn_signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nohp = mNohp.getText().toString().trim();
                String username = mUsername.getText().toString().trim();
                Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
                startActivity(intent);

                if (nohp.isEmpty()) {
                    mNohp.setError("Nomor Handphone Tidak Boleh Kosong");
                    mNohp.requestFocus();
                }
                else if (username.isEmpty()) {
                    mUsername.setError("Username Tidak Boleh Kosong");
                    mUsername.requestFocus();
                }
                else if (nohp.isEmpty() && username.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
                else if (!(nohp.isEmpty() && username.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(nohp, username).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(SignupActivity.this, "SignUp Gagal, Silahkan Ulangi", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                startActivity(new Intent(SignupActivity.this, BerandaActivity.class));
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(SignupActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
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