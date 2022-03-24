package com.animasium.searchitcards.Anime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.animasium.searchitcards.AnimeEpisodeList;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.animasium.searchitcards.Anime.Adapter.AdapterClass.Promo;
import com.animasium.searchitcards.Anime.Adapter.AdapterClass.RecommendedAnime;
import com.animasium.searchitcards.Anime.Adapter.SideStoryAdapter;
import com.animasium.searchitcards.YoutubePlayer.YoutubeVideoAdapter;
import com.animasium.searchitcards.Anime.AdapterCLass.SStory;
import com.animasium.searchitcards.R;
import com.animasium.searchitcards.RecommendationAdapter;
import com.google.android.gms.ads.AdView;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import de.mustafagercek.materialloadingbutton.LoadingButton;

public class AnimeDeatails extends AppCompatActivity {

    private static final String TAG = "AnimeDetails";
    TextView textView;
    TextView shortDeatil;
    TextView deatils;


    String Url;

    ImageView imageView;
    String forImage;
    String itemUrl;


    String prequelId = "";
    String sequelId = "";
    JSONObject related;

    ImageView fullScreen;

    boolean firstVideoPlay = true;
    boolean flag = true;
    List<String> playlistUrl;
    String urlp;
    String Urlv;




    //------------------------------------------------------------------------------------------------------- ------------------------------------

    ImageView animePoster;
    TextView animeRank;
    TextView animeTitleEng;
    TextView animeGenres;
    TextView animeStatus;
    TextView animeDescription;
    TextView animeTitle;
    TextView animeDuration;
    TextView animePremier;
    TextView animeBroadcast;
    TextView animeScore;
    TextView animePopularity;
    TextView animeProducer;
    TextView animeLisensor;
    TextView animeStudios;
    TextView animeEpisodes;
    TextView animeAired;
    TextView animeType;


    Button animePrequel;
    Button animeSequel;
    LoadingButton episodeButton;

    Button animeSub;
    Button animeDub;

//    Button animeSideStory;
//    Button animeSummery;


    String addGenres = "";
    String addProducer = "";
    String addStudios = "";
    String addLisensor = "";
    String addPrequel = "";
    String addSequel = "";
    String searchId;
    String searchIdv;
    String Status_check;
//    String addSide_story = "";
//    String addSummary = "";


    JSONObject cluster;

    LinearLayout prequelLaout;
    LinearLayout sequelLaout;
    LinearLayout sideStoryLaout;
    LinearLayout clusterLayout;
    LinearLayout trailerLayout;
    LinearLayout recommendation_section;

    List<SlideModel> modelList;
    List<Promo> Yvideos;
    List<RecommendedAnime> Ranime;
    List<SStory> storyList;

    ImageSlider slider;

    RecyclerView animeTrailerRecycle;
    RecyclerView animeRecommendationRecycle;
    RecyclerView SSRecycle;


    YoutubeVideoAdapter youtubeVideoAdapter;
    RecommendationAdapter Radapter;
    SideStoryAdapter SAdapter;
    private AdView mAdView;
    private String Urlr;
    String passId = "";
    String eNumb;
    Boolean reequestCashed;
    String Jname;
    //    YouTubePlayerView youTubePlayerView;
//    YouTubePlayer.OnInitializedListener onInitializedListener;
//    private YouTubePlayer youTubePlayer;
    String links;
    String links1;
    private LinearLayout animeOptionsLayout;

    public class DetailsAnime extends Thread {

