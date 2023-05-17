package Fetch;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

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

import Adapter.SearchMoviesAdapter;
import AddOn.ReadUrl;
import data.MovieSearchData;

public class FetchSearchMovies extends AsyncTask<List<JSONObject>, Void, List<JSONObject>> {
    private ArrayList<MovieSearchData> values;
    private SearchMoviesAdapter searchMoviesAdapter;
    private RecyclerView recyclerView;
    private Context context;
    private String query;
    public FetchSearchMovies(Context context, ArrayList<MovieSearchData> values,
                             SearchMoviesAdapter searchMoviesAdapter, RecyclerView movie_rv, String query) {
        this.values = values;
        this.searchMoviesAdapter = searchMoviesAdapter;
        this.context = context;
        this.recyclerView = movie_rv;
        this.query = query;
    }
    @Override
    protected List<JSONObject> doInBackground(List<JSONObject>... lists) {
        String queryString = query;
        List<JSONObject> result = new ArrayList<>();
        String QUERY_MOVIE = "query";
        String url = "https://api.themoviedb.org/3/search/movie?api_key=a76bee06956c9a4cfd14133c1f84dec0&language=id-ID";
        String genresUrl = "https://api.themoviedb.org/3/genre/movie/list?api_key=a76bee06956c9a4cfd14133c1f84dec0&language=en";
        String  searchMoviesJSONString = null;
        Uri builUri = Uri.parse(url).buildUpon().appendQueryParameter(QUERY_MOVIE, queryString).build();
        ReadUrl readUrl = new ReadUrl();
        String SearchJSONString = readUrl.ReadUrl(builUri.toString());
        String GenresString = readUrl.ReadUrl(genresUrl);
        try {
            JSONObject jsonObject = new JSONObject(SearchJSONString);
            JSONObject genresData = new JSONObject(GenresString);
            result.add(jsonObject);
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
            JSONArray moviesArray = result.get(0).getJSONArray("results");
            JSONArray genresArray = result.get(1).getJSONArray("genres");
            String MoviesTittle = null;
            String MoviesOverview = null;
            String releasedate = null;
            String MoviesImagePath = null;
            String MovieID = null;
            String rate = null;
            String MovieGenres = null;
            for(int i = 0; i<moviesArray.length(); i++){
                JSONObject results = moviesArray.getJSONObject(i);
                try{
                    MoviesTittle = results.getString("title");
                    releasedate = results.getString("release_date");
                    MoviesOverview = results.getString("overview");
                    MoviesImagePath =  results.getString("poster_path");
                    MovieID = results.getString("id");
                    rate = results.getString("vote_average");
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
                    MovieSearchData MovieData= new MovieSearchData();
                   MovieData.MoviesTittle = MoviesTittle;
                   MovieData.MoviesOverview = MoviesOverview;
                   MovieData.releasedate = releasedate;
                   MovieData.MoviesImagePath = MoviesImagePath;
                   MovieData.MovieId = MovieID;
                   MovieData.Rate = rate;
                   MovieData.genres = MovieGenres;
                   values.add(MovieData);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.searchMoviesAdapter = new SearchMoviesAdapter(context, values);
        this.recyclerView.setAdapter(this.searchMoviesAdapter);
    }


}
