package com.animasium.searchitcards.Movie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import com.animasium.searchitcards.WebView;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
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
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class DeatailsMovie extends AppCompatActivity {


    ImageView moviePoster;
    String imdb_id;
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

    Button LWatch;
    Button HWatch;

    //    List<> trailerList;
    private AdView mAdView;

    String addGenres;
    String addProducer;
    String id ="";
    String tmdb_id;
    String id2 ="";
    Uri shortLink;



    String MovieImageURl;

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
    String posterPath = "";
    String name = "";

    AdapterRMovies RMadpter;


    public class MoviesDetails extends Thread {
        public MoviesDetails(String src) {

            addGenres = "";
            addProducer = "";

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, src, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.i("responcex", response.toString());
                    try {
                        posterPath = response.getString("poster_path");
                        MovieImageURl = "https://image.tmdb.org/t/p/w500" + posterPath;
                        Picasso.get().load(MovieImageURl).into(moviePoster);
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
                        imdb_id = response.getString("imdb_id");
                        tmdb_id = response.getString("id");

                        JSONArray producer = response.getJSONArray("production_companies");
                        for (int i = 0; i < producer.length(); i++) {
                            JSONObject producerList = producer.getJSONObject(i);
                            addProducer += producerList.getString("name") + ", ";
                        }
                        movieProducer.setText(addProducer);


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

    public void trailerFetch(String src) {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, src, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray mJ = response.getJSONArray("results");
                    for (int g = 0; g < mJ.length(); g++) {
                        JSONObject object = mJ.getJSONObject(g);
                        Trailer trailer = new Trailer();
                        Log.i("trailer", object.getString("key"));
                        trailer.setKey(object.getString("key"));
                        trailer.setName(object.getString("name"));
                        trailer.setType(object.getString("type"));
                        trailerList.add(trailer);
                    }


                    //Log.i("title", string);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                trailerRecycle.setLayoutManager(new LinearLayoutManager(DeatailsMovie.this, LinearLayoutManager.HORIZONTAL, false));
                trailerAdapter = new TrailerAdapter(DeatailsMovie.this, trailerList);
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
                            s = "https://image.tmdb.org/t/p/w500" + pics.getString("file_path");
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

                        Log.i("hjlj", object.getString("id"));

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

//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//
//        mAdView = findViewById(R.id.adMoiveDetails);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);


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
        LWatch = findViewById(R.id.movie_watch_online);
        HWatch = findViewById(R.id.movie_watch_online2);
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
        ReciveDynamicLinks();

        try {

            Intent intent = getIntent();
            Intent jio = getIntent();

            id = intent.getStringExtra("pass_id");
            id2 = jio.getStringExtra("searchToDetails");

        }catch (Exception e){
            e.printStackTrace();
        }


        if (id == null) {
            showZid = id2;
            String videoUrl = "https://api.themoviedb.org/3/movie/" + id2 + "/videos?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US";
            String url = "https://api.themoviedb.org/3/movie/" + id2 + "?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US";
            MoviesDetails moviesDetails = new MoviesDetails(url);
            moviesDetails.start();
            trailerFetch(videoUrl);
            ImagesSlideShow("https://api.themoviedb.org/3/movie/" + id2 + "/images?api_key=e707c6ad620e69cda284fbbc6af06e43");
            for (int p = 1; p < 5; p++) {
                Recommend("https://api.themoviedb.org/3/movie/" + id2 + "/similar?api_key=e707c6ad620e69cda284fbbc6af06e43&page=" + p);
            }

        } else {
            showZid = id;
            String videoUrl = "https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US";
            String url = "https://api.themoviedb.org/3/movie/" + id + "?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US";
            MoviesDetails moviesDetails = new MoviesDetails(url);
            moviesDetails.start();
            trailerFetch(videoUrl);
            ImagesSlideShow("https://api.themoviedb.org/3/movie/" + id + "/images?api_key=e707c6ad620e69cda284fbbc6af06e43");
            for (int p = 1; p < 5; p++) {
                Recommend("https://api.themoviedb.org/3/movie/" + id + "/similar?api_key=e707c6ad620e69cda284fbbc6af06e43&page=" + p);
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
                                tvShowFav.put("posterPath", posterPath);
                                tvShowFav.put("showName", name);
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
                        } catch (Exception e1) {
                            Log.i("errrrr", e.getMessage());
                        }

                        dialog.dismiss();
                    }
                });

            }

        });

        watchMovie();

