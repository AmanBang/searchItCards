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

    String pass ="" ;
    String Url;

    ImageView imageView;
String forImage;
    String itemUrl;

    Button prequelButton;
    Button sequelButton;
    String prequelId ="";
    String sequelId ="";
    JSONObject related;

    ImageView fullScreen;

    boolean firstVideoPlay = true;
    boolean flag = true;
    List<String> playlistUrl;


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

    Button animePrequel;
    Button animeSequel;
    Button animeSideStory;
    Button animeSummery;

    RecyclerView animeTrailerRecycle;

    String addGenres = "";
    String addProducer = "";
    String addStudios = "";
    String addLisensor = "";
    String addPrequel = "";
    String addSequel = "";
    String addSide_story = "";
    String addSummary = "";

    JSONObject cluster;

//    YouTubePlayerView youTubePlayerView;
//    YouTubePlayer.OnInitializedListener onInitializedListener;
//    private YouTubePlayer youTubePlayer;


    public class DetailsAnime extends Thread {

        public DetailsAnime(String top) {

            pass ="";
            prequelId ="";
            sequelId = "";



            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                    try {
                        Picasso.get().load(response.getString("image_url")).into(animePoster);
                        animeRank.setText(response.getString("rank"));
                        if(!response.getString("title_english").isEmpty()){
                            animeTitleEng.setText(response.getString("title_english"));
                        }else {
                            animeTitleEng.setText(response.getString("title"));
                        }

                        JSONArray genres = response.getJSONArray("genres");
                        for (int i = 0;i<genres.length();i++){
                            JSONObject genresList = genres.getJSONObject(i);

                            addGenres += genresList.getString("name")+", ";

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



                       JSONArray producer = response.getJSONArray("producers");
                       for (int i = 0;i<producer.length();i++){
                           JSONObject producerList = producer.getJSONObject(i);
                           addProducer = producerList.getString("name")+", ";
                       }
                       animeProducer.setText(addProducer);

                        JSONArray lisensor = response.getJSONArray("producers");
                        for (int i = 0;i<lisensor.length();i++){
                            JSONObject lisensorList = lisensor.getJSONObject(i);
                            addLisensor = lisensorList.getString("name")+", ";
                        }
                        animeProducer.setText(addLisensor);
                        JSONArray studios = response.getJSONArray("producers");
                        for (int i = 0;i<studios.length();i++){
                            JSONObject studiosList = studios.getJSONObject(i);
                            addStudios = studiosList.getString("name")+", ";
                        }
                        animeProducer.setText(addStudios);

                        //Json object for culster
                         cluster = response.getJSONObject("related");













//                        forImage = response.getString("image_url");
//
//
////                        Log.i("Titile",jsonArray.toString());
//                         itemUrl = response.getString("trailer_url");
//                        String sDeatil = "";
//                               sDeatil += "Status :"+response.getString("status") +"\n";
//                               sDeatil += "Episodes :"+response.getString("episodes") +"\n";
//                               sDeatil += "Duration :"+response.getString("duration") +"\n";
//                               sDeatil += "Rating :"+response.getString("rating") +"\n";
//                               sDeatil += "Score :"+response.getString("score") +"\n";
//
//                               shortDeatil.append(sDeatil);
//                           deatils.setText(response.getString("synopsis"));
//
//                         related = response.getJSONObject("related");
//
//
//
//                       // Log.i("related",pass);
//                        for (int i= 30;i<=40;i++){
//                            char c = itemUrl.charAt(i);
//                            pass = pass + c;
//                            playlistUrl.add(pass);
//
//                        }
//////
////                            if (itemUrl != null){
////                                Log.i("erro","showing info only");
////
////
////                            }else {
////                              youTubePlayerView.setVisibility(View.GONE);
////
////                            }
//
//
//                        youTubePlayerView.initialize("AIzaSyCXIXfoD0Vm50mL6f-Dsdax5-IhRGXIOds",onInitializedListener);
//
//                        if(!firstVideoPlay){
//
//                            youTubePlayer.next();
//                            Log.i("cue","cue Complete");
//
//                            }
//                        if (!related.getString("Prequel").isEmpty()){
//                            JSONArray prequel = related.getJSONArray("Prequel");
//                            for (int i=0;i<prequel.length();i++){
//                                JSONObject prequelJSONObject = prequel.getJSONObject(i);
//                                prequelId = prequelJSONObject.getString("mal_id");
//                                prequelButton.setText(prequelJSONObject.getString("name"));
//                                Log.i("related",prequelJSONObject.getString("name"));
//                            }
//                        }
//                        if(!related.getString("Sequel").isEmpty()){
//                            JSONArray sequel = related.getJSONArray("Sequel");
//                            for (int i=0;i<sequel.length();i++){
//                                JSONObject sequelJSONObject = sequel.getJSONObject(i);
//                                sequelId = sequelJSONObject.getString("mal_id");
//                                sequelButton.setText(sequelJSONObject.getString("name"));
//                                Log.i("related",sequelJSONObject.getString("name"));
//                            }
//                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {

                        JSONArray prequel = cluster.getJSONArray("Prequel");
                        for (int i = 0;i<prequel.length();i++){
                            JSONObject prequelList = prequel.getJSONObject(i);
                            addPrequel = prequelList.getString("name")+", ";
                        }
                        animePrequel.setText(addPrequel);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {

                        JSONArray sequel = cluster.getJSONArray("Sequel");
                        for (int i = 0;i<sequel.length();i++){
                            JSONObject sequelList = sequel.getJSONObject(i);
                            addSequel = sequelList.getString("name")+", ";
                        }
                        animeSequel.setText(addSequel
                        );
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        JSONArray side_story = cluster.getJSONArray("Side story");
                        for (int i = 0;i<side_story.length();i++){
                            JSONObject side_storylList = side_story.getJSONObject(i);
                            addSide_story = side_storylList.getString("name")+", ";
                        }
                        animeSideStory.setText(addSide_story);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {

                        JSONArray summary = cluster.getJSONArray("Summary");
                        for (int i = 0;i<summary.length();i++){
                            JSONObject summaryList = summary.getJSONObject(i);
                            addSummary = summaryList.getString("name")+", ";
                        }
                        animeSummery.setText(addSummary);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

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

    //  -----------------------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_anime_deatails);

        animePoster = findViewById(R.id.anime_poster);
        animeRank = findViewById(R.id.anime_rank);
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
        animeSideStory = findViewById(R.id.anime_sideStory);
        animeSummery = findViewById(R.id.anime_summery);

        animeTrailerRecycle = findViewById(R.id.anime_trailer_recycle);


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
     String   searchId = i.getStringExtra("title_Id_Pass");
     String   searchIdv = x.getStringExtra("passing_id");
   //  String   newRefresh = y.getStringExtra("refreshUrl");

//        textView.setText(searchId);
        if (searchIdv == null ){
             Url = "https://api.jikan.moe/v3/anime/" + searchId;
        }else {
            Url = "https://api.jikan.moe/v3/anime/" + searchIdv;
        }
        DetailsAnime detailsAnime = new DetailsAnime(Url);
        detailsAnime.start();


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





//        prequelButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        if(!prequelId.isEmpty()){
//                            String  Urlp = "https://api.jikan.moe/v3/anime/" + prequelId;
//                            final Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//
//
//                                    firstVideoPlay = false;
//                                    DetailsAnime detailsAnime = new DetailsAnime(Urlp);
//                                    detailsAnime.start();
//
//                                }
//                            }, 500);
//                        }
//                   // }
//                });
////
//                sequelButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        if (!sequelId.isEmpty()){
//                            firstVideoPlay =false;
//                            String  Urls = "https://api.jikan.moe/v3/anime/" + sequelId;
//                            final Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    firstVideoPlay = false;
//
//
//                                    DetailsAnime detailsAnime = new DetailsAnime(Urls);
//                                    detailsAnime.start();
//
//                                }
//                            }, 500);
//                        }
//                    }
//                });
////
//
//
//        fullScreen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                Toast.makeText(AnimeDeatails.this,"Press Back Button to exit full screen",Toast.LENGTH_LONG).show();
//            }
//        });

    }


    }