        public DetailsAnime(String top) {


            prequelId = "";
            sequelId = "";
            addGenres = "";
            addProducer = "";
            addLisensor = "";
            addStudios = "";

            Log.i("hoole", passId);


            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                    try {
                        Picasso.get().load(response.getString("image_url")).into(animePoster);
                        Jname = response.getString("title");

//                        getAnimeDetails(Jname);
//                        animeRank.setText("#"+response.getString("rank"));
                        if (!response.getString("title_english").equals("null")) {
                            animeTitleEng.setText(response.getString("title_english"));
                        } else {

                            animeTitleEng.setText(Jname);
                        }

                        JSONArray genres = response.getJSONArray("genres");
                        for (int i = 0; i < genres.length(); i++) {
                            JSONObject genresList = genres.getJSONObject(i);

                            addGenres += genresList.getString("name") + ", ";

                        }
                        animeGenres.setText(addGenres);

                        Status_check = response.getString("status");
                        animeStatus.setText(Status_check);
                        animeDescription.setText(response.getString("synopsis"));
                        animeTitle.setText(response.getString("title"));
                        animeDuration.setText(response.getString("duration"));
                        animePremier.setText(response.getString("premiered"));
                        animeBroadcast.setText(response.getString("broadcast"));
                        animeScore.setText(response.getString("score"));
                        animePopularity.setText(response.getString("popularity"));
                        animeEpisodes.setText(response.getString("episodes"));
                        eNumb = response.getString("episodes");
                        animeType.setText(response.getString("type"));

                        if (response.getString("type").equals("Movie")) {
                            clusterLayout.setVisibility(View.GONE);
                        }
//                            episodeButton.setVisibility(View.GONE);

                        JSONObject aired = response.getJSONObject("aired");
                        animeAired.setText(aired.getString("string"));

                        JSONArray producer = response.getJSONArray("producers");
                        for (int i = 0; i < producer.length(); i++) {
                            JSONObject producerList = producer.getJSONObject(i);
                            addProducer += producerList.getString("name") + ", ";
                        }
                        animeProducer.setText(addProducer);

                        JSONArray lisensor = response.getJSONArray("licensors");
                        for (int i = 0; i < lisensor.length(); i++) {
                            JSONObject lisensorList = lisensor.getJSONObject(i);
                            addLisensor += lisensorList.getString("name") + ", ";
                        }
                        animeLisensor.setText(addLisensor);
                        JSONArray studios = response.getJSONArray("studios");
                        for (int i = 0; i < studios.length(); i++) {
                            JSONObject studiosList = studios.getJSONObject(i);
                            addStudios += studiosList.getString("name") + ", ";
                        }
                        animeStudios.setText(addStudios);

                        //Json object for culster
                        cluster = response.getJSONObject("related");

                        JSONArray prequel = cluster.getJSONArray("Prequel");
                        for (int i = 0; i < prequel.length(); i++) {
                            JSONObject prequelList = prequel.getJSONObject(i);
                            addPrequel = prequelList.getString("name");
                            prequelId = prequelList.getString("mal_id");
                        }
                        animePrequel.setText(addPrequel);

                        JSONArray sequel = cluster.getJSONArray("Sequel");
                        for (int i = 0; i < sequel.length(); i++) {
                            JSONObject sequelList = sequel.getJSONObject(i);
                            addSequel = sequelList.getString("name");
                            sequelId = sequelList.getString("mal_id");
                        }
                        animeSequel.setText(addSequel);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    try {
////                        AnimeScraper scraper = new AnimeScraper();
////                        String[] detailLink = scraper.animeScraper(Jname);
////                        Log.i("detailLink0", detailLink+"");
//                    }catch (NullPointerException e){
//                        e.printStackTrace();
//                    }
                    try {
                        JSONArray side_story = cluster.getJSONArray("Side story");
                        for (int i = 0; i < side_story.length(); i++) {
                            JSONObject side_storylList = side_story.getJSONObject(i);
//                            addSide_story = side_storylList.getString("name") + ", ";

                            SStory sStory = new SStory();

                            sStory.setMal_id(side_storylList.getString("mal_id"));
                            sStory.setName(side_storylList.getString("name"));

                            storyList.add(sStory);

                        }
//                        animeSideStory.setText(addSide_story);
                    } catch (Exception e) {
                        e.printStackTrace();
                        sideStoryLaout.setVisibility(View.GONE);
                    }

                    SSRecycle.setLayoutManager(new LinearLayoutManager(AnimeDeatails.this, LinearLayoutManager.HORIZONTAL, false));
                    SAdapter = new SideStoryAdapter(AnimeDeatails.this, storyList);
                    SSRecycle.setAdapter(SAdapter);
//                    try {
//
//                        JSONArray summary = cluster.getJSONArray("Summary");
//                        for (int i = 0; i < summary.length(); i++) {
//                            JSONObject summaryList = summary.getJSONObject(i);
//                            addSummary = summaryList.getString("name") + ", ";
//                        }
//                        animeSummery.setText(addSummary);
//                    } catch (Exception e) {
//
//                        e.printStackTrace();
//                        summeryLaout.setVisibility(View.GONE);
//                    }
                    Log.i("statusCheck", Status_check);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(request);

            if (Status_check == "Not yet aired") {
                recommendation_section.setVisibility(View.GONE);
            }
        }


    }

    private void getAnimeDetails(String jname) {

        try {


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void ImagesSlideShow(String pic) {
        modelList.clear();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, pic, null, new Response.Listener<JSONObject>() {
            private SlideModel SlideModel;

            @Override
            public void onResponse(JSONObject response) {
                Log.i("resasda", response.toString());
                try {
                    JSONArray pictures = response.getJSONArray("data");


                    for (int i = 0; i < pictures.length(); i++) {

                        if (i < 8) {
                            JSONObject pics = pictures.getJSONObject(i);
                            String s = "";
                            JSONObject jpg = pics.getJSONObject("jpg");
                            s = jpg.getString("image_url");
                            modelList.add(new SlideModel(s));
                        } else {
                            Log.i("not", "to add");
                        }


                    }
                    slider.setImageList(modelList, true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);

    }

    public void youtube(String Urly) {
        Yvideos.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, Urly, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String epis = response.getString("promo");
                    Log.i("epis", epis);
                    if (epis.equals("[]")) {
                        trailerLayout.setVisibility(View.GONE);
                    }
                    JSONArray jsonArray = response.getJSONArray("promo");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Promo p = new Promo();
                        p.setVideo_url(object.getString("video_url"));
                        p.setImage_url(object.getString("image_url"));
                        p.setTitle(object.getString("title"));

                        Yvideos.add(p);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                topAnimeRecycle.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
//                topAnime = new TopAnime(MainActivity.this,animeResult);
//                topAnimeRecycle.setAdapter(topAnime);
                animeTrailerRecycle.setLayoutManager(new LinearLayoutManager(AnimeDeatails.this, LinearLayoutManager.HORIZONTAL, false));
                youtubeVideoAdapter = new YoutubeVideoAdapter(AnimeDeatails.this, Yvideos);
                animeTrailerRecycle.setAdapter(youtubeVideoAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(objectRequest);
    }

    public void Recommend(String Urly) {
        Yvideos.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, Urly, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    Log.d("recommendetion", "onResponse: " + response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        RecommendedAnime anime = new RecommendedAnime();
                        JSONObject entery = object.getJSONObject("entry");
                        anime.setTitle(entery.getString("title"));
                        anime.setMal_id(entery.getString("mal_id"));
                        JSONObject img = entery.getJSONObject("images");
                        JSONObject jpg = img.getJSONObject("jpg");
                        anime.setImage_url(jpg.getString("image_url"));

                        Ranime.add(anime);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                animeRecommendationRecycle.setLayoutManager(new GridLayoutManager(AnimeDeatails.this,3));
                Radapter = new RecommendationAdapter(AnimeDeatails.this, Ranime);
                animeRecommendationRecycle.setAdapter(Radapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(objectRequest);
    }


    //  -----------------------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_anime_deatails);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        animePoster = findViewById(R.id.anime_poster);
//        animeRank = findViewById(R.id.anime_rank);
        animeTitleEng = findViewById(R.id.anime_title_eng);
        animeGenres = findViewById(R.id.anime_genres);
        animeStatus = findViewById(R.id.anime_status);
        animeDescription = findViewById(R.id.anime_description);
        animeTitle = findViewById(R.id.anime_title);
        animeDuration = findViewById(R.id.anime_duration);
        animePremier = findViewById(R.id.anime_premier);
        animeBroadcast = findViewById(R.id.anime_broadcast);
        animeScore = findViewById(R.id.anime_score);
        animePopularity = findViewById(R.id.anime_popularity);
        animeProducer = findViewById(R.id.anime_producers);
        animeLisensor = findViewById(R.id.anime_licensors);
        animeStudios = findViewById(R.id.anime_studios);
        animePrequel = findViewById(R.id.anime_prequel);
        animeSequel = findViewById(R.id.anime_sequel);
//        animeSideStory = findViewById(R.id.anime_sideStory);
//        animeSummery = findViewById(R.id.anime_summery);
        animeEpisodes = findViewById(R.id.anime_episodes);
        animeAired = findViewById(R.id.anime_aired);
        animeType = findViewById(R.id.anime_type);
        episodeButton = findViewById(R.id.anime_episodes_button);
        animeOptionsLayout = findViewById(R.id.AnimeOptionsLayout);
        animeTrailerRecycle = findViewById(R.id.anime_trailer_recycle);
        animeTrailerRecycle.setHasFixedSize(true);
        animeRecommendationRecycle = findViewById(R.id.anime_recommendation_recycle);
        animeRecommendationRecycle.setHasFixedSize(true);
        SSRecycle = findViewById(R.id.anime_sideStory_recycle);
        SSRecycle.setHasFixedSize(true);

        prequelLaout = findViewById(R.id.anime_prequel_layout);
        sequelLaout = findViewById(R.id.anime_sequel_layout);
        sideStoryLaout = findViewById(R.id.anime_sideStory_layout);
        trailerLayout = findViewById(R.id.anime_trailer_Layout);
        clusterLayout = findViewById(R.id.anime_custer_check);
        recommendation_section = findViewById(R.id.recommedation_section);

        animeDub = findViewById(R.id.anime_dubd);
        animeSub = findViewById(R.id.anime_Subd);

        modelList = new ArrayList<>();
        Yvideos = new ArrayList<>();
        Ranime = new ArrayList<>();
        storyList = new ArrayList<>();

        slider = findViewById(R.id.anime_pictures);

//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//
//        mAdView = findViewById(R.id.details_ads);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        //-----------------------------------------------------------------------------------------------------------------------------------------------------
//        textView = findViewById(R.id.title_Eng);
//        shortDeatil = findViewById(R.id.short_details);
//        deatils = findViewById(R.id.deatils);
//        imageView = findViewById(R.id.animeImageView2);
//        prequelButton = findViewById(R.id.prequel);
//        sequelButton= findViewById(R.id.sequel);
//       // youTubePlayerView = findViewById(R.id.youtubePlayer);
//        fullScreen = findViewById(R.id.full_screen);
//
//        playlistUrl = new ArrayList<>();
//
//        imageView.setVisibility(View.GONE);

        Intent i = getIntent();
        Intent x = getIntent();
        Intent y = getIntent();
        searchId = i.getStringExtra("title_Id_Pass");
        searchIdv = x.getStringExtra("passing_id");
        //  String   newRefresh = y.getStringExtra("refreshUrl");

//        textView.setText(searchId);
        if (searchIdv == null) {
            Url = "https://api.jikan.moe/v3/anime/" + searchId;
            urlp = "https://api.jikan.moe/v4/anime/" + searchId + "/pictures";
            Urlv = "https://api.jikan.moe/v3/anime/" + searchId + "/videos";
            Urlr = "https://api.jikan.moe/v4/anime/" + searchId + "/recommendations";
            passId = searchId;
        } else {
            Url = "https://api.jikan.moe/v3/anime/" + searchIdv;
            urlp = "https://api.jikan.moe/v4/anime/" + searchIdv + "/pictures";
            Urlv = "https://api.jikan.moe/v3/anime/" + searchIdv + "/videos";
            Urlr = "https://api.jikan.moe/v4/anime/" + searchIdv + "/recommendations";
            passId =searchIdv;
        }
        Handler handler = new Handler();


        DetailsAnime detailsAnime = new DetailsAnime(Url);
        detailsAnime.start();

        ImagesSlideShow(urlp);
        Log.d("passId  ", "onCreate: "+ passId);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                youtube(Urlv);
            }
        }, 250);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Recommend(Urlr);
            }
        }, 100);

//            onInitializedListener = new YouTubePlayer.OnInitializedListener() {
//                @Override
//                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//
//                    youTubePlayer.loadVideo(pass);
//                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
//
//                }
//
//                @Override
//                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//                    Log.i("responce",youTubeInitializationResult.toString());
//                    Toast.makeText(AnimeDeatails.this, "ERROR:" + youTubeInitializationResult.toString(), Toast.LENGTH_LONG).show();
//                }
//            };


        // youTubePlayerView.initialize("AIzaSyBWUOfzasbM1Gm8WRu2W_lXvV1NPBXy88k",onInitializedListener);
        animeSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent op = new Intent(AnimeDeatails.this, AnimeEpisodeList.class);

                op.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                op.putExtra("animelink", links);
                 op.putExtra("episodes_id",passId);
                AnimeDeatails.this.startActivity(op);
            }
        });
        animeDub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent op = new Intent(AnimeDeatails.this, AnimeEpisodeList.class);

