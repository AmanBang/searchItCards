package com.example.searchitcards;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterRMovies  extends RecyclerView.Adapter<AdapterRMovies.RMViewHolder> {

    LayoutInflater RMInfaltor;
    List<RecommendedMovies> RMList;

    public AdapterRMovies(Context RMInfaltor, List<RecommendedMovies> RMList) {
        this.RMInfaltor = LayoutInflater.from(RMInfaltor);
        this.RMList = RMList;
    }

    @NonNull
    @Override
    public RMViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("ResourceType") View view = RMInfaltor.inflate(R.layout.custom_anime_recommendation,parent,false);
        return new RMViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RMViewHolder holder, int position) {
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+RMList.get(position).getPoster_path()).into(holder.Poster);
        holder.Tiltle.setText(RMList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return RMList.size();
    }

    public class RMViewHolder extends RecyclerView.ViewHolder{

        TextView Tiltle;
        ImageView Poster;

        public RMViewHolder(@NonNull View itemView) {
            super(itemView);

            Tiltle = itemView.findViewById(R.id.anime_r_title);
            Poster = itemView.findViewById(R.id.anime_r_poster);

        }
    }
}
