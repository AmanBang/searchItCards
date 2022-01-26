package com.animasium.searchitcards.AnimeShowEPList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.animasium.searchitcards.AnimeWeb;
import com.animasium.searchitcards.R;
import com.animasium.searchitcards.WebView;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public class EpisodeAdpater extends RecyclerView.Adapter<EpisodeAdpater.EviewHolder> {

    LayoutInflater EInflator;
    List<Episode> episodeList;
    String idSh;
    String Seas;
    String EP = "";

    public EpisodeAdpater(Context EInflator, List<Episode> episodeList) {
        this.EInflator = LayoutInflater.from(EInflator);
        this.episodeList = episodeList;
    }

    @NonNull
    @Override
    public EviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("ResourceType") View view = EInflator.inflate(R.layout.custom_episode_layout, parent, false);
        return new EviewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EviewHolder holder, int position) {
        holder.title.setText(episodeList.get(position).getTitle());
        holder.aired.setText(episodeList.get(position).getAired());
        holder.episodeNo.setText(episodeList.get(position).getEpisode_id());
        holder.filler.setText(episodeList.get(position).getFiller());
        idSh = episodeList.get(position).getShowID();
        Seas = episodeList.get(position).getSeason();
        EP = position + "";
        String dub = episodeList.get(position).getDubLink();

        if (episodeList.get(position).getEpisodeDetails() == null) {
            holder.details.setVisibility(View.GONE);
            holder.image.setVisibility(View.GONE);
            holder.HD.setText("Sub");
            holder.FHD.setText("Dub");
            if (dub.equals("a")) {
                holder.FHD.setVisibility(View.INVISIBLE);
            } else if (!dub.equals("a")) {
                holder.FHD.setVisibility(View.VISIBLE);
            }
        } else {
            holder.details.setText(episodeList.get(position).getEpisodeDetails());
            Picasso.get().load("https://image.tmdb.org/t/p/w500" + episodeList.get(position).getEpisodeImage()).into(holder.image);

        }
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    public class EviewHolder extends RecyclerView.ViewHolder {

        TextView title, aired, episodeNo, filler, details;
        ImageView image;
        Button HD, FHD, FHDplus;
        String l;

        public EviewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.episode_title);
            aired = itemView.findViewById(R.id.episode_airdate);
            episodeNo = itemView.findViewById(R.id.episode_number);
            filler = itemView.findViewById(R.id.filler);
            details = itemView.findViewById(R.id.episode_details);
            image = itemView.findViewById(R.id.episode_image);
            HD = itemView.findViewById(R.id.play_HD);
            FHD = itemView.findViewById(R.id.play_FHD);
            FHDplus = itemView.findViewById(R.id.play_FHDplus);


            HD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (episodeList.get(getAdapterPosition()).getEpisodeDetails() == null) {
                        Intent i = new Intent(itemView.getContext(), AnimeWeb.class);
                        i.putExtra("AnimewatchID", episodeList.get(getAdapterPosition()).getSubLink());
                        v.getContext().startActivity(i);
                    } else {
                        Intent i = new Intent(itemView.getContext(), WebView.class);
                        String link = "https://series.databasegdriveplayer.co/player.php?type=series&tmdb=" + idSh + "&season=" + Seas + "&episode=" + episodeNo.getText();
                        Log.i("LinkSHow", link);
                        i.putExtra("watchID", link);
                        v.getContext().startActivity(i);

                    }
                }
            });
            FHD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (episodeList.get(getAdapterPosition()).getEpisodeDetails() == null) {
                        Intent i = new Intent(itemView.getContext(), AnimeWeb.class);
                        i.putExtra("AnimewatchID", episodeList.get(getAdapterPosition()).getDubLink());
                        v.getContext().startActivity(i);
                    } else {
                        Intent i = new Intent(itemView.getContext(), WebView.class);
                        String link = "https://www.2embed.ru/embed/tmdb/tv?id=" + idSh + "&s=" + Seas + "&e=" + episodeNo.getText();
//                    String link = "https://curtstream.ir/series/imdb/"+idSh+"/"+Seas+"/"+episodeNo.getText();
                        Log.i("LinkSHow", link);
                        i.putExtra("watchID", link);
                        v.getContext().startActivity(i);
                    }


                }
            });
//            FHDplus.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    try { getLink(idSh,Seas,episodeNo.getText()+"");
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
//
//                    Intent i = new Intent(itemView.getContext(), WebView.class);
//                    i.putExtra("watchID2", "https://getsuperembed.link/?video_id="+idSh+"&tmdb=1&s="+Seas+"&e="+episodeNo.getText());
//                    itemView.getContext().startActivity(i);
//                }
//            });


        }

        public void getLink(String id, String season, String episode) throws IOException {
            Document document = Jsoup.connect("https://getsuperembed.link/?video_id=" + id + "&tmdb=1&s=" + season + "&e=" + episode).get();
            String l = document.getElementsByTag("body").text();
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, 2000);
        }

    }


}
