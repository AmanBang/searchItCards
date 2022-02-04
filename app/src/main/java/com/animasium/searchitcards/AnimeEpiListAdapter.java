package com.animasium.searchitcards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.animasium.searchitcards.Movie.DeatailsMovie;
import com.animasium.searchitcards.Room.EpisodeDataBase;
import com.animasium.searchitcards.Room.EpisodePOJO;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public class AnimeEpiListAdapter extends RecyclerView.Adapter<AnimeEpiListAdapter.AnimeHolder> {

    LayoutInflater inflater;
    List<AnimePOJO> animePOJOList;
    Activity activity;
    private String unityGameId = "4157281";
    private boolean testMode = false;
    private String interPlacement = "Interstitial_Android";

    public AnimeEpiListAdapter(Context inflater, List<AnimePOJO> animePOJOList, Activity activity) {
        this.inflater = LayoutInflater.from(inflater);
        this.animePOJOList = animePOJOList;
        this.activity = activity;

    }

    @NonNull
    @Override
    public AnimeEpiListAdapter.AnimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapterforepisode,parent,false);
        return new AnimeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeEpiListAdapter.AnimeHolder holder, int position) {

        holder.textView.setText(animePOJOList.get(position).getEpisode()+". "+animePOJOList.get(position).getName());

        EpisodeDataBase dataBase = EpisodeDataBase.getInstance(inflater.getContext());

        if (dataBase.EpiDao().checkEpiByNameAndEpisodeNo(animePOJOList.get(position).getName(),animePOJOList.get(position).getEpisode()) != 0){
            holder.relativeLayout.setBackgroundColor(Color.GRAY);
        }

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EpisodePOJO epiPOJO = new EpisodePOJO(animePOJOList.get(position).Name,animePOJOList.get(position).episode);
                EpisodePOJO epiData = dataBase.EpiDao().getEpiByNameAndEpisodeNo(epiPOJO.getName(),epiPOJO.getEpisodeNo());

                if (epiData != null){

                dataBase.EpiDao().deleteEpiByNameAndEpisodeNo(epiPOJO.getName(),epiData.getEpisodeNo());
                }

                EpisodePOJO episode = new EpisodePOJO(epiPOJO.getName(),epiPOJO.getEpisodeNo());
                dataBase.EpiDao().insertEpi(episode);
                holder.relativeLayout.setBackgroundColor(Color.GRAY);
                if (UnityAds.isReady(interPlacement)) {
                    UnityAds.show(activity, interPlacement);
                }
                Intent i = new Intent(inflater.getContext(),AnimeWeb.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("AnimewatchID",animePOJOList.get(position).getLink());
                inflater.getContext().startActivity(i);

            }
        });

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Document[] gogoAnimePageDocument = {null};

                final String[] splitString = new String[1];
                new Thread( new Runnable() { @Override public void run() {
                    try {
                        gogoAnimePageDocument[0] = Jsoup.connect(animePOJOList.get(position).getLink()).get();
                        String vidStreamUrl = "https:" + gogoAnimePageDocument[0].getElementsByClass("play-video").get(0).getElementsByTag("iframe").get(0).attr("src");
                        Log.i("VideoLinkTOPlay",animePOJOList.get(position).getLink());
                        Log.i("VideoLinkTOPlay",vidStreamUrl);
                        splitString[0] =vidStreamUrl.replace("streaming.php","download");
                        Log.i("VideoLinkTOPlay2", splitString[0]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } } ).start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(Uri.parse(splitString[0]));
                        view.getContext().startActivity(intent);
                    }
                },2000);


            }
        });
    }

    @Override
    public int getItemCount() {
        return animePOJOList.size();
    }

    public class AnimeHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button download;
        RelativeLayout relativeLayout;
        public AnimeHolder(@NonNull View itemView) {
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

            textView = itemView.findViewById(R.id.notbutton);
            download = itemView.findViewById(R.id.downloadchoice);
            relativeLayout = itemView.findViewById(R.id.episodeRelativeLayout);
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });

        }
    }
}
