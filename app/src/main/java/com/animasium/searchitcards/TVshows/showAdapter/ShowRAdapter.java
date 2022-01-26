package com.animasium.searchitcards.TVshows.showAdapter;

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

import com.animasium.searchitcards.Movie.DeatailsMovie;
import com.animasium.searchitcards.R;
import com.animasium.searchitcards.RecommendedMovies;
import com.animasium.searchitcards.TVshows.ShowDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShowRAdapter extends RecyclerView.Adapter<ShowRAdapter.SRViewHolder> {

    LayoutInflater SInfaltor;
    List<RecommendedMovies> zList;

    public ShowRAdapter(Context SInfaltor, List<RecommendedMovies> Zlist) {
        this.SInfaltor = LayoutInflater.from(SInfaltor);
        zList = Zlist;



    }

    @NonNull
    @Override
    public SRViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("ResourceType") View view = SInfaltor.inflate(R.layout.custom_anime_recommendation,parent,false);
        return new SRViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SRViewHolder holder, int position) {
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+zList.get(position).getPoster_path()).into(holder.animePoster);
        holder.animeTitle.setText(zList.get((position)).getTitle());
    }

    @Override
    public int getItemCount() {
        return zList.size();
    }

    public class SRViewHolder extends RecyclerView.ViewHolder{
        TextView animeTitle;
        ImageView animePoster;
        public SRViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), ShowDetails.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("pass_id", String.valueOf(zList.get(getAdapterPosition()).getId()));
                    v.getContext().startActivity(i);

                }
            });

//

            animeTitle = itemView.findViewById(R.id.anime_r_title);
            animePoster = itemView.findViewById(R.id.anime_r_poster);
        }
    }
}
