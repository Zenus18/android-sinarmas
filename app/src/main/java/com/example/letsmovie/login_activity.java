package com.example.letsmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import AddOn.LoadingDialog;

public class login_activity extends AppCompatActivity {
    EditText email,  password;
    Button login;
    ActionBar actionBar;
    LoadingDialog loadingDialog;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView tvRegisterNow = findViewById(R.id.tv_register_now);
//        meisahkan kata Don't have an account? Register now
        String text = "Don't have an account? Register now";
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.primary_color));
        //aksi saat kata register di pencet
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(login_activity.this,
                        register_activity.class));
                finish();
            }
        };
        //menyembunyikan action bar
        actionBar = getSupportActionBar();
        actionBar.hide();

        int start = text.indexOf("Register now");
        int end = start + "Register now".length();
        spannableString.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRegisterNow.setText(spannableString);
        tvRegisterNow.setMovementMethod(LinkMovementMethod.getInstance());
        // memulai kode untuk login
        email = findViewById(R.id.txt_email);
        password =  findViewById(R.id.txt_password);
        login = findViewById(R.id.login_button);
        login.setOnClickListener(v -> {
            if ((email.getText().length() > 0) && (password.getText().length() > 0)){
                login(email.getText().toString(), password.getText().toString());
            }else{
                if (email.getText().length() == 0){
                    email.setError("Masukkan Email Terlebih Dahulu");
                }else if(password.getText().length() == 0){
                    password.setError("Masukkan  Password Terlebih Dahulu");
                }
            }
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //membuat tampilan loading
        loadingDialog = new LoadingDialog(this);

    }
    //untuk  login
    private void login(String email, String password){
        loadingDialog.startLoadingDialog();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult() != null){
                    if (task.getResult().getUser() != null ){
                        reload();
                    }else{
                        Toast.makeText(login_activity.this, "Login gagal!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(login_activity.this, "Login gagal!", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismissDialog();
                }
            }
        });
    }
    public void reload(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    // untuk  inialisasi apabila user sudah  login maka akan llangsung menuju ke mainactivity
    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}