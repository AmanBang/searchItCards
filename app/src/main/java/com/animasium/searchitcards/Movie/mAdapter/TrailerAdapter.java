package com.animasium.searchitcards.Movie.mAdapter;

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

import com.animasium.searchitcards.Movie.MovieTrailer;
import com.animasium.searchitcards.Movie.mAdapterclasses.Trailer;
import com.animasium.searchitcards.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder> {


    LayoutInflater trailerInflator;
    List<Trailer> trailerList;

    public TrailerAdapter(Context trailerInflator, List<Trailer> trailerList) {
        this.trailerInflator = LayoutInflater.from(trailerInflator);
        this.trailerList = trailerList;
    }


    @NonNull
    @Override
    public TrailerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = trailerInflator.inflate(R.layout.custon_videoview,parent,false);
        return new TrailerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerHolder holder, int position) {
        holder.name.setText(trailerList.get(position).getName());
        Picasso.get().load("https://img.youtube.com/vi/"+trailerList.get(position).getKey()+"/hqdefault.jpg").into(holder.tumbnail);

    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public class TrailerHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView tumbnail;

        public TrailerHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ikal = new Intent(v.getContext(), MovieTrailer.class);
                    Log.i("ahad",trailerList.get(getAdapterPosition()).getKey());
                    ikal.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ikal.putExtra("movie_trailer",trailerList.get(getAdapterPosition()).getKey());
                    v.getContext().startActivity(ikal);
                }
            });

            name = itemView.findViewById(R.id.youtubeTitle);
            tumbnail = itemView.findViewById(R.id.youtubrTumbnail);


        }
    }
}
