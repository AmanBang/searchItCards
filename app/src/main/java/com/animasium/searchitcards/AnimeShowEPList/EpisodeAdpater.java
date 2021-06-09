package com.animasium.searchitcards.AnimeShowEPList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.animasium.searchitcards.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EpisodeAdpater extends RecyclerView.Adapter<EpisodeAdpater.EviewHolder> {

    LayoutInflater EInflator;
    List<Episode> episodeList;

    public EpisodeAdpater(Context EInflator, List<Episode> episodeList) {
        this.EInflator = LayoutInflater.from(EInflator);
        this.episodeList = episodeList;
    }

    @NonNull
    @Override
    public EviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("ResourceType") View view = EInflator.inflate(R.layout.custom_episode_layout,parent,false);
        return new EviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EviewHolder holder, int position) {

        holder.title.setText(episodeList.get(position).getTitle());
        holder.aired.setText(episodeList.get(position).getAired());
        holder.episodeNo.setText(episodeList.get(position).getEpisode_id());
        holder.filler.setText(episodeList.get(position).getFiller());
        if (episodeList.get(position).getEpisodeDetails() == null){
            holder.details.setVisibility(View.GONE);
            holder.image.setVisibility(View.GONE);
        }else {
            holder.details.setText(episodeList.get(position).getEpisodeDetails());
            Picasso.get().load("https://image.tmdb.org/t/p/w500"+ episodeList.get(position).getEpisodeImage()).into(holder.image);

        }

    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    public class EviewHolder extends RecyclerView.ViewHolder{

        TextView title, aired, episodeNo,filler,details;
        ImageView image;
        public EviewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.episode_title);
            aired = itemView.findViewById(R.id.episode_airdate);
            episodeNo = itemView.findViewById(R.id.episode_number);
            filler = itemView.findViewById(R.id.filler);
            details = itemView.findViewById(R.id.episode_details);
            image = itemView.findViewById(R.id.episode_image);


        }
    }
}
