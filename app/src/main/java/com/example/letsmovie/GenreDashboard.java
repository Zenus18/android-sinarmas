package com.example.letsmovie;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import Adapter.GenreDashboardAdapter;
import Adapter.MoviesAdapter;
import Fetch.FetchGenreDashboard;
import Fetch.FetchMovies;
import data.GenreDashboardData;
import data.MovieData;

public class GenreDashboard extends AppCompatActivity {
    private String name_genre;
    private GenreDashboardAdapter genreDashboardAdapter;
    private ArrayList<GenreDashboardData> values;
    ImageView previous, next;
    private int page;
    private RecyclerView rvgd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_dashboard);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));
        actionBar.setDisplayHomeAsUpEnabled(true);
        //validasi untuk mendapatkan Intent
        Intent intent = getIntent();
        // Mendapatkan bundle yang berisi data yang dikirim melalui intent
        Bundle bundle = intent.getExtras();
        String id_genre = bundle.getString("id_genre");
        Log.d(TAG, "anu: "+id_genre);
        name_genre = bundle.getString("name_genre");
        actionBar.setTitle(name_genre);
        values = new ArrayList<>();
        rvgd = findViewById(R.id.rvdg);
        page = 1;
        genreDashboardAdapter = new GenreDashboardAdapter(this, values);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvgd.setLayoutManager(gridLayoutManager);
        setAdapter(id_genre);
    }
    public void setAdapter(String id_genre){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            if(id_genre.length() > 0) {
                new FetchGenreDashboard(this, values, genreDashboardAdapter, rvgd, page, id_genre).execute();
            }
        }else {
            Toast.makeText(this, "tidak terhubung ke internet", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}