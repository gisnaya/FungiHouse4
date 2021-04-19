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

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class SignupActivity extends AppCompatActivity {
    TextView txtSignup;
    private EditText username;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    UsernameInfo usernameInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.username);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Username");

        usernameInfo = new UsernameInfo();

        final EditText inputMobile = findViewById(R.id.nohp);
        Button buttonSignUp = findViewById(R.id.btn_signup);

        final ProgressBar progressBar = findViewById(R.id.progressBar);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputMobile.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Masukkan Nomor Handphone", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                buttonSignUp.setVisibility(View.INVISIBLE);

                String name = username.getText().toString();

                if (TextUtils.isEmpty(name)){
                    Toast.makeText(SignupActivity.this, "Masukkan Username", Toast.LENGTH_SHORT).show();
                }else{
                    addDataOnFirebase(name);
                }

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+62" + inputMobile.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        SignupActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                buttonSignUp.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                buttonSignUp.setVisibility(View.VISIBLE);
                                Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressBar.setVisibility(View.GONE);
                                buttonSignUp.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
                                intent.putExtra("mobile", inputMobile.getText().toString());
                                intent.putExtra("verificationId", verificationId);
                                startActivity(intent);
                            }
                        }
                );
            }

            private void addDataOnFirebase(String name) {
                usernameInfo.setUsername(name);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.setValue(name);
                        Toast.makeText(SignupActivity.this, "Username ditambahkan", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(SignupActivity.this, "Username gagal ditambahkan" + error, Toast.LENGTH_SHORT).show();

                    }
                });
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