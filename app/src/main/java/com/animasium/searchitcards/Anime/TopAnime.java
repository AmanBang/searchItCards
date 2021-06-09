package com.animasium.searchitcards.Anime;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.animasium.searchitcards.Anime.AdapterCLass.Topresults;
import com.animasium.searchitcards.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopAnime extends RecyclerView.Adapter<TopAnime.AnimeHolder> {
LayoutInflater animeInflator;
List<Topresults> animeResult;

    public TopAnime(Context animeCtx, List<Topresults> animeResult){
      this.animeInflator = LayoutInflater.from(animeCtx);
        this.animeResult = animeResult;

    }

    @NonNull
    @Override
    public AnimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        @SuppressLint("ResourceType") View view = animeInflator.inflate(R.layout.featured_cardview,parent,false);
        return new AnimeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeHolder holder, int position) {
        holder.Title.setText(animeResult.get(position).getTitle());
//        holder.rank.setText(String.valueOf(animeResult.get(position).getRank()));
//        holder.Episode.setText(String.valueOf(animeResult.get(position).getEpisode()));
       // holder.Episode.setText((animeResult.get(position).getEpisode()));
        holder.Mal_Id.setText(String.valueOf(animeResult.get(position).getMal_id()));
//        holder.ratingBar.setRating(animeResult.get(position).getScore()/2);
        holder.Released.setText(animeResult.get(position).getStart_date());
        holder.Type.setText(animeResult.get(position).getType());
        Picasso.get().load(animeResult.get(position).getImage_url()).into(holder.AnimeCover);
    }

    @Override
    public int getItemCount() {
        return animeResult.size();
    }

    public class AnimeHolder extends RecyclerView.ViewHolder {
//        RatingBar ratingBar;
        TextView Title,rank,Episode,Mal_Id,Type,Released;
        ImageView AnimeCover;

        public AnimeHolder(@NonNull View itemView) {
            super(itemView);


           itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(v.getContext(), AnimeDeatails.class);
                   i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("title_Id_Pass", String.valueOf(animeResult.get(getAdapterPosition()).getMal_id()));
                    v.getContext().startActivity(i);
//                    FancyToast.makeText(v.getContext(),"ADDED to Favourites", FancyToast.LENGTH_SHORT,FancyToast.INFO,true).show();


                }
            });



//            ratingBar = itemView.findViewById(R.id.rating_search);
            Title =  itemView.findViewById(R.id.title_search);
//            rank = itemView.findViewById(R.id.animeRank);
            AnimeCover = itemView.findViewById(R.id.image);
//            Episode = itemView.findViewById(R.id.search_episode);
            Mal_Id = itemView.findViewById(R.id.search_mal_id);
            Type =itemView.findViewById(R.id.search_type);
            Released = itemView.findViewById(R.id.search_released);
        }
    }
}
