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

import Adapter.GenreAdapter;
import Adapter.MoviesAdapter;
import AddOn.ReadUrl;
import data.GenresList;
import data.MovieData;

public class FetchGenre extends AsyncTask<List<JSONObject>, Void, List<JSONObject>> {
    private ArrayList<GenresList> values;
    private RecyclerView rv_genres;
    private GenreAdapter genreAdapter;
    private Context context;
    public FetchGenre(Context context, ArrayList<GenresList> values,
                      GenreAdapter genreAdapter, RecyclerView rv_genres){
       this.context = context;
       this.values =  values;
       this.genreAdapter = genreAdapter;
       this.rv_genres = rv_genres;
    }
    @Override
    protected List<JSONObject> doInBackground(List<JSONObject>... lists) {
        List<JSONObject> result = new ArrayList<>();
        String genresUrl = "https://api.themoviedb.org/3/genre/movie/list?api_key=a76bee06956c9a4cfd14133c1f84dec0&language=en";
        ReadUrl Data = new ReadUrl();
        String GenresString = Data.ReadUrl(genresUrl);
        JSONObject genresData = null;
        try {
            genresData = new JSONObject(GenresString);
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
            JSONArray genresArray = result.get(0).getJSONArray("genres");
            Log.d(TAG, "nugroho: "+ genresArray);
            String nameG = null, id=null;
            for(int i =  0; i<genresArray.length(); i++){
                JSONObject genresOBJ = genresArray.getJSONObject(i);
                try{
                    nameG = genresOBJ.get("name").toString();
                    id = genresOBJ.get("id").toString();
                }catch (Exception e) {
                    e.printStackTrace();
                }
               GenresList data = new GenresList();
                data.name = nameG;
                data.id = id;
                values.add(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.genreAdapter = new GenreAdapter(context, values);
        this.rv_genres.setAdapter(this.genreAdapter);
    }
}
