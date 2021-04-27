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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;


public class LoginActivity extends AppCompatActivity {
    TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText inputMobile = findViewById(R.id.nohp);
        Button buttonLogin = findViewById(R.id.btn_login);

        final ProgressBar progressBar = findViewById(R.id.progressBar);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (inputMobile.getText().toString().trim().isEmpty()) {
//                    Toast.makeText(LoginActivity.this, "Masukkan Nomor Handphone", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                progressBar.setVisibility(View.VISIBLE);
                buttonLogin.setVisibility(View.INVISIBLE);

//                PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                        "+62" + inputMobile.getText().toString(),
//                        60,
//                        TimeUnit.SECONDS,
//                        LoginActivity.this,
//                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
//                            @Override
//                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                                progressBar.setVisibility(View.GONE);
//                                buttonLogin.setVisibility(View.VISIBLE);
//                            }
//
//                            @Override
//                            public void onVerificationFailed(@NonNull FirebaseException e) {
//                                progressBar.setVisibility(View.GONE);
//                                buttonLogin.setVisibility(View.VISIBLE);
//                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }

//                            @Override
//                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                progressBar.setVisibility(View.GONE);
//                                buttonLogin.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(getApplicationContext(), BerandaActivity.class);
//                                intent.putExtra("mobile", inputMobile.getText().toString());
//                                intent.putExtra("verificationId", verificationId);
                                startActivity(intent);
                            }
//                        }
//                );
//            }
        });


        txtLogin = findViewById(R.id.txt_login);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}