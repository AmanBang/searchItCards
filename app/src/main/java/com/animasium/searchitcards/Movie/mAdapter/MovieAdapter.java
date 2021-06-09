package com.animasium.searchitcards.Movie.mAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.animasium.searchitcards.Movie.DeatailsMovie;
import com.animasium.searchitcards.Movie.mAdapterclasses.Movies;
import com.animasium.searchitcards.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    LayoutInflater inflatorMovie;
    List<Movies> moviesList;

    public MovieAdapter(Context inflatorMovie, List<Movies> moviesList) {
        this.inflatorMovie = LayoutInflater.from(inflatorMovie);
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
View view = inflatorMovie.inflate(R.layout.custom_movies_card,parent,false);
return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {

        Picasso.get().load("https://image.tmdb.org/t/p/w500"+moviesList.get(position).getPoster_path()).into(holder.moviePoster);

            holder.movieTile.setText(moviesList.get(position).getTitle());



    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    public class MovieHolder extends RecyclerView.ViewHolder{

        ImageView moviePoster;

        TextView movieTile,ShowName;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent x = new Intent(v.getContext(), DeatailsMovie.class);
                    x.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    x.putExtra("pass_id",String.valueOf(moviesList.get(getAdapterPosition()).getId()));
                    v.getContext().startActivity(x);

                }
            });

            moviePoster = itemView.findViewById(R.id.moviePoster);
            movieTile = itemView.findViewById(R.id.cardMovieTitle);



        }
    }
}
