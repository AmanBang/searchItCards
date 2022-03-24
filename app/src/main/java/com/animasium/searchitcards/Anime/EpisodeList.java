package com.animasium.searchitcards.Anime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.animasium.searchitcards.AnimeShowEPList.Episode;
import com.animasium.searchitcards.AnimeShowEPList.EpisodeAdpater;
import com.animasium.searchitcards.R;
import com.animasium.searchitcards.Scraper.HindiTVShowScraper;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.mustafagercek.materialloadingbutton.LoadingButton;

public class EpisodeList extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RecyclerView episodeRecycle;
    EpisodeAdpater episodeAdpater;
    List<Episode> episodeList;

    CardView cardView;
    int color;
    TextView filler;
    JSONObject object;
    int numb = 0;
    int k = 1;
    int lastPage;
    String eUrl;

    TextView episdoeDetails;
    ImageView episodeImage;

    ProgressBar progressBar;

    Spinner spinner;
    Spinner spinner2;


    LoadingButton hindiTVshow;

    LinearLayoutManager mLayoutManager;
    private final boolean loading = true;

    List<Integer> page;
    List<Integer> seasons;
    List<String> sublink;
    List<String> dubLink;
    boolean first1 = true;

    LinearLayout pageLayout;
    LinearLayout seasonsLayout;
    Handler handler;
    String seasonNumber;
    String eUrl2;
    private String sea = "1";
    String links="";
    private String links1 = "";
    private String showName;

    public void getList(String Urly) {

        episodeList.clear();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, Urly, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {

                try {
                    lastPage = response.getInt("episodes_last_page");
                    JSONArray jsonArray = response.getJSONArray("episodes");
                    if (!jsonArray.isNull(0) && (jsonArray.length() >= sublink.size())){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        object = jsonArray.getJSONObject(i);
                        Episode anime = new Episode();
                        anime.setEpisode_id(object.getString("episode_id"));
                        anime.setAired(object.getString("aired"));
                        anime.setTitle(object.getString("title"));
                        anime.setSubLink(sublink.get(i));
                        anime.setDubLink(dubLink.get(i));
                        if (object.getBoolean("filler")) {
                            anime.setFiller(" Filler ");
                        } else {
                            Log.i("sda", "asda");
                        }

                        episodeList.add(anime);


                    }

                    }else {
                        for (int e=0;e<sublink.size();e++){
                        Episode episode = new Episode();
                        episode.setSubLink(sublink.get(e));
//                        episode.setDubLink(dubLink.get(e));
                        episode.setEpisode_id(String.valueOf(e+1));
                        episodeList.add(episode);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (lastPage == 1) {
                    pageLayout.setVisibility(View.GONE);
                }

                if (first1) {
                    for (int m = 1; m <= lastPage; m++) {
                        page.add(m);
                    }
                    ArrayAdapter<Integer> integerArrayAdapter = new ArrayAdapter<Integer>(EpisodeList.this,
                            android.R.layout.simple_gallery_item, page);
                    integerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(integerArrayAdapter);
                    first1 = false;
                } else {
                    Log.i("sd", "asda");
                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        episodeRecycle.setLayoutManager(new LinearLayoutManager(EpisodeList.this));
                        episodeAdpater = new EpisodeAdpater(EpisodeList.this, episodeList,EpisodeList.this);
                        episodeRecycle.setAdapter(episodeAdpater);
                        progressBar.setVisibility(View.GONE);
                    }
                }, 200);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(objectRequest);

    }

    public void getList2(String Urly) {

        episodeList.clear();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, Urly, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("episodes");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        object = jsonArray.getJSONObject(i);
                        Episode anime = new Episode();

                        anime.setAired(object.getString("air_date"));
                        anime.setTitle(object.getString("name"));
                        anime.setEpisodeImage(object.getString("still_path"));
                        anime.setEpisodeDetails(object.getString("overview"));
                        anime.setEpisode_id(object.getString("episode_number"));
                        anime.setShowID(eUrl2);
                        anime.setSeason(sea);
                        episodeList.add(anime);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                if (lastPage == 1){
//                    pageLayout.setVisibility(View.GONE);
//                }
//

//
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        episodeRecycle.setLayoutManager(new LinearLayoutManager(EpisodeList.this));
                        episodeAdpater = new EpisodeAdpater(EpisodeList.this, episodeList,EpisodeList.this);
                        episodeRecycle.setAdapter(episodeAdpater);
                        progressBar.setVisibility(View.GONE);
                    }
                }, 500);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(objectRequest);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_list);

        episodeImage = findViewById(R.id.episode_image);
        episdoeDetails = findViewById(R.id.episode_details);
        progressBar = findViewById(R.id.episodelist_probressBar);


        episodeRecycle = findViewById(R.id.episode_list_recycle_hello);
        episodeList = new ArrayList<>();
        cardView = findViewById(R.id.episode_cardView);
        filler = findViewById(R.id.filler);
        page = new ArrayList<>();
        seasons = new ArrayList<>();
        spinner = findViewById(R.id.episode_page_spinner);
        spinner2 = findViewById(R.id.episode_season_spinner);
        sublink = new ArrayList<>();
        dubLink = new ArrayList<>();

