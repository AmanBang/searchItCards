package com.animasium.searchitcards.Movie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.animasium.searchitcards.Movie.mAdapter.AdapterRMovies;

import com.animasium.searchitcards.Movie.mAdapter.TrailerAdapter;
import com.animasium.searchitcards.Movie.mAdapterclasses.Trailer;
import com.animasium.searchitcards.R;
import com.animasium.searchitcards.RecommendedMovies;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DeatailsMovie extends AppCompatActivity {


    ImageView moviePoster;
    TextView movieRank;
    TextView movieTitleEng;
    TextView movieGenres;
    TextView movieStatus;
    TextView movieDescription;
    TextView movieTitle;
    TextView movieDuration;
    TextView movieBudget;
    TextView movieRevenue;
    TextView movieScore;
    TextView moviePopularity;
    TextView movieProducer;
    TextView movieAired;
    TextView movieType;

    RecyclerView trailerRecycle;
    RecyclerView RMRecycle;
    TrailerAdapter trailerAdapter;
    List<Trailer> trailerList;
//    List<> trailerList;
private AdView mAdView;

    String addGenres;
    String addProducer;
    String id;

    ImageSlider slider;
    List<SlideModel> modelList;
    List<RecommendedMovies> RMList;

    private String showZid;

    Dialog dialog;
    RadioGroup radioGroup;
    RadioButton ongoingbtn;
    Button cancle;
    Button Add;
    String switchText = "Pending";
    String posterPath ="";
    String name = "";

    AdapterRMovies RMadpter;



    public class MoviesDetails extends Thread{
        public MoviesDetails(String src) {

            addGenres = "";
            addProducer = "";

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, src, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

Log.i("responcex",response.toString());
                    try {
                        posterPath = response.getString("poster_path");
                        Picasso.get().load("https://image.tmdb.org/t/p/w500"+posterPath).into(moviePoster);
//                        animeRank.setText("#"+response.getString("rank"));
//                        if (!response.getString("title_english").equals("null")) {
                        name = response.getString("title");
                            movieTitleEng.setText(name);
//                        } else {
//                            movieTitle.setText(response.getString("original_title"));
//                        }

                        JSONArray genres = response.getJSONArray("genres");
                        for (int i = 0; i < genres.length(); i++) {
                            JSONObject genresList = genres.getJSONObject(i);

                            addGenres += genresList.getString("name") + ", ";

                        }
                        movieGenres.setText(addGenres);
                        movieStatus.setText(response.getString("status"));
                        movieDescription.setText(response.getString("overview"));
                        movieTitle.setText(response.getString("original_title"));
                  // movieDirector.setText(response.getString("director"));
                        movieDuration.setText(response.getString("runtime"));
                        movieBudget.setText(response.getString("budget"));
                        movieRevenue.setText(response.getString("revenue"));
                        movieScore.setText(response.getString("vote_average"));
                        moviePopularity.setText(response.getString("popularity"));
                        movieAired.setText(response.getString("release_date"));

                       // animeEpisodes.setText(response.getString("episodes"));
                      //  eNumb = response.getString("episodes");
                     //   animeType.setText(response.getString("type"));

//                        if (response.getString("type").equals("Movie")) {
//                            clusterLayout.setVisibility(View.GONE);
//                            episodeButton.setVisibility(View.GONE);
//                        }
//
//                        JSONObject aired = response.getJSONObject("aired");
//                        animeAired.setText(aired.getString("string"));
//
                        JSONArray producer = response.getJSONArray("production_companies");
                        for (int i = 0; i < producer.length(); i++) {
                            JSONObject producerList = producer.getJSONObject(i);
                            addProducer += producerList.getString("name") + ", ";
                        }
                        movieProducer.setText(addProducer);
//
//                        JSONArray lisensor = response.getJSONArray("licensors");
//                        for (int i = 0; i < lisensor.length(); i++) {
//                            JSONObject lisensorList = lisensor.getJSONObject(i);
//                            addLisensor += lisensorList.getString("name") + ", ";
//                        }
//                        animeLisensor.setText(addLisensor);
//                        JSONArray studios = response.getJSONArray("studios");
//                        for (int i = 0; i < studios.length(); i++) {
//                            JSONObject studiosList = studios.getJSONObject(i);
//                            addStudios += studiosList.getString("name") + ", ";
//                        }
//                        animeStudios.setText(addStudios);
//
//                        //Json object for culster
//                        cluster = response.getJSONObject("related");
//
//                        JSONArray prequel = cluster.getJSONArray("Prequel");
//                        for (int i = 0; i < prequel.length(); i++) {
//                            JSONObject prequelList = prequel.getJSONObject(i);
//                            addPrequel = prequelList.getString("name");
//                            prequelId = prequelList.getString("mal_id");
//                        }
//                        animePrequel.setText(addPrequel);
//
//                        JSONArray sequel = cluster.getJSONArray("Sequel");
//                        for (int i = 0; i < sequel.length(); i++) {
//                            JSONObject sequelList = sequel.getJSONObject(i);
//                            addSequel = sequelList.getString("name");
//                            sequelId = sequelList.getString("mal_id");
//                        }
//                        animeSequel.setText(addSequel);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    try {
//
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        prequelLaout.setVisibility(View.GONE);
//                    }
//                    try {
//
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        sequelLaout.setVisibility(View.GONE);
//                    }
//                    try {
//                        JSONArray side_story = cluster.getJSONArray("Side story");
//                        for (int i = 0; i < side_story.length(); i++) {
//                            JSONObject side_storylList = side_story.getJSONObject(i);
////                            addSide_story = side_storylList.getString("name") + ", ";
//
//                            SStory sStory = new SStory();
//
//                            sStory.setMal_id(side_storylList.getString("mal_id"));
//                            sStory.setName(side_storylList.getString("name"));
//
//                            storyList.add(sStory);
//
//                        }
////                        animeSideStory.setText(addSide_story);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        sideStoryLaout.setVisibility(View.GONE);
//                    }

//                    SSRecycle.setLayoutManager(new LinearLayoutManager(AnimeDeatails.this, LinearLayoutManager.HORIZONTAL, false));
//                    SAdapter = new SideStoryAdapter(AnimeDeatails.this, storyList);
//                    SSRecycle.setAdapter(SAdapter);
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

                trailerRecycle.setLayoutManager(new LinearLayoutManager(DeatailsMovie.this,LinearLayoutManager.HORIZONTAL,false));
                trailerAdapter = new TrailerAdapter(DeatailsMovie.this,trailerList);
                trailerRecycle.setAdapter(trailerAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);

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
                        anime.setTitle(object.getString("title"));

                        RMList.add(anime);

                        Log.i("hjlj",RMList.toString());

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                topAnimeRecycle.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
//                topAnime = new TopAnime(MainActivity.this,animeResult);
//                topAnimeRecycle.setAdapter(topAnime);
                RMRecycle.setLayoutManager(new LinearLayoutManager(DeatailsMovie.this, LinearLayoutManager.HORIZONTAL, false));
                RMadpter = new AdapterRMovies(DeatailsMovie.this, RMList);
                RMRecycle.setAdapter(RMadpter);
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
        setContentView(R.layout.activity_deatails_movie);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adMoiveDetails);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        moviePoster = findViewById(R.id.moive_poster);
//        movieRank = findViewById(R.id.movie_rank);
        movieTitleEng = findViewById(R.id.moive_title_eng);
        movieGenres = findViewById(R.id.moive_genres);
        movieStatus = findViewById(R.id.moive_status);
        movieDescription = findViewById(R.id.moive_description);
        movieTitle = findViewById(R.id.moive_title);
        //movieDirector = findViewById(R.id.moive_director);
        movieDuration = findViewById(R.id.movie_duration);
        movieBudget = findViewById(R.id.movie_budget);
        movieRevenue = findViewById(R.id.movie_revenue);
        movieScore = findViewById(R.id.movie_score);
        moviePopularity = findViewById(R.id.movie_popularity);
        movieProducer = findViewById(R.id.movie_producers);
//        movieLisensor = findViewById(R.id.movie_licensors);
//        movieStudios = findViewById(R.id.movie_studios);
//        moviePrequel = findViewById(R.id.movie_prequel);
//        movieSequel = findViewById(R.id.movie_sequel);
//        movieSideStory = findViewById(R.id.movie_sideStory);
//        movieSummery = findViewById(R.id.movie_summery);
//        movieEpisodes = findViewById(R.id.movie_episodes);
        movieAired = findViewById(R.id.movie_aired);
//        movieType = findViewById(R.id.movie_type);
       // episodeButton = findViewById(R.id.movie_episodes_button);
        slider = findViewById(R.id.moive_pictures);

        modelList = new ArrayList<>();
        RMList = new ArrayList<>();
        trailerList = new ArrayList<>();
        trailerRecycle = findViewById(R.id.movie_trailer_recycle);
        RMRecycle = findViewById(R.id.movie_similar_recycle);




        Intent intent = getIntent();
        Intent jio = getIntent();

             id = intent.getStringExtra("pass_id");
            String id2  = jio.getStringExtra("searchToDetails");

if(id == null){
    showZid = id2;
    String videoUrl = "https://api.themoviedb.org/3/movie/"+id2+"/videos?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US";
    String url = "https://api.themoviedb.org/3/movie/"+id2+"?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US";
    MoviesDetails moviesDetails = new MoviesDetails(url);
    moviesDetails.start();
    trailerFetch(videoUrl);
    ImagesSlideShow("https://api.themoviedb.org/3/movie/"+id2+"/images?api_key=e707c6ad620e69cda284fbbc6af06e43");
    for (int p = 1;p<5;p++){
        Recommend("https://api.themoviedb.org/3/movie/"+id2+"/similar?api_key=e707c6ad620e69cda284fbbc6af06e43&page="+p);
    }

}else {
    showZid = id;
    String videoUrl = "https://api.themoviedb.org/3/movie/"+id+"/videos?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US";
    String url = "https://api.themoviedb.org/3/movie/"+id+"?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US";
    MoviesDetails moviesDetails = new MoviesDetails(url);
    moviesDetails.start();
    trailerFetch(videoUrl);
    ImagesSlideShow("https://api.themoviedb.org/3/movie/"+id+"/images?api_key=e707c6ad620e69cda284fbbc6af06e43");
    for (int p = 1;p<5;p++){
        Recommend("https://api.themoviedb.org/3/movie/"+id+"/similar?api_key=e707c6ad620e69cda284fbbc6af06e43&page="+p);
    }
}
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_favourites_dialogbox);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = this.getResources().getDrawable(R.drawable.background);
            dialog.getWindow().setBackgroundDrawable(drawable);
        }

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        radioGroup = dialog.findViewById(R.id.radioGroup);
        ongoingbtn = dialog.findViewById(R.id.RB_ongoing);



        cancle = dialog.findViewById(R.id.dialog_cancelBtn);
        Add = dialog.findViewById(R.id.dialog_addBtn);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.RB_pending:
                        switchText = "";
                        switchText = "Pending";
                        break;
                    case R.id.RB_ongoing:
                        switchText = "";
                        switchText = "Ongoing";
                        break;
                    case R.id.RB_watched:
                        switchText = "";
                        switchText = "Watched";
                        break;
                }
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ParseQuery<ParseUser> parseQuery = new ParseQuery<ParseUser>("Movies");

                parseQuery.whereMatches("showID", showZid);
                parseQuery.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> objects, ParseException e) {
//                        Log.i("objestparesd", objects + "");
//                        Log.i("objestparesd:", switchText);
                        try {
                            if (objects.isEmpty()) {
                                ParseObject tvShowFav = new ParseObject("Movies");
                                tvShowFav.put("showID", showZid);
                                tvShowFav.put("type", switchText);
                                tvShowFav.put("posterPath",posterPath);
                                tvShowFav.put("showName",name);
                                // tvShowFav.put("posterPath",posterPath);
                                //tvShowFav.put("");
                                tvShowFav.setACL(new ParseACL(ParseUser.getCurrentUser()));
                                tvShowFav.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            FancyToast.makeText(v.getContext(), "ADDED to Favourites", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();

                                        } else {
                                            FancyToast.makeText(v.getContext(), "Could Not Add to Favourites:" + e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
                                        }
                                    }
                                });
                            } else {
                                FancyToast.makeText(v.getContext(), "Already add in your favourite", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();

                            }
                        }catch (Exception e1){
                            Log.i("errrrr",e.getMessage());
                        }

                        dialog.dismiss();
                    }
                });

            }

        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.fav_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.fav_add_barbtn){
            Toast.makeText(this,"fav pressed",Toast.LENGTH_SHORT).show();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}