package com.example.letsmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import AddOn.LoadingDialog;

public class register_activity extends AppCompatActivity {
    EditText nama, email, password,password_confirm;
    Button register;
    private FirebaseAuth mAuth;
    LoadingDialog loadingDialog;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView tvSigninNow = findViewById(R.id.tv_SignIn_now);
//        code pemisah kata
        String text = "Already have account? Sign in now";
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.primary_color)); // Ubah warna sesuai keinginan Anda
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(register_activity.this,
                        login_activity.class));
                finish();
            }
        };
        int start = text.indexOf("Sign in now");
        int end = start + "Sign in now".length();
        spannableString.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvSigninNow.setText(spannableString);
        tvSigninNow.setMovementMethod(LinkMovementMethod.getInstance());
        //memanggil action bar
        actionBar = getSupportActionBar();
        actionBar.hide();
        // mulai code Register
        nama = findViewById(R.id.txt_nama);
        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);
        password_confirm = findViewById(R.id.txt_confirmPass);
        register = findViewById(R.id.register_button);

        register.setOnClickListener(v -> {
            if (nama.getText().length() > 0 && email.getText().length() > 0 && password.getText().length() > 0 && password_confirm.getText().length() > 0){
                if (password.getText().toString().equalsIgnoreCase(password_confirm.getText().toString())){
                    register(nama.getText().toString(), email.getText().toString(), password.getText().toString());
                }else{
                    password.setError("password tidak sama");
                    password_confirm.setError("password tidak sama");
                }
            }else{
                if(nama.getText().length() == 0){
                    nama.setError("isi nama terlebih dahulu");
                }else if(email.getText().length() == 0){
                    email.setError("isi email anda terlebih dahulu");
                }else if (password.getText().length() == 0){
                    password.setError("isi password anda terlebih dahulu");
                }else if(password_confirm.getText().length() == 0){
                    password_confirm.setError("isi confirmasi password anda");
                }
            }
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //membuat tampilan loading
        loadingDialog = new LoadingDialog(this);
    }

    private void register(String nama, String email, String password){
        loadingDialog.startLoadingDialog();
    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult() != null){
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    if (firebaseUser != null){
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setDisplayName(nama)
                                .build();
                        firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                reload();
                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(), "Register Gagal", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    loadingDialog.dismissDialog();
                }
        }
    });
    }

    public void reload(){
        startActivity(new Intent(getApplicationContext(), login_activity.class));
        finish();
    }
}