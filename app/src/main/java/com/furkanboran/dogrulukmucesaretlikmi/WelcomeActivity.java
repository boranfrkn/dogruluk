package com.furkanboran.dogrulukmucesaretlikmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
        loginControl();
        finish();
    }
    public void init(){
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }
    public void loginControl(){
        if(currentUser==null){
            startActivity(new Intent(WelcomeActivity.this,RegisterActivity.class));
        }else{
            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
        }
    }
}