package com.example.letsmovie;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import Adapter.GenreAdapter;
import Adapter.SearchMoviesAdapter;
import Fetch.FetchGenre;
import Fetch.FetchSearchMovies;
import data.GenresList;
import data.MovieSearchData;


public class search_fragment extends Fragment {
    private EditText search_txt;
    private ImageView search_image;
    private RecyclerView movie_rv;
    private LinearLayout search_loading_view;
    private ArrayList<MovieSearchData> values;
    private SearchMoviesAdapter SearchMoviesAdapter;
    String text_search;
    View rootview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootview = inflater.inflate(R.layout.fragment_search_fragment, container, false);
        // Inflate the layout for this fragment
        search_txt = rootview.findViewById(R.id.txt_search);
        movie_rv = rootview.findViewById(R.id.movie_rv);

        search_loading_view =  rootview.findViewById(R.id.search_loading_view);
        values  = new ArrayList<>();

        SearchMoviesAdapter = new SearchMoviesAdapter(getContext(), values);

        movie_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        movie_rv.setAdapter(SearchMoviesAdapter);
        search_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                movie_rv.setVisibility(View.GONE);
              search_loading_view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                text_search = s.toString();
                if (text_search.length() != 0){
                    search_loading_view.setVisibility(View.GONE);
                    movie_rv.setVisibility(View.VISIBLE);
                    // tambahkan kode untuk text itu
                    SearchMovie(text_search);
                }else{
                    search_loading_view.setVisibility(View.VISIBLE);
                    movie_rv.setVisibility(View.GONE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return rootview;
    }
    public void SearchMovie(String queryString){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            if(queryString.length() > 0) {
                new FetchSearchMovies(getContext(), values, SearchMoviesAdapter, movie_rv, queryString).execute();
            }
        }else {
            Toast.makeText(getContext(), "tidak terhubung ke internet", Toast.LENGTH_SHORT).show();
        }
    }
}