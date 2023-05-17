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

import data.MovieData;
import data.MovieSearchData;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MovieData> values;
    private LayoutInflater inflater;
    public MoviesAdapter(Context context, ArrayList<MovieData> values){
        this.context = context;
        this.values = values;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieData data = values.get(position);
        holder.title.setText(data.title);
        holder.date.setText(data.genres);
        holder.rate.setText(data.rate);
            String PosterPath = data.posterPath;
            String ImgUrl = "https://image.tmdb.org/t/p/w500" + PosterPath;
        Glide.with(context)
                .asBitmap()
                .load(ImgUrl)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        // Menggunakan image bitmap di sini
                        holder.poster.setImageBitmap(resource);
                    }
                });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieData selectedMovie = values.get(holder.getAdapterPosition());
                Intent intent = new Intent(context, detail_movie.class);
                intent.putExtra("movieTitle", selectedMovie.title);
                intent.putExtra("movieOverview", selectedMovie.overview);
                intent.putExtra("movieReleaseDate", selectedMovie.date);
                intent.putExtra("movieImagePath", selectedMovie.posterPath);
                intent.putExtra("MovieID", selectedMovie.id);
                intent.putExtra("RateMovie", selectedMovie.rate);
                intent.putExtra("genresMovie", selectedMovie.genres);
                // Start detail activity
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView poster;
        TextView title, date, rate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.img_movie);
            title = itemView.findViewById(R.id.title_movie);
            date =  itemView.findViewById(R.id.date_movie);
            rate = itemView.findViewById(R.id.rate_movie);
        }

    }
}
