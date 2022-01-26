package com.animasium.searchitcards;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.animasium.searchitcards.Movie.mAdapterclasses.Movies;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LiveTVAdapter extends RecyclerView.Adapter<LiveTVAdapter.LiveTVHolder> {

    LayoutInflater inflatorTV;
    List<Channel> moviesList;

    public LiveTVAdapter(Context inflatorTV, List<Channel> moviesList) {
        this.inflatorTV = LayoutInflater.from(inflatorTV);
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public LiveTVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflatorTV.inflate(R.layout.custom_movies_card,parent,false);

        return new LiveTVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LiveTVHolder holder, int position) {
        Picasso.get().load(moviesList.get(position).getLogo()).into(holder.channelLogo);

        holder.channelName.setText(moviesList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return moviesList.size();

    }

    public class LiveTVHolder extends RecyclerView.ViewHolder {

        ImageView channelLogo;
        TextView channelName;

        public LiveTVHolder(@NonNull View itemView) {
            super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(),VideoPlayerActivity.class);
                        i.putExtra("liveLink",moviesList.get(getAdapterPosition()).getUrl());
                        v.getContext().startActivity(i);
                    }
                });



            channelLogo = itemView.findViewById(R.id.moviePoster);
            channelName = itemView.findViewById(R.id.cardMovieTitle);
        }
    }
}
