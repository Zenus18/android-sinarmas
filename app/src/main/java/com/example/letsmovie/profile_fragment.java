package com.example.letsmovie;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.NetworkOnMainThreadException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class profile_fragment extends Fragment {
    LinearLayout btn_signout;
    TextView usn;
    FirebaseAuth  firebaseAuth;
    FirebaseUser currentUser;
    String userId;
    CardView account, about, CS, detailed_account;
    EditText usr, usremail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_profile_fragment, container, false);
        usn =  rootview.findViewById(R.id.username);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        //set textview agar muncul nama user yang login
        userId = currentUser.getUid();
        usn.setText(currentUser.getDisplayName());
        //membuat bagian detailed_account agar muncul
        account = rootview.findViewById(R.id.account);
        detailed_account = rootview.findViewById(R.id.detailed_account);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailed_account.getVisibility() == View.GONE){
                    detailed_account.setVisibility(View.VISIBLE);
                }else{
                    detailed_account.setVisibility(View.GONE);
                }
            }
        });
        //code untuk sign out
        btn_signout = rootview.findViewById(R.id.btn_logout);
        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alert untuk validasi
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("are you sure to exit now?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                if (firebaseAuth.getCurrentUser() != null){
                                    firebaseAuth.signOut();
                                    startActivity(new Intent(getContext(), welcome_activity.class));
                                    getActivity().finish();
                                }
                            }
                        });
                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
        //intent to  about page
        about =  rootview.findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), About.class));
            }
        });
        //create intent for user feedback
        String email = "nuganuca17@gmail.com";
        CS = rootview.findViewById(R.id.CS);
        CS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + email));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "dear, lazier");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "you can message your problem here");
                startActivity(Intent.createChooser(emailIntent, "Send feedback"));
            }
        });
        usr = rootview.findViewById(R.id.usr);
        usremail = rootview.findViewById(R.id.usr_email);
        usr.setText(currentUser.getDisplayName());
        usremail.setText(currentUser.getEmail());
        return rootview;
    }
}