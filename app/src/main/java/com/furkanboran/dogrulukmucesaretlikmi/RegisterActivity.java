package com.furkanboran.dogrulukmucesaretlikmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputEditText textRegisterUsername, textRegisterPassword,textRegisterEmail,textRegisterPhone;
    private Button buttonRegister;
    private TextView textViewGoToLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
        textViewGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });
    }
    public void init(){
        textRegisterUsername = findViewById(R.id.textRegisterUsername);
        textRegisterPassword = findViewById(R.id.textRegisterPassword);
        textRegisterEmail = findViewById(R.id.textRegisterEmail);
        buttonRegister = findViewById(R.id.buttonRegister);
        mAuth = FirebaseAuth.getInstance();
        textViewGoToLogin = findViewById(R.id.textViewGoToLogin);
    }

    private void createNewAccount() {
        String username = textRegisterUsername.getText().toString();
        String password = textRegisterPassword.getText().toString();
        String email = textRegisterEmail.getText().toString();
        if (TextUtils.isEmpty(username)){
            Toast.makeText(getApplicationContext(),"Kullanıcı adı boş olamaz!",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Parola alanı boş olamaz!",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"E-Mail alanı boş olamaz!",Toast.LENGTH_SHORT).show();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Tamamlandı!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    }else{
                        Toast.makeText(getApplicationContext(),"Hata oluştu, tekrar deneyiniz!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}