package Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.letsmovie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


import data.trailerData;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder>{
    private Context context;
    private ArrayList<trailerData> values;
    private LayoutInflater inflater;
    public TrailerAdapter(Context context, ArrayList<trailerData> values) {
        this.context = context;
        this.values = values;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.trailer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        trailerData data = values.get(position);
        String key = data.trailerKey;
        Glide.with(context)
                .asBitmap()
                .load("https://img.youtube.com/vi/"+key+"/maxresdefault.jpg")
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        // Menggunakan image bitmap di sini
                        holder.trailer.setImageBitmap(resource);
                    }
                });
        holder.trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + key)));
            }
        });

    }
    @Override
    public int getItemCount() {
        return values.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView trailer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            trailer  = itemView.findViewById(R.id.trailerView);
        }

    }
}
