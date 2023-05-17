package Fetch;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import Adapter.TrailerAdapter;
import AddOn.ReadUrl;
import data.trailerData;

public class GetMovieTrailer extends AsyncTask<String, Void, String> {
    private ArrayList<trailerData> values;
    Context context;
    RecyclerView rvtrailer;
    TrailerAdapter trailerAdapter;
    public GetMovieTrailer (Context context, ArrayList<trailerData> values,
                            TrailerAdapter trailerAdapter, RecyclerView rvtrailer){
        this.context = context;
        this.values = values;
        this.trailerAdapter = trailerAdapter;
        this.rvtrailer = rvtrailer;

    }
    @Override
    protected String doInBackground(String... strings) {
        String movieId = strings[0];
        String BASE_URL = "https://api.themoviedb.org/3/movie/";
        String VIDEOS_PATH = "videos";
        String API_KEY = "a76bee06956c9a4cfd14133c1f84dec0";
        String  trailerJSONString;
        // Build URL for getting movie trailer
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(movieId)
                .appendPath(VIDEOS_PATH)
                .appendQueryParameter("api_key", API_KEY)
                .build();
        ReadUrl readUrl = new ReadUrl();
        trailerJSONString = readUrl.ReadUrl(builtUri.toString());
        return trailerJSONString;
    }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray trailerArray = jsonObject.getJSONArray("results");
                String type;
                String key = null;
                for ( int i =0; i<trailerArray.length(); i++) {
                    JSONObject videos = trailerArray.getJSONObject(i);
                    try {
                        type = videos.getString("type");
                        if (type.equals("Trailer")) {
                            key = videos.getString("key");
                            trailerData trailerData = new trailerData();
                            trailerData.trailerKey = key;
                            values.add(trailerData);
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                } catch (JSONException ex) {
                ex.printStackTrace();
            }
            this.trailerAdapter = new TrailerAdapter(context, values);
            this.rvtrailer.setAdapter(trailerAdapter);
        }


}