                op.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                op.putExtra("animelink", links1);
                 op.putExtra("episodes_id",passId);
                AnimeDeatails.this.startActivity(op);

            }
        });



        animePrequel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),AnimeDeatails.class);
                i.putExtra("title_Id_Pass",prequelId);
                startActivity(i);

//                Url = "https://api.jikan.moe/v3/anime/" + prequelId;
//                urlp = "https://api.jikan.moe/v4/anime/" + prequelId + "/pictures";
//                Urlv = "https://api.jikan.moe/v3/anime/" + prequelId + "/videos";
//                Urlr = "https://api.jikan.moe/v4/anime/" + prequelId + "/recommendations";
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        animeOptionsLayout.setVisibility(View.GONE);
//                        episodeButton.setVisibility(View.VISIBLE);
//                        DetailsAnime detailsAnime = new DetailsAnime(Url);
//                        detailsAnime.start();
//
//
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                ImagesSlideShow(urlp);
//                            }
//                        }, 100);
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                youtube(Urlv);
//                            }
//                        }, 50);
//
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                Recommend(Urlr);
//                            }
//                        }, 100);
//                    }
//                }, 100);
            }

        });
////
        animeSequel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AnimeDeatails.class);
                i.putExtra("title_Id_Pass",sequelId);
                startActivity(i);
