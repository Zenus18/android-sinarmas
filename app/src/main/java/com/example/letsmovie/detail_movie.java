package com.example.letsmovie;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import Adapter.TrailerAdapter;
import Fetch.FetchMovies;
import Fetch.GetMovieTrailer;
import data.MovieSearchData;
import data.trailerData;


public class detail_movie extends AppCompatActivity {
    TextView judul, tahun_terbit, overview, trailer, genresM;
    ImageView  poster;
    RecyclerView recyclerView;
    VideoView videoView;
    private ArrayList<trailerData> values;
    private TrailerAdapter trailerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));
        actionBar.setDisplayHomeAsUpEnabled(true);
        judul = findViewById(R.id.title_movie);
        tahun_terbit = findViewById(R.id.date_movie);
        overview = findViewById(R.id.deskripsi_movie);
        poster = findViewById(R.id.img_movie_detail);
        trailer = findViewById(R.id.Trailer);
        genresM = findViewById(R.id.genre_movie);
        //validasi untuk mendapatkan Intent
        Intent intent = getIntent();
        // Mendapatkan bundle yang berisi data yang dikirim melalui intent
        Bundle bundle = intent.getExtras();
        String judulFilm = bundle.getString("movieTitle");
        String overview_mov = bundle.getString("movieOverview");
        String realease_date = bundle.getString("movieReleaseDate");
        String image_path = bundle.getString("movieImagePath");
        String movieId = bundle.getString("MovieID");
        String genres = bundle.getString("genresMovie");
        if (genres != null){
            genresM.setText(genres);
        }
        judul.setText(judulFilm);
        tahun_terbit.setText(realease_date);
        overview.setText(overview_mov);
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + image_path)
                .into(poster);
        //trailer action
        recyclerView = findViewById(R.id.trailerRv);
        values = new ArrayList<>();
        trailerAdapter = new TrailerAdapter(this, values);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(trailerAdapter);
        setTrailer(movieId);
    }
    public void setTrailer(String ID){
        new GetMovieTrailer(this, values, trailerAdapter, recyclerView).execute(ID);
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