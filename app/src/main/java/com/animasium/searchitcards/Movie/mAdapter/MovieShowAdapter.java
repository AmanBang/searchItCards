package com.animasium.searchitcards.Movie.mAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.animasium.searchitcards.Movie.DeatailsMovie;
import com.animasium.searchitcards.R;
import com.animasium.searchitcards.Movie.mAdapterclasses.MSearchResultConst;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieShowAdapter extends RecyclerView.Adapter<MovieShowAdapter.MovieShowHolder> {

    LayoutInflater showInflator;
    List<MSearchResultConst> showList;

    public MovieShowAdapter(Context showInflator, List<MSearchResultConst> showList) {
        this.showInflator = LayoutInflater.from(showInflator);
        this.showList = showList;
    }

    @NonNull
    @Override
    public MovieShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = showInflator.inflate(R.layout.custom_movie_result_card,parent,false);
        return new MovieShowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieShowHolder holder, int position) {

        holder.title.setText(showList.get(position).getTitle());
        holder.releaseDate.setText(showList.get(position).getRelease_date());
        float f = (float) showList.get(position).getVote_average();
        holder.ratu.setRating(f/2);
        holder.ratu.setNumStars(5);

        Picasso.get().load("https://image.tmdb.org/t/p/w500"+showList.get(position).getPoster_path()).into(holder.posterView);

    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    public class MovieShowHolder extends RecyclerView.ViewHolder{
        TextView title,releaseDate;
        ImageView posterView;
        RatingBar ratu;
       public MovieShowHolder(@NonNull View itemView) {
           super(itemView);

           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent jio = new Intent(v.getContext(), DeatailsMovie.class);
                   jio.putExtra("searchToDetails",showList.get(getAdapterPosition()).getId());
                   v.getContext().startActivity(jio);
               }
           });



           title = itemView.findViewById(R.id.movieSearch_titleholder);
           releaseDate= itemView.findViewById(R.id.movieSearch_releaseDate);
           posterView = itemView.findViewById(R.id.movie_search_result_poster);
           ratu = itemView.findViewById(R.id.ratingTheMovie);
        }
   }
}
