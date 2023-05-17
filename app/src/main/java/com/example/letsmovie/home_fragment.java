package com.example.letsmovie;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import Adapter.GenreAdapter;
import Adapter.MoviesAdapter;
import AddOn.LoadingDialog;
import Fetch.FetchGenre;
import Fetch.FetchMovies;
import Fetch.FetchSearchMovies;
import data.GenresList;
import data.MovieData;


public class home_fragment extends Fragment {
    Spinner filter;
    RecyclerView recyclerView;
    ImageView previous, next;
    private MoviesAdapter moviesAdapter;
    private ArrayList<MovieData> values;
    private int page;
    private String selectedItem;
    private RecyclerView genre_rv;
    private GenreAdapter genreAdapter;
    private ArrayList<GenresList> Gvalues;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        // Inflate the layout for this fragment
        filter = rootview.findViewById(R.id.filter);
        recyclerView = rootview.findViewById(R.id.rvmovie);
        page = 1;
        values = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(getContext(), values);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
           setAdapter("popular");
        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = parent.getItemAtPosition(position).toString();
                switch (selectedItem){
                    case "Popular":
                        setAdapter("popular");
                        page = 1;
                        break;
                    case "Top Rated":
                        setAdapter("top_rated");
                        page = 1;
                        break;
                    case "Up Coming":
                        setAdapter("upcoming");
                        page = 1;
                        break;
                    case "Now Playing":
                        setAdapter("now_playing");
                        page = 1;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        previous = rootview.findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page -= 1;
                switch (selectedItem){
                    case "Popular":
                        setAdapter("popular");
                        break;
                    case "Top Rated":
                        setAdapter("top_rated");
                        break;
                    case "Lastest":
                        setAdapter("latest");
                        break;
                    case "Now Playing":
                        setAdapter("now_playing");
                        page = 1;
                        break;
                }
                if (page == 1){
                    previous.setEnabled(false);
                }else{
                    previous.setEnabled(true);
                }
            }
        });
        next = rootview.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page +=1;
                switch (selectedItem){
                    case "Popular":
                        setAdapter("popular");
                        break;
                    case "Top Rated":
                        setAdapter("top_rated");
                        break;
                    case "Lastest":
                        setAdapter("latest");
                        break;
                    case "Now Playing":
                        setAdapter("now_playing");
                        page = 1;
                        break;
                }
                if (page == 1){
                    previous.setEnabled(false);
                }else{
                    previous.setEnabled(true);
                }
            }
        });
        if (page == 1){
            previous.setEnabled(false);
        }else{
            previous.setEnabled(true);
        }
        genre_rv = rootview.findViewById(R.id.rv_genres);
        Gvalues = new ArrayList<>();
        genreAdapter = new GenreAdapter(getContext(), Gvalues);
        GridLayoutManager gridLayoutManagerG = new GridLayoutManager(getContext(), 3);
        genre_rv.setLayoutManager(gridLayoutManagerG);
        getGenres();
        return rootview;
    }
    public void setAdapter(String filter){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            if(filter.length() > 0) {
                new FetchMovies(getContext(), values, moviesAdapter, recyclerView, page, filter).execute();
            }
        }else {
            Toast.makeText(getContext(), "tidak terhubung ke internet", Toast.LENGTH_SHORT).show();
        }
    }
    public void getGenres(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            new FetchGenre(getContext(), Gvalues, genreAdapter, genre_rv).execute();
        }else {
            Toast.makeText(getContext(), "tidak terhubung ke internet", Toast.LENGTH_SHORT).show();
        }
    }
}
