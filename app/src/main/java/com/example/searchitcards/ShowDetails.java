package com.example.searchitcards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.searchitcards.Anime.Adapter.AdapterClass.Promo;
import com.example.searchitcards.Anime.Adapter.AdapterClass.RecommendedAnime;
import com.example.searchitcards.Movie.mAdapter.DeatailsMovie;
import com.example.searchitcards.Movie.mAdapter.Trailer;
import com.example.searchitcards.Movie.mAdapter.TrailerAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowDetails extends AppCompatActivity {

    ImageView showPoster;
    TextView showTitleEng;
    TextView showGenres;
    TextView showStatus;
    TextView showDescription;
    TextView showTitle;
    TextView showDuration;
    TextView showPremier;
    TextView showBroadcast;
    TextView showScore;
    TextView showPopularity;
    TextView showProducer;
    TextView showEpisodes;
    TextView showAired;
    TextView showType;


    RecyclerView showTrailerRecycle;
    RecyclerView showRecommendationRecycle;
    RecyclerView trailerRecycle;

    ShowRAdapter showRAdapter;


    List<SlideModel> modelList;
    List<Promo> Yvideos;
    List<RecommendedMovies> RMList;
    List<Trailer> trailerList;
    TrailerAdapter trailerAdapter;


    ImageSlider slider;

    String addGenres = "";
    String addProducer = "";
    String seasonNumber = "";
    String passMe;
    
    //-----------------------------------------------------------------------------------------------------------------------------------------


    public class DetailsShow extends Thread {

        public DetailsShow(String top) {



            addGenres = "";
            addProducer = "";


            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                    try {
                        Picasso.get().load("https://image.tmdb.org/t/p/w500"+response.getString("poster_path")).into(showPoster);
//                        showRank.setText("#"+response.getString("rank"));

                            showTitleEng.setText(response.getString("name"));

                        JSONArray genres = response.getJSONArray("genres");
                        for (int i = 0; i < genres.length(); i++) {
                            JSONObject genresList = genres.getJSONObject(i);

                            addGenres += genresList.getString("name") + ", ";

                        }
                        showGenres.setText(addGenres);

                        showStatus.setText(response.getString("status"));
                        showDescription.setText(response.getString("overview"));
                        showTitle.setText(response.getString("original_name"));
                        showDuration.setText(response.getString("episode_run_time")+" Min");//this
                        showPremier.setText(response.getString("first_air_date"));//first episode to air
                        showBroadcast.setText(response.getString("last_air_date"));//last episode to air
                        showScore.setText(response.getString("vote_average"));
                        showPopularity.setText(response.getString("popularity"));
                        showEpisodes.setText(response.getString("number_of_episodes"));
                        //eNumb = response.getString("episodes");
                        showType.setText(response.getString("type"));
                        seasonNumber = response.getString("number_of_seasons");
                        passMe = response.getString("id");

                        JSONObject aired = response.getJSONObject("aired");
                        showAired.setText(aired.getString("string"));

                        JSONArray producer = response.getJSONArray("production_companies");
                        for (int i = 0; i < producer.length(); i++) {
                            JSONObject producerList = producer.getJSONObject(i);
                            addProducer += producerList.getString("name") + ", ";
                        }
                        showProducer.setText(addProducer);


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
//
        }

    }


    public void ImagesSlideShow(String pic) {
        modelList.clear();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, pic, null, new Response.Listener<JSONObject>() {
            private com.denzcoskun.imageslider.models.SlideModel SlideModel;

            @Override
            public void onResponse(JSONObject response) {
                Log.i("resasda", response.toString());
                try {
                    JSONArray pictures = response.getJSONArray("backdrops");


                    for (int i = 0; i < pictures.length(); i++) {

                        if (i < 10) {
                            JSONObject pics = pictures.getJSONObject(i);
                            String s = "";
                            s ="https://image.tmdb.org/t/p/w500" +pics.getString("file_path");
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


    public void trailerFetch(String src){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, src, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray mJ = response.getJSONArray("results");
                    for (int g= 0;g<mJ.length();g++){
                        JSONObject object = mJ.getJSONObject(g);
                        Trailer trailer = new Trailer();
                        Log.i("trailer",object.getString("key"));
                        trailer.setKey(object.getString("key"));
                        trailer.setName(object.getString("name"));
                        trailer.setType(object.getString("type"));
                        trailerList.add(trailer);
                    }


                    //Log.i("title", string);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                showTrailerRecycle.setLayoutManager(new LinearLayoutManager(ShowDetails.this,LinearLayoutManager.HORIZONTAL,false));
                trailerAdapter = new TrailerAdapter(ShowDetails.this,trailerList);
                showTrailerRecycle.setAdapter(trailerAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);

    }


    public void Recommend(String Urly) {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, Urly, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        RecommendedMovies anime = new RecommendedMovies();
                        anime.setId(object.getString("id"));
                        anime.setPoster_path(object.getString("poster_path"));
                       anime.setTitle(object.getString("name"));

                        RMList.add(anime);

                        Log.i("hjlj",RMList.toString());

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                showRecommendationRecycle.setLayoutManager(new LinearLayoutManager(ShowDetails.this, LinearLayoutManager.HORIZONTAL, false));
                showRAdapter = new ShowRAdapter(ShowDetails.this, RMList);
                showRecommendationRecycle.setAdapter(showRAdapter);
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
        setContentView(R.layout.activity_show_details);


        showPoster = findViewById(R.id.show_poster);
        showTitleEng = findViewById(R.id.show_title_eng);
        showGenres = findViewById(R.id.show_genres);
        showStatus = findViewById(R.id.show_status);
        showDescription = findViewById(R.id.show_description);
        showTitle = findViewById(R.id.show_title);
        showDuration = findViewById(R.id.show_duration);
        showPremier = findViewById(R.id.show_premier);
        showBroadcast = findViewById(R.id.show_broadcast);
        showScore = findViewById(R.id.show_score);
        showPopularity = findViewById(R.id.show_popularity);
        showProducer = findViewById(R.id.show_producers);

        showEpisodes = findViewById(R.id.show_episodes);
        showType = findViewById(R.id.show_type);

        showTrailerRecycle = findViewById(R.id.show_trailer_recycle);
        showTrailerRecycle.setHasFixedSize(true);
        showRecommendationRecycle = findViewById(R.id.show_recommendation_recycle);
        showRecommendationRecycle.setHasFixedSize(true);


        modelList = new ArrayList<>();
        Yvideos = new ArrayList<>();
        RMList = new ArrayList<>();
        trailerList = new ArrayList<>();

        slider = findViewById(R.id.show_pictures);
       // trailerRecycle = findViewById(R.id.show_trailer_recycle);

        Intent i = getIntent();
        String id = i.getStringExtra("show_id");
        String passId = i.getStringExtra("pass_id");

        if (id == null){
            String url ="https://api.themoviedb.org/3/tv/"+passId+"?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US";
            DetailsShow show = new DetailsShow(url);
            show.start();
            ImagesSlideShow("https://api.themoviedb.org/3/tv/"+passId+"/images?api_key=e707c6ad620e69cda284fbbc6af06e43");
            trailerFetch("https://api.themoviedb.org/3/tv/"+passId+"/videos?api_key=e707c6ad620e69cda284fbbc6af06e43");
            Recommend("https://api.themoviedb.org/3/tv/"+passId+"/similar?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US&page=1");

        }else {
            String url ="https://api.themoviedb.org/3/tv/"+id+"?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US";
            DetailsShow show = new DetailsShow(url);
            show.start();
            ImagesSlideShow("https://api.themoviedb.org/3/tv/"+id+"/images?api_key=e707c6ad620e69cda284fbbc6af06e43");
            trailerFetch("https://api.themoviedb.org/3/tv/"+id+"/videos?api_key=e707c6ad620e69cda284fbbc6af06e43");
            Recommend("https://api.themoviedb.org/3/tv/"+id+"/similar?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US&page=1");

        }


        Button episodeButton = findViewById(R.id.show_episodes_button);
        episodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent op = new Intent(ShowDetails.this, EpisodeList.class);
                op.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                op.putExtra("episodeList_2", passMe);
                op.putExtra("season_Numbers", seasonNumber);
                // op.putExtra("episodes_number",eNumb);
                ShowDetails.this.startActivity(op);
            }
        });

    }
}