//                Url = "https://api.jikan.moe/v3/anime/" + sequelId;
//                urlp = "https://api.jikan.moe/v4/anime/" + sequelId + "/pictures";
//                Urlv = "https://api.jikan.moe/v3/anime/" + sequelId + "/videos";
//                Urlr = "https://api.jikan.moe/v4/anime/" + prequelId + "/recommendations";
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        animeOptionsLayout.setVisibility(View.GONE);
//                        episodeButton.setVisibility(View.VISIBLE);
//
//                        DetailsAnime detailsAnime = new DetailsAnime(Url);
//                        detailsAnime.start();
//
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                ImagesSlideShow(urlp);
//                            }
//                        }, 100);
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                youtube(Urlv);
//                            }
//                        }, 40);
//
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                Recommend(Urlr);
//                            }
//                        }, 100);
//                    }
//                }, 100);
            }

        });

        episodeButton.setButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SearchingEpisodes().execute();
            }
        });


    }

    private class SearchingEpisodes extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            episodeButton.onStartLoading();
//            animeDub.setBackgroundColor(Color.parseColor("#152D35"));
//            animeDub.setEnabled(true);

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Log.d(TAG,"AnimeName : "+Jname);
                Document document = Jsoup.connect("https://www1.gogoanime.pe//search.html?keyword=" + Jname).get();
