package com.animasium.searchitcards.AnimeShowEPList;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.animasium.searchitcards.Scraper.TVshowScraper;
import com.animasium.searchitcards.WebView;
import com.squareup.picasso.Picasso;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import de.mustafagercek.materialloadingbutton.LoadingButton;

public class EpisodeAdpater extends RecyclerView.Adapter<EpisodeAdpater.EviewHolder> {

    LayoutInflater EInflator;
    List<Episode> episodeList;
    String idSh;
    String Seas;
    String EP = "";
    Activity activity;

    private String unityGameId = "4157281";
    private boolean testMode = false;
    private String interPlacement = "Interstitial_Android";


    public EpisodeAdpater(Context EInflator, List<Episode> episodeList, Activity activity) {
        this.EInflator = LayoutInflater.from(EInflator);
        this.episodeList = episodeList;
        this.activity = activity;
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
        Button HD, FHD;
        LoadingButton FHDplus;
        String l;

        LoadingButton hindiTVshow;
        public EviewHolder(@NonNull View itemView) {
            super(itemView);

            UnityAds.initialize(activity, unityGameId, testMode);
            IUnityAdsListener interListner = new IUnityAdsListener() {
                @Override
                public void onUnityAdsReady(String s) {

                }

                @Override
                public void onUnityAdsStart(String s) {

                }

                @Override
                public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {

                }

                @Override
                public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {

                }
            };
            UnityAds.setListener(interListner);
            UnityAds.load(interPlacement);

            title = itemView.findViewById(R.id.episode_title);
            aired = itemView.findViewById(R.id.episode_airdate);
            episodeNo = itemView.findViewById(R.id.episode_number);
            filler = itemView.findViewById(R.id.filler);
            details = itemView.findViewById(R.id.episode_details);
            image = itemView.findViewById(R.id.episode_image);
            HD = itemView.findViewById(R.id.play_HD);
            FHD = itemView.findViewById(R.id.play_FHD);
            FHDplus = itemView.findViewById(R.id.play_FHDplus);
            hindiTVshow = itemView.findViewById(R.id.TVLinksServer);


            HD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    if (UnityAds.isReady(interPlacement)) {
//                        UnityAds.show(activity, interPlacement);
//                    }

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
//                    if (UnityAds.isReady(interPlacement)) {
//                        UnityAds.show(activity, interPlacement);
//                    }
                    if (episodeList.get(getAdapterPosition()).getEpisodeDetails() == null) {
                        Intent i = new Intent(itemView.getContext(), AnimeWeb.class);
                        i.putExtra("AnimewatchID", episodeList.get(getAdapterPosition()).getDubLink());
                        v.getContext().startActivity(i);
                    } else {
                        Intent i = new Intent(itemView.getContext(), WebView.class);
                        String link = "https://www.2embed.to/embed/tmdb/tv?id=" + idSh + "&s=" + Seas + "&e=" + episodeNo.getText();
//                    String link = "https://curtstream.ir/series/imdb/"+idSh+"/"+Seas+"/"+episodeNo.getText();
                        Log.i("LinkSHow", link);
                        i.putExtra("watchID", link);
                        v.getContext().startActivity(i);
                    }


                }
            });
            FHDplus.setButtonOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (UnityAds.isReady(interPlacement)) {
//                        UnityAds.show(activity, interPlacement);
//                    }
                            FHDplus.onStartLoading();
                    final String[] linktt = new String[1];
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                TVshowScraper tVshowScraper = new TVshowScraper();
                                linktt[0] = tVshowScraper.scraper(idSh, Seas, (String) episodeNo.getText());
                                    Log.d("linkaspa", "onClick: "+linktt[0]);
                                    Intent i = new Intent(itemView.getContext(), WebView.class);
                                    i.putExtra("watchID", linktt[0]);
                                    itemView.getContext().startActivity(i);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                                    FHDplus.onStopLoading();
                }
            });


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
