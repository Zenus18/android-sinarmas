package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letsmovie.GenreDashboard;
import com.example.letsmovie.R;
import java.util.ArrayList;
import data.GenresList;


public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {
    private Context context;
    private ArrayList<GenresList> values;
    private LayoutInflater inflater;
    public GenreAdapter(Context context, ArrayList<GenresList> values){
        this.context = context;
        this.values = values;
        this.inflater = LayoutInflater.from(context);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_genres, parent, false);
        return new GenreAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GenresList data = values.get(position);
        holder.txt_genre.setText(data.name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            GenresList genreselected = values.get(holder.getAdapterPosition());
                Intent intent = new Intent(context, GenreDashboard.class);
                intent.putExtra("id_genre", genreselected.id);
                intent.putExtra("name_genre", genreselected.name);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_genre;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        txt_genre = itemView.findViewById(R.id.txt_genres);
        }

    }
}