//        new Thread( new Runnable() { @Override public void run() {
//            // Run whatever background code you want here.
//
//        } } ).start();

    }

    private void ReciveDynamicLinks() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                        }
                        if (deepLink != null){
                            id2 = deepLink.getQueryParameter("passID");
                            Log.i("dynamic",id2);
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "getDynamicLink:onFailure", e);
                    }
                });

    }

    private void createDynamicLinks() {

        Toast.makeText(this, "Link Copied", Toast.LENGTH_SHORT).show();

        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://www.example.com?passID="+tmdb_id))
                .setDomainUriPrefix("https://animasium.page.link")
                // Open links with this app on Android
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                // Open links with com.example.ios on iOS
//                .setIosParameters(new DynamicLink.IosParameters.Builder("com.example.ios").build())
                .buildDynamicLink();

        Uri dynamicLinkUri = dynamicLink.getUri();
        Log.i("DynamicLInk","Short :"+dynamicLinkUri);

//        Task<ShortDynamicLink> dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
//                .setLink(Uri.parse("https://www.example.com?passID="+tmdb_id))
//                .setDomainUriPrefix("https://searchitcards.page.link")
////                .setAndroidParameters(
////                        new DynamicLink.AndroidParameters.Builder("com.animasium.searchitcards.Movie.DeatailsMovie")
////                                .setMinimumVersion(125)
////                                .build())
////                // Open links with this app on Android
////                .setSocialMetaTagParameters(
////                        new DynamicLink.SocialMetaTagParameters.Builder()
////                                .setTitle(name)
////                                .setDescription("Hey! Check out this Interesting Movie")
////                                .setImageUrl(Uri.parse(MovieImageURl))
////                                .build())
//                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
//                // Open links with com.example.ios on iOS
//                .setIosParameters(new DynamicLink.IosParameters.Builder("com.example.ios").build())
//                .buildShortDynamicLink()
//                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
//                    @Override
//                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
//                        if (task.isSuccessful()) {
//                            // Short link created
//                            shortLink = Objects.requireNonNull(task.getResult()).getShortLink();
//                            Uri flowchartLink = task.getResult().getPreviewLink();
//
//                            Log.i("DynamicLInk","Short :"+shortLink);
//                            Log.i("DynamicLInk","flowchartLink :"+flowchartLink);
//                        } else {
//                            Toast.makeText(DeatailsMovie.this, "Something ERR :\n", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
////        Uri dynamicLinkUri = Objects.requireNonNull(dynamicLink.getResult()).getShortLink();
////        Log.i("DynamicLInk","dynamicLinkUri :"+dynamicLinkUri);




    }

    private void watchMovie() {

        LWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeatailsMovie.this, WebView.class);
                i.putExtra("watchID", "https://database.gdriveplayer.io/player.php?tmdb=" + tmdb_id);
                startActivity(i);
            }
        });
        HWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeatailsMovie.this, WebView.class);
//                i.putExtra("watchID","https://gomo.to/movie/"+imdb_id);
//                i.putExtra("watchID","https://api.123movie.cc/jadeed.php?imdb="+imdb_id+"&server_name=vcu");
                i.putExtra("watchID", "https://www.2embed.ru/embed/tmdb/movie?id=" + tmdb_id);


                startActivity(i);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.fav_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.fav_add_barbtn) {
            Toast.makeText(this, "fav pressed", Toast.LENGTH_SHORT).show();
            dialog.show();
        }else if (item.getItemId() == R.id.share_btn){

            createDynamicLinks();
            Toast.makeText(this, "Work in Progress :)", Toast.LENGTH_SHORT).show();
//            new Timer().schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    // this code will be executed after 2 seconds
//                    shareIntent();
//                }
//            }, 2000);

        }
        return super.onOptionsItemSelected(item);
    }

    private void shareIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shortLink);
        startActivity(intent);
    }
}