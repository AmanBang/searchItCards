package com.example.searchitcards.YoutubePlayer;

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

import com.example.searchitcards.Anime.Adapter.AdapterClass.Promo;
import com.example.searchitcards.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class YoutubeVideoAdapter extends RecyclerView.Adapter<YoutubeVideoAdapter.VideoHolder> {
List<Promo> Yvideos;
LayoutInflater YvideoInflter;

public YoutubeVideoAdapter(Context YvideoCtx,List<Promo> Yvideos ){
    this.YvideoInflter =LayoutInflater.from(YvideoCtx);
    this.Yvideos =Yvideos;
}

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("ResourceType") View view = YvideoInflter.inflate(R.layout.custon_videoview,parent,false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {
      holder.yTitle.setText(Yvideos.get(position).getTitle());
      Picasso.get().load(Yvideos.get(position).getImage_url()).into(holder.yThumnail);
    }

    @Override
    public int getItemCount() {
        return Yvideos.size();
    }

    public class VideoHolder extends RecyclerView.ViewHolder{

        ImageView yThumnail;
        TextView yTitle;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent kali = new Intent(v.getContext(), Youtube.class);
                    kali.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    kali.putExtra("youtube_url",Yvideos.get(getAdapterPosition()).getVideo_url());
                    Log.i("urlPass",Yvideos.get(getAdapterPosition()).getVideo_url());
                    v.getContext().startActivity(kali);
                }
            });

            yThumnail =itemView.findViewById(R.id.youtubrTumbnail);
            yTitle = itemView.findViewById(R.id.youtubeTitle);


        }
    }
}
