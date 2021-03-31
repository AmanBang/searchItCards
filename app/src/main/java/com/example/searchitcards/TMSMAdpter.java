package com.example.searchitcards;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.searchitcards.Anime.AdapterCLass.WantMCLass;
import com.example.searchitcards.Movie.DeatailsMovie;
import com.example.searchitcards.TVshows.ShowDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TMSMAdpter extends RecyclerView.Adapter<TMSMAdpter.WantMore> {

//    LayoutInflater TMShowMoreInflator;
//    List<WantMCLass> TMLISt;
//
//    public TMSMAdpter(Context TMShowMoreInflator, List<WantMCLass> TMLISt) {
//        this.TMShowMoreInflator = LayoutInflater.from(TMShowMoreInflator);
//        this.TMLISt = TMLISt;
//    }

    LayoutInflater RMInfaltor;
    List<WantMCLass> RMList;

    public TMSMAdpter(Context RMInfaltor, List<WantMCLass> RMList) {
        this.RMInfaltor = LayoutInflater.from(RMInfaltor);
        this.RMList = RMList;
    }
    @NonNull
    @Override
    public TMSMAdpter.WantMore onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("ResourceType") View view = RMInfaltor.inflate(R.layout.custom_anime_recommendation,parent,false);
        return new WantMore(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TMSMAdpter.WantMore holder, int position) {
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+RMList.get(position).getPoster_path()).into(holder.Poster);
        holder.Tiltle.setText(RMList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return RMList.size();
    }

    public class WantMore extends RecyclerView.ViewHolder {

        TextView Tiltle;
        ImageView Poster;
        public WantMore(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (RMList.get(getAdapterPosition()).getMovie()){
                        Intent i = new Intent(v.getContext(), DeatailsMovie.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("movieId", String.valueOf(RMList.get(getAdapterPosition()).getId()));
                        v.getContext().startActivity(i);
                    }else {
                        Intent i = new Intent(v.getContext(), ShowDetails.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("show_id", String.valueOf(RMList.get(getAdapterPosition()).getId()));
                        v.getContext().startActivity(i);
                    }
                    Log.i("pasdasdasd1:",RMList.get(getAdapterPosition()).getId());
                    Log.i("pasdasdasd",RMList.get(getAdapterPosition()).getTitle());

                }
            });

            Tiltle = itemView.findViewById(R.id.anime_r_title);
            Poster = itemView.findViewById(R.id.anime_r_poster);
        }
    }
}
