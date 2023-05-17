package Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.letsmovie.R;
import com.example.letsmovie.detail_movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import data.MovieSearchData;

public class SearchMoviesAdapter extends RecyclerView.Adapter<SearchMoviesAdapter.ViewHolder> {
    private Context context1;
    private ArrayList<MovieSearchData> values;
    private LayoutInflater inflater;
    public SearchMoviesAdapter(Context context, ArrayList<MovieSearchData> values){
        this.context1 = context;
        this.values = values;
        this.inflater = LayoutInflater.from(context1);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_search_movie, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    MovieSearchData data = values.get(position);
    //Menampilkan interface dari recyclerview
    holder.movies_title.setText(data.MoviesTittle);
    holder.overview.setText(data.MoviesOverview);
        String posterPath = data.MoviesImagePath;
        String imageUrl = "https://image.tmdb.org/t/p/w500" + posterPath;
        Picasso.get().load(imageUrl).into(holder.search_movie_image);
        Glide.with(context1)
                .asBitmap()
                .load(imageUrl)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        // Menggunakan image bitmap di sini
                        holder.search_movie_image.setImageBitmap(resource);
                    }
                });
        //saat recyclerview di click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected movie
                MovieSearchData selectedMovie = values.get(holder.getAdapterPosition());
                // Create Intent to open detail page
                Intent intent = new Intent(context1, detail_movie.class);
                // Add selected movie data to Intent
                intent.putExtra("movieTitle", selectedMovie.MoviesTittle);
                intent.putExtra("movieOverview", selectedMovie.MoviesOverview);
                intent.putExtra("movieReleaseDate", selectedMovie.releasedate);
                intent.putExtra("movieImagePath", selectedMovie.MoviesImagePath);
                intent.putExtra("MovieID", selectedMovie.MovieId);
                intent.putExtra("RateMovie", selectedMovie.Rate);
                intent.putExtra("genresMovie", selectedMovie.genres);
                // Start detail activity
                context1.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView movies_title, overview;
        ImageView search_movie_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           movies_title = itemView.findViewById(R.id.title_movie);
           overview = itemView.findViewById(R.id.description_movie);
           search_movie_image = itemView.findViewById(R.id.search_movie_img);
        }

    }
}