//                Element Sub = document.select("a:contains(" +Jname+ ")").first();
//                Log.d(TAG, "doInBackground:SUB "+document);

//                Element Dub = document.select("a:contains(" +l+ ")").first();
//                Log.d(TAG, "doInBackground:SUB "+Dub.attr("href"));
//
                Elements mElementAnimeName = document.select("p[class=name]").select("a");
//                Elements mElementAnimeNames = document.getElementsByClass("name");
////                Log.i("detailLink0",document+"");
////                Log.i("detailLink0",mElementAnimeName+"");
                Log.i(TAG, ""+mElementAnimeName);
                for (Element element : mElementAnimeName) {
                    Log.i(TAG, "for loop started");

                    if (Jname.contains("Ψ")){
                        Jname = Jname.replace("Ψ","Ψ N");
                        Log.d(TAG, "doInBackground: Replaced"+Jname);
                    }

                    if(Jname.contains("."))
                        Jname = Jname.replace(".","");

                    if (Jname.equalsIgnoreCase(element.text())) {
                        links = "https://www1.gogoanime.pe" + element.attr("href");
                        Log.i(TAG,"Link Name"+ Jname);
                        Log.i(TAG,"Link"+ links);
                    }
                    String l = Jname + " (Dub)";
                    if (l.equalsIgnoreCase(element.text())) {
                        links1 = "https://www1.gogoanime.pe" + element.attr("href");
                        Log.i(TAG,"else: "+ l);
                        Log.i(TAG,"else: "+ links1);
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            episodeButton.onStopLoading();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    FancyToast.makeText(getApplicationContext(), "Links Found", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

                }
            });

            if (links1 == null){
                animeDub.setEnabled(false);
                animeDub.setBackgroundColor(Color.RED);
            }

            if (links == null){
                animeSub.setEnabled(false);
                animeSub.setBackgroundColor(Color.RED);
            }
            episodeButton.setVisibility(View.GONE);

            animeOptionsLayout.setVisibility(View.VISIBLE);
        }
    }

}