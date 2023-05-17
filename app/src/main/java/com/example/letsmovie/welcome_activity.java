package com.example.letsmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class welcome_activity extends AppCompatActivity {
    //membuat variable firebase  auth dengan nama mAuth
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //authentifikasi fire base, apabila user diketahui sudah login maka
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(this, login_activity.class));
            finish();
        }
    }
    //untuk onclick menuju halaman login
    public void login(View view) {
        startActivity(new Intent(this, login_activity.class));
        finish();
    }
    //untuk onclick menuju halaman register
    public void register(View view) {
        startActivity(new Intent(this, register_activity.class));
        finish();
    }
}