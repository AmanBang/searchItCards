package com.example.searchitcards;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.searchitcards.Movie.mAdapter.DeatailsMovie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class showSearchAdapter extends RecyclerView.Adapter<showSearchAdapter.ShowView> {

    LayoutInflater layoutInflater;
    List<Show> list;

    public showSearchAdapter(Context layoutInflater, List<Show> list) {
        this.layoutInflater = LayoutInflater.from(layoutInflater);
        this.list = list;
    }

    @NonNull
    @Override
    public ShowView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_movie_result_card,parent,false);
        return new ShowView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowView holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.releaseDate.setText(list.get(position).getRelease_date());
        holder.ratu.setRating(list.get(position).getVote_average()/2);
        holder.ratu.setNumStars(6);

        Picasso.get().load("https://image.tmdb.org/t/p/w500"+list.get(position).getPoster_path()).into(holder.posterView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ShowView extends RecyclerView.ViewHolder{

        TextView title,releaseDate;
        ImageView posterView;
        RatingBar ratu;
        public ShowView(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent jio = new Intent(v.getContext(), ShowDetails.class);
                    jio.putExtra("pass_id",list.get(getAdapterPosition()).getId());
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
