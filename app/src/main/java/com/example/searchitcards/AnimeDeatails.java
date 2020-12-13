package com.example.searchitcards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaCodec;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.searchitcards.Anime.Adapter.AdapterClass.Promo;
import com.example.searchitcards.Anime.Adapter.AdapterClass.RecommendedAnime;
import com.example.searchitcards.Anime.Adapter.YoutubeVideoAdapter;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnimeDeatails extends AppCompatActivity {

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
    Button episodeButton;
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
//    String addSide_story = "";
//    String addSummary = "";


    JSONObject cluster;

    LinearLayout prequelLaout;
    LinearLayout sequelLaout;
    LinearLayout sideStoryLaout;
    LinearLayout clusterLayout;
    LinearLayout trailerLayout;

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

    private String Urlr;
     String passId ="";
     String eNumb;
     Boolean reequestCashed;

//    YouTubePlayerView youTubePlayerView;
//    YouTubePlayer.OnInitializedListener onInitializedListener;
//    private YouTubePlayer youTubePlayer;


    public class DetailsAnime extends Thread {

        public DetailsAnime(String top) {


            prequelId = "";
            sequelId = "";
            addGenres = "";
            addProducer = "";
            addLisensor = "";
            addStudios = "";

          Log.i("hoole",passId);


            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                    try {
                        Picasso.get().load(response.getString("image_url")).into(animePoster);
//                        animeRank.setText("#"+response.getString("rank"));
                        if (!response.getString("title_english").equals("null")) {
                            animeTitleEng.setText(response.getString("title_english"));
                        } else {
                            animeTitleEng.setText(response.getString("title"));
                        }

                        JSONArray genres = response.getJSONArray("genres");
                        for (int i = 0; i < genres.length(); i++) {
                            JSONObject genresList = genres.getJSONObject(i);

                            addGenres += genresList.getString("name") + ", ";

                        }
                        animeGenres.setText(addGenres);
                        animeStatus.setText(response.getString("status"));
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
                            episodeButton.setVisibility(View.GONE);
                        }

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

                    try {


                    } catch (Exception e) {
                        e.printStackTrace();
                        prequelLaout.setVisibility(View.GONE);
                    }
                    try {


                    } catch (Exception e) {
                        e.printStackTrace();
                        sequelLaout.setVisibility(View.GONE);
                    }
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

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(request);
//
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
                    JSONArray pictures = response.getJSONArray("pictures");


                    for (int i = 0; i < pictures.length(); i++) {

                        if (i < 8) {
                            JSONObject pics = pictures.getJSONObject(i);
                            String s = "";
                            s = pics.getString("large");
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
                    Log.i("epis",epis);
                    if (epis.equals("[]")){
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
                    JSONArray jsonArray = response.getJSONArray("recommendations");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        RecommendedAnime anime = new RecommendedAnime();
                        anime.setMal_id(object.getString("mal_id"));
                        anime.setImage_url(object.getString("image_url"));
                        anime.setTitle(object.getString("title"));

                        Ranime.add(anime);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                topAnimeRecycle.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
//                topAnime = new TopAnime(MainActivity.this,animeResult);
//                topAnimeRecycle.setAdapter(topAnime);
                animeRecommendationRecycle.setLayoutManager(new LinearLayoutManager(AnimeDeatails.this, LinearLayoutManager.HORIZONTAL, false));
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

        modelList = new ArrayList<>();
        Yvideos = new ArrayList<>();
        Ranime = new ArrayList<>();
        storyList = new ArrayList<>();

        slider = findViewById(R.id.anime_pictures);

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
            urlp = "https://api.jikan.moe/v3/anime/" + searchId + "/pictures";
            Urlv = "https://api.jikan.moe/v3/anime/" + searchId + "/videos";
            Urlr = "https://api.jikan.moe/v3/anime/" + searchId + "/recommendations";
            passId = "https://api.jikan.moe/v3/anime/" + searchId;
        } else {
            Url = "https://api.jikan.moe/v3/anime/" + searchIdv;
            urlp = "https://api.jikan.moe/v3/anime/" + searchIdv + "/pictures";
            Urlv = "https://api.jikan.moe/v3/anime/" + searchIdv + "/videos";
            Urlr = "https://api.jikan.moe/v3/anime/" + searchIdv + "/recommendations";
            passId = "https://api.jikan.moe/v3/anime/" + searchIdv;
        }
        Handler handler = new Handler();

        DetailsAnime detailsAnime = new DetailsAnime(Url);
        detailsAnime.start();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ImagesSlideShow(urlp);
            }
        },100);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                youtube(Urlv);
            }
        },500);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Recommend(Urlr);
            }
        },300);







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


        animePrequel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Url = "https://api.jikan.moe/v3/anime/" + prequelId;
                urlp = "https://api.jikan.moe/v3/anime/" + prequelId + "/pictures";
                Urlv = "https://api.jikan.moe/v3/anime/" + prequelId + "/videos";
                Urlr = "https://api.jikan.moe/v3/anime/" + prequelId + "/recommendations";
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DetailsAnime detailsAnime = new DetailsAnime(Url);
                        detailsAnime.start();


                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ImagesSlideShow(urlp);
                            }
                        },100);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                youtube(Urlv);
                            }
                        },400);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Recommend(Urlr);
                            }
                        },800);
                    }
                }, 100);
            }

        });
////
        animeSequel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Url = "https://api.jikan.moe/v3/anime/" + sequelId;
                urlp = "https://api.jikan.moe/v3/anime/" + sequelId + "/pictures";
                Urlv = "https://api.jikan.moe/v3/anime/" + sequelId + "/videos";
                Urlr = "https://api.jikan.moe/v3/anime/" + prequelId + "/recommendations";
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DetailsAnime detailsAnime = new DetailsAnime(Url);
                        detailsAnime.start();


                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ImagesSlideShow(urlp);
                            }
                        },100);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                youtube(Urlv);
                            }
                        },400);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Recommend(Urlr);
                            }
                        },800);
                    }
                }, 100);
            }

        });

        episodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent op = new Intent(AnimeDeatails.this, EpisodeList.class);
                op.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                op.putExtra("episodeList_1", passId);
               // op.putExtra("episodes_number",eNumb);
                AnimeDeatails.this.startActivity(op);
            }
        });

    }


}