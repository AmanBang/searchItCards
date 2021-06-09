package com.animasium.searchitcards.Anime.AdapterCLass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.animasium.searchitcards.Anime.AnimeDeatails;
import com.animasium.searchitcards.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class SMAdapter extends RecyclerView.Adapter<SMAdapter.SMore> {

    List<AShowMore> SMList;
    LayoutInflater SMInflator;

    public SMAdapter(Context SMInflator, List<AShowMore> SMList){
        this.SMInflator = LayoutInflater.from(SMInflator);
        this.SMList = SMList;
    }

    @NonNull
    @Override
    public SMAdapter.SMore onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = SMInflator.inflate(R.layout.custom_anime_recommendation,parent,false);
        return new SMore(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SMAdapter.SMore holder, int position) {
        Picasso.get().load(SMList.get(position).getPoster_path()).into(holder.Poster);
        holder.Tiltle.setText(SMList.get(position).getTitle());


    }

    @Override
    public int getItemCount() {
        return SMList.size();
    }

    public class SMore extends RecyclerView.ViewHolder {
        TextView Tiltle;
        ImageView Poster;
        public SMore(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), AnimeDeatails.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("title_Id_Pass", String.valueOf(SMList.get(getAdapterPosition()).getId()));
                    v.getContext().startActivity(i);
                }
            });


            Tiltle = itemView.findViewById(R.id.anime_r_title);
            Poster = itemView.findViewById(R.id.anime_r_poster);

        }
    }
}
