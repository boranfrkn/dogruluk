package com.furkanboran.dogrulukmucesaretlikmi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    SignInButton signInButton;
    private Button buttonGoToRegister,buttonLogin,buttonGoogleSignIn;
    private TextInputEditText textLoginEmail,textLoginPassword;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private GoogleApiClient googleApiClient;
    private static final int SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        buttonGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        buttonGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,SIGN_IN);
            }
        });
    }

    private void loginUser() {
        String email = textLoginEmail.getText().toString();
        String password = textLoginPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"E-Mail alanı boş olamaz",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Parola alanı boş olamaz",Toast.LENGTH_SHORT).show();
        }else{
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(),"Giriş başarısız!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void init(){
        buttonGoToRegister = findViewById(R.id.buttonGoToRegister);
        buttonLogin = findViewById(R.id.buttonLogin);
        textLoginEmail = findViewById(R.id.textLoginEmail);
        textLoginPassword = findViewById(R.id.textLoginPassword);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        buttonGoogleSignIn = findViewById(R.id.buttonGoogleSingIn);

    }
    public void googleSignIn(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"Başarısız!",Toast.LENGTH_SHORT).show();
            }
        }
    }
}