package com.example.searchitcards;

import android.annotation.SuppressLint;
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

import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchShow extends RecyclerView.Adapter<SearchShow.AnimeHolder> {
    LayoutInflater animeInflatorSearch;
    List<Topresults> animeResultSearch;

    public SearchShow(Context animeSearchCtx, List<Topresults> animeResultSearch){
        this.animeInflatorSearch = LayoutInflater.from(animeSearchCtx);
        this.animeResultSearch = animeResultSearch;

    }

    @NonNull
    @Override
    public AnimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        @SuppressLint("ResourceType") View view = animeInflatorSearch.inflate(R.layout.custom_search_result_view,parent,false);
        return new AnimeHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull AnimeHolder holder, int position) {
        holder.Title.setText(animeResultSearch.get(position).getTitle());
     //   holder.rank.setText(String.valueOf(animeResult.get(position).getRank()));
      //  holder.Episode.setText(String.valueOf(animeResultSearch.get(position).getType()));
        holder.Episode.setText((animeResultSearch.get(position).getType()));
        holder.Mal_Id.setText(String.valueOf(animeResultSearch.get(position).getMal_id()));
        holder.ratingBar.setRating(animeResultSearch.get(position).getScore()/2);
        Picasso.get().load(animeResultSearch.get(position).getImage_url()).into(holder.AnimeCover);
    }

    @Override
    public int getItemCount() {
        return animeResultSearch.size();
    }

    public class AnimeHolder extends RecyclerView.ViewHolder {
        RatingBar ratingBar;
        TextView Title,Episode,Mal_Id;
        ImageView AnimeCover;

        public AnimeHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String pa = String.valueOf(animeResultSearch.get(getAdapterPosition()).getMal_id());
                    Intent x = new Intent(view.getContext(), AnimeDeatails.class);
                    x.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    x.putExtra("passing_id",pa );
                    view.getContext().startActivity(x);

                }
            });

            ratingBar = itemView.findViewById(R.id.rating_search);
            Title =  itemView.findViewById(R.id.title_search);
           // rank = itemView.findViewById(R.id.animeRank);
            AnimeCover = itemView.findViewById(R.id.image);
            Episode = itemView.findViewById(R.id.search_episode);
            Mal_Id = itemView.findViewById(R.id.search_mal_id);
        }
    }
}

