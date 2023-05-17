package Fetch;

import static android.content.ContentValues.TAG;

import android.content.Context;
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
import java.util.List;

import Adapter.MoviesAdapter;
import AddOn.ReadUrl;
import data.MovieData;

public class FetchMovies extends AsyncTask<List<JSONObject>, Void, List<JSONObject>> {
    private ArrayList<MovieData> values;
    private MoviesAdapter MoviesAdapter;
    private RecyclerView recyclerView;
    private Context context;
    private  String filter;
    private int page;
    public FetchMovies(Context context, ArrayList<MovieData> values,
                       MoviesAdapter moviesAdapter, RecyclerView rvmovie, int page, String filter) {
        this.values = values;
        this.MoviesAdapter = moviesAdapter;
        this.context = context;
        this.recyclerView = rvmovie;
        this.page = page;
        this.filter = filter;
    }

    @Override
    protected List<JSONObject> doInBackground(List<JSONObject>... lists) {
        List<JSONObject> result = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/"+filter+"?api_key=a76bee06956c9a4cfd14133c1f84dec0&language=en-US&page=" + page +"&include_adult=false";
        String genresUrl = "https://api.themoviedb.org/3/genre/movie/list?api_key=a76bee06956c9a4cfd14133c1f84dec0&language=en";
        //menggunakan fungsi dari addon saya untuk mengambil MoviesJSONString
        ReadUrl Data = new ReadUrl();
        String MoviesJSONString = Data.ReadUrl(url);
        String GenresString = Data.ReadUrl(genresUrl);
        // add result JSONObject with data and  genres data
        try {
            JSONObject data = new JSONObject(MoviesJSONString);
            JSONObject genresData = new JSONObject(GenresString);
            result.add(data);
            result.add(genresData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(List<JSONObject> result) {
        super.onPostExecute(result);
        values = new ArrayList<>();
        try {
            //mengambil result
            JSONArray moviesArray = result.get(0).getJSONArray("results");
            JSONArray genresArray = result.get(1).getJSONArray("genres");
            //mengambil name dari id genre di genres.json
            String title=null, date=null, overview=null, poster_path=null, rate=null, id=null, MovieGenres=null;
            for(int i = 0; i<moviesArray.length(); i++){
                JSONObject results = moviesArray.getJSONObject(i);
                try{
                    title = results.getString("title");
                    date = results.getString("release_date");
                    overview = results.getString("overview");
                    poster_path =  results.getString("poster_path");
                    rate = results.getString("vote_average");
                    id = results.getString("id");
                    //mengambil genre (ruwet)
                    JSONArray movGen = results.getJSONArray("genre_ids");
                    String genre = "";
                    for(int j = 0; j<genresArray.length(); j++){
                        for (int k = 0; k<movGen.length(); k++){
                            JSONObject CurrentGen = genresArray.getJSONObject(j);
                            if (CurrentGen.getInt("id") == movGen.getInt(k)){
                                genre += CurrentGen.getString("name") + ", ";
                            }
                        }
                    }
                    genre = genre.substring(0, genre.length() -2);
                    MovieGenres = genre;
                    MovieData movieData = new MovieData();
                    movieData.title = title;
                    movieData.date = date;
                    movieData.overview = overview;
                    movieData.posterPath = poster_path;
                    movieData.rate = rate;
                    movieData.id = id;
                    movieData.genres = MovieGenres;
                    values.add(movieData);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.MoviesAdapter = new MoviesAdapter(context, values);
        this.recyclerView.setAdapter(this.MoviesAdapter);
    }
}