//        filler.setVisibility(View.INVISIBLE);
        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        pageLayout = findViewById(R.id.page_layout);
        seasonsLayout = findViewById(R.id.season_layout);
//        hindiTVshow = findViewById(R.id.TVLinksServer);

        Intent i = getIntent();
        eUrl = i.getStringExtra("episodeList_1");
        eUrl2 = i.getStringExtra("episodeList_2");
        seasonNumber = i.getStringExtra("season_Numbers");
        links = i.getStringExtra("episodeListZ");
        links1 = i.getStringExtra("episodeListZ1");
        showName = i.getStringExtra("ShowNameEpisode");
        Log.i("episodesare", "onCreate: "+links);

//60574

        if (links != null){
            new SearchingEpisodes().execute();
        }

        if (eUrl != null) {
            seasonsLayout.setVisibility(View.GONE);
        } else {
            pageLayout.setVisibility(View.GONE);

            String k = "https://api.themoviedb.org/3/tv/" + eUrl2 + "/season/1?api_key=e707c6ad620e69cda284fbbc6af06e43";
            getList2(k);
            Log.i("sd", k);


            if (first1) {
                for (int m = 1; m <= Integer.parseInt(seasonNumber); m++) {
                    seasons.add(m);
                }
                ArrayAdapter<Integer> integerArrayAdapter = new ArrayAdapter<Integer>(EpisodeList.this,
                        android.R.layout.simple_gallery_item, seasons);
                integerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(integerArrayAdapter);
                first1 = false;
            } else {
                Log.i("sd", "asda");
            }
        }


        handler = new Handler();



    }

    private void getEpisodelink() {
        if (!links.equals("")) {
            try {
                Log.i("episodesare:link", "doInBackground: " + links);
                Document searching = Jsoup.connect(links).get();
                Log.i("episodesare:link", "doInBackground: " + searching);
                Elements li = searching.select("div[class=anime_video_body]").select("ul[id=episode_page]").select("li");
                String a = String.valueOf(li.select("a").eq(li.size() - 1).html());
                int end;
                if (a.contains("-")) {
                    int index = a.indexOf('-');
                    end = Integer.parseInt(a.substring(index + 1));
                } else {
                    end = Integer.parseInt(a);
                }
                Log.d("episodesare", String.valueOf(end));
                String putlink = links.replace("/category", "");
                if (end != 0)
                    for (int i = 1; i <= end; i++) {
                        String c = putlink + "-episode-" + i;
                        Log.i("cis", c);
                        Log.d("episodesare", c);
                        sublink.add(c);
                    }
                else {
                    String c = putlink + "-episode-" + 0;
                    sublink.add(c);
                }

                if (!links1.equals("")) {
                    Document document = Jsoup.connect(links1).get();
                    Elements elements = document.select("div[class=anime_video_body]").select("ul[id=episode_page]").select("li");
                    String a1 = String.valueOf(elements.select("a").eq(li.size() - 1).html());
                    int end1;
                    if (a.contains("-")) {
                        int index = a1.indexOf('-');
                        end1 = Integer.parseInt(a1.substring(index + 1));
                    } else {
                        end1 = Integer.parseInt(a1);
                    }
                    Log.d("episodesare", String.valueOf(end1));
                    String putlink1 = links1.replace("/category", "");
                    if (end1 != 0)
                        for (int i = 1; i <= end; i++) {

                            if (i <=end1){
                                String c = putlink1 + "-episode-" + i;
                                Log.i("cis", c);
                                Log.d("episodesare:Dub", c);
                                dubLink.add(c);
                            }else {
                                String c = "a";
                                Log.i("cis", c);
                                Log.d("episodesare:Dub", c);
                                dubLink.add(c);
                            }


                        }
                    else {

                        String c = putlink1 + "-episode-" + 0;
                        dubLink.add(c);
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.episode_page_spinner) {
//           String p = String.valueOf();
//           Log.i("pod",position+"");
            if (eUrl != null) {


                getList(eUrl + "/episodes/" + (position + 1));
            }

        }
        if (parent.getId() == R.id.episode_season_spinner) {
            episodeList.clear();
            if ((position + 1) != 1) {
                String m = "https://api.themoviedb.org/3/tv/" + eUrl2 + "/season/" + (position + 1) + "?api_key=e707c6ad620e69cda284fbbc6af06e43";
//                Log.i("hell",(position+1)  + "");
                sea = position + 1 + "";
                getList2(m);
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class SearchingEpisodes extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... params) {
            getEpisodelink();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (eUrl != null){
                getList(eUrl + "/episodes");
            }
        }
    }
}















