package Fetch;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapter.GenreDashboardAdapter;
import Adapter.MoviesAdapter;
import AddOn.ReadUrl;
import data.GenreDashboardData;
import data.MovieData;

public class FetchGenreDashboard extends AsyncTask<List<JSONObject>, Void, List<JSONObject>> {
    private ArrayList<GenreDashboardData> values;
    private GenreDashboardAdapter genreDashboardAdapter;
    private RecyclerView rvgd;
    private Context context;
    private  String id_genres;
    private int page;
    public FetchGenreDashboard(Context context, ArrayList<GenreDashboardData> values,
                               GenreDashboardAdapter genreDashboardAdapter, RecyclerView rvgd, int page, String filter) {
        this.values = values;
        this.genreDashboardAdapter = genreDashboardAdapter;
        this.context = context;
        this.rvgd= rvgd;
        this.page = page;
        this.id_genres = filter;
    }

    @Override
    protected List<JSONObject> doInBackground(List<JSONObject>... lists) {
        List<JSONObject> result = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=a76bee06956c9a4cfd14133c1f84dec0&with_genres=" + id_genres;
        String genresUrl = "https://api.themoviedb.org/3/genre/movie/list?api_key=a76bee06956c9a4cfd14133c1f84dec0&language=en";
        ReadUrl Data = new ReadUrl();
        String GenresJSONString =  Data.ReadUrl(url);
        String GenresString = Data.ReadUrl(genresUrl);
        try {
            JSONObject genresDashboarData = new JSONObject(GenresJSONString);
            JSONObject genresData = new JSONObject(GenresString);
            result.add(genresDashboarData);
            result.add(genresData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(List<JSONObject> jsonObjects) {
        super.onPostExecute(jsonObjects);
        values = new ArrayList<>();
        try {
            JSONArray moviesArray = jsonObjects.get(0).getJSONArray("results");
            JSONArray genresArray = jsonObjects.get(1).getJSONArray("genres");
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
                    GenreDashboardData movieData = new GenreDashboardData();
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.genreDashboardAdapter = new GenreDashboardAdapter(context, values);
        this.rvgd.setAdapter(this.genreDashboardAdapter);
    }
}
