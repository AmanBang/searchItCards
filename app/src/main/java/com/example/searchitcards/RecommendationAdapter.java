package com.example.searchitcards;

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

import com.example.searchitcards.Anime.Adapter.AdapterClass.RecommendedAnime;
import com.example.searchitcards.Anime.AnimeDeatails;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RecommendationAdapter extends  RecyclerView.Adapter<RecommendationAdapter.RViewHolder>{

   LayoutInflater RlaoutInflator;
   List<RecommendedAnime> Ranime;

   public RecommendationAdapter(Context rlaoutInflator, List<RecommendedAnime> ranime) {
      RlaoutInflator = LayoutInflater.from(rlaoutInflator);
      Ranime = ranime;
   }

   @NonNull
   @Override
   public RViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      @SuppressLint("ResourceType") View view = RlaoutInflator.inflate(R.layout.custom_anime_recommendation,parent,false);
      return new RecommendationAdapter.RViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull RViewHolder holder, int position) {
      Picasso.get().load(Ranime.get(position).getImage_url()).into(holder.animePoster);
      holder.animeTitle.setText(Ranime.get(position).getTitle());

   }

   @Override
   public int getItemCount() {
      return Ranime.size();
   }

   public class RViewHolder extends RecyclerView.ViewHolder {


      TextView animeTitle;
      ImageView animePoster;

   public RViewHolder(@NonNull View itemView) {
      super(itemView);

      itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
           Intent i = new Intent(v.getContext(), AnimeDeatails.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("title_Id_Pass", String.valueOf(Ranime.get(getAdapterPosition()).getMal_id()));
            v.getContext().startActivity(i);

         }
      });

      animeTitle = itemView.findViewById(R.id.anime_r_title);
      animePoster = itemView.findViewById(R.id.anime_r_poster);

   }
}
}
