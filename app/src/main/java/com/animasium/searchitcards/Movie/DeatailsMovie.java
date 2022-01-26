

package com.animasium.searchitcards.Movie;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.animasium.searchitcards.Scraper.BeatScraper;
import com.animasium.searchitcards.Scraper.HScraper;
import com.animasium.searchitcards.WebView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.animasium.searchitcards.Movie.mAdapter.AdapterRMovies;

import com.animasium.searchitcards.Movie.mAdapter.TrailerAdapter;
import com.animasium.searchitcards.Movie.mAdapterclasses.Trailer;
import com.animasium.searchitcards.R;
import com.animasium.searchitcards.RecommendedMovies;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

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
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.IUnityBannerListener;
import com.unity3d.services.banners.UnityBanners;
import com.unity3d.services.banners.view.BannerPosition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;


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
    RecyclerView RMRecycle2;
    TrailerAdapter trailerAdapter;
    List<Trailer> trailerList;

    Button LWatch;
    Button HWatch;

    //    List<> trailerList;
    private AdView mAdView;

    String addGenres;
    String addProducer;
    String id = "";
    String tmdb_id;
    String id2 = "";
    Uri shortLink;
    String gogoAnimeUrl = null;


    String MovieImageURl;

    ImageSlider slider;
    List<SlideModel> modelList;
    List<RecommendedMovies> RMList;
    List<RecommendedMovies> RMList2;
    List<String> linkList;

    LinearLayout ServerLayout;
    LinearLayout layout;
    private String showZid;

    Dialog dialog;
    RadioGroup radioGroup;
    RadioButton ongoingbtn;
    Button cancle;
    Button Add;
    String switchText = "Pending";
    String posterPath = "";
    String name = "";
    String des = "";

    AdapterRMovies RMadpter;
    ProgressBar progressBar;
    Button m_watch;
    Button h_watch;
    private String watchonlineLink;
    private ArrayList<String> HindiLink = null;
    private Button D_button;
    private Button getMovieServer;


    //ads section

    private String unityGameId = "4157281";
    private boolean testMode = false;
    private String placementId = "Banner_Android";
    private View bannerView;
    private String interPlacement = "Interstitial_Android";
    private String rewardedPlacement = "Rewarded_Android";

    public class MoviesDetails extends Thread {
        public MoviesDetails(String src, String src2) {

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
                        des = response.getString("overview");
                        movieDescription.setText(des);
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
//                        new GetLink(, DeatailsMovie.this).execute();
                        layout.setVisibility(View.VISIBLE);
//                        new GetLink(name,DeatailsMovie.this).execute();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, src2, null, new Response.Listener<JSONObject>() {
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
                    progressBar.setVisibility(View.GONE);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            queue.add(request);
            queue.add(request2);


        }
    }

//    public void trailerFetch(String src) {
//
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        queue.add(request);
//
//    }

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


    public void Recommend(String Urly, String Urly2) {

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

        JsonObjectRequest objectRequest2 = new JsonObjectRequest(Request.Method.GET, Urly2, null, new Response.Listener<JSONObject>() {
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

                        RMList2.add(anime);

                        Log.i("hjlj", object.getString("id"));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                topAnimeRecycle.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
//                topAnime = new TopAnime(MainActivity.this,animeResult);
//                topAnimeRecycle.setAdapter(topAnime);
                RMRecycle2.setLayoutManager(new LinearLayoutManager(DeatailsMovie.this, LinearLayoutManager.HORIZONTAL, false));
                RMadpter = new AdapterRMovies(DeatailsMovie.this, RMList2);
                RMRecycle2.setAdapter(RMadpter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(objectRequest);
        requestQueue.add(objectRequest2);
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

        // Declare a new banner listener, and set it as the active banner listener:
//        final Activity myActivity = this;
//        final IUnityBannerListener myBannerListener = new UnityBannerListener ();
//        UnityBanners.setBannerListener (myBannerListener);
//        // Initialize the Ads SDK:
//        UnityAds.initialize (this, unityGameId, testMode);
//
//        ToggleBannerAd();

        UnityAds.initialize(this, unityGameId, testMode);
//        IUnityBannerListener bannerListener = new IUnityBannerListener() {
//            @Override
//            public void onUnityBannerLoaded(String s, View view) {
//                ((ViewGroup) findViewById(R.id.banner_ad)).removeView(view);
//                ((ViewGroup) findViewById(R.id.banner_ad)).addView(view);
//            }
//
//            @Override
//            public void onUnityBannerUnloaded(String s) {
//
//            }
//
//            @Override
//            public void onUnityBannerShow(String s) {
//
//            }
//
//            @Override
//            public void onUnityBannerClick(String s) {
//
//            }
//
//            @Override
//            public void onUnityBannerHide(String s) {
//
//            }
//
//            @Override
//            public void onUnityBannerError(String s) {
//
//            }
//        };
//        UnityBanners.setBannerListener(bannerListener);
//        UnityBanners.loadBanner(DeatailsMovie.this, placementId);

//        IUnityAdsListener interListner = new IUnityAdsListener() {
//            @Override
//            public void onUnityAdsReady(String s) {
//
//            }
//
//            @Override
//            public void onUnityAdsStart(String s) {
//
//            }
//
//            @Override
//            public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {
//
//            }
//
//            @Override
//            public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {
//
//            }
//        };
//        UnityAds.setListener(interListner);
//        UnityAds.load(interPlacement);

        IUnityAdsListener rewardedListner = new IUnityAdsListener() {
            @Override
            public void onUnityAdsReady(String s) {

            }

            @Override
            public void onUnityAdsStart(String s) {

            }

            @Override
            public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {
                // Implement conditional logic for each ad completion status:
                if (finishState.equals(UnityAds.FinishState.COMPLETED)) {
                    // Reward the user for watching the ad to completion.
                    Toast.makeText(DeatailsMovie.this, "Completed", Toast.LENGTH_SHORT).show();

                } else if (finishState == UnityAds.FinishState.SKIPPED) {
                    // Do not reward the user for skipping the ad.
                    Toast.makeText(DeatailsMovie.this, "Skipped", Toast.LENGTH_SHORT).show();
                } else if (finishState == UnityAds.FinishState.ERROR) {
                    // Log an error.
                    Toast.makeText(DeatailsMovie.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {
                Toast.makeText(DeatailsMovie.this, "Error " + unityAdsError, Toast.LENGTH_SHORT).show();
            }
        };
        UnityAds.setListener(rewardedListner);
        UnityAds.load(rewardedPlacement);
        Intialization();
        ReciveDynamicLinks();
        getIDandRunCode();
        favDialogBox();
        watchMovie();

        getMovieServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetLink(name, DeatailsMovie.this).execute();

//                if (UnityAds.isReady(interPlacement)) {
//                    UnityAds.show(DeatailsMovie.this, interPlacement);
//                }
//                if (UnityAds.isReady(rewardedPlacement)) {
//                    UnityAds.show(DeatailsMovie.this, rewardedPlacement);
//                }
            }
        });
        //================================================Button===========================================================================//


        new Thread(new Runnable() {
            @Override
            public void run() {
                // Run whatever background code you want here.
                try {
                    Document document = Jsoup.connect(gogoAnimeUrl).get();
                    watchonlineLink = document.body().text();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    private void favDialogBox() {
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
    }

    private void getIDandRunCode() {

        try {

            Intent intent = getIntent();
            Intent jio = getIntent();

            id = intent.getStringExtra("pass_id");
            id2 = jio.getStringExtra("searchToDetails");

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (id == null) {
            showZid = id2;
            String videoUrl = "https://api.themoviedb.org/3/movie/" + id2 + "/videos?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US";
            String url = "https://api.themoviedb.org/3/movie/" + id2 + "?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US";
            gogoAnimeUrl = "https://getsuperembed.link/?video_id=" + id2 + "&tmdb=1";
            MoviesDetails moviesDetails = new MoviesDetails(url, videoUrl);
            moviesDetails.start();
//            trailerFetch(videoUrl);
            ImagesSlideShow("https://api.themoviedb.org/3/movie/" + id2 + "/images?api_key=e707c6ad620e69cda284fbbc6af06e43");
            for (int p = 1; p < 5; p++) {
                Recommend("https://api.themoviedb.org/3/movie/" + id2 + "/similar?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US&page=" + p,
                        "https://api.themoviedb.org/3/movie/" + id2 + "/recommendations?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US&page=" + p);
            }

        } else {
            showZid = id;
            String videoUrl = "https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US";
            String url = "https://api.themoviedb.org/3/movie/" + id + "?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US";
            String ImgURl = "https://api.themoviedb.org/3/movie/" + id + "/images?api_key=e707c6ad620e69cda284fbbc6af06e43";
            gogoAnimeUrl = "https://getsuperembed.link/?video_id=" + id + "&tmdb=1";
            MoviesDetails moviesDetails = new MoviesDetails(url, videoUrl);
            moviesDetails.start();
//            trailerFetch(videoUrl);
            ImagesSlideShow(ImgURl);
            for (int p = 1; p < 5; p++) {
                Recommend("https://api.themoviedb.org/3/movie/" + id + "/similar?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US&page=" + p,
                        "https://api.themoviedb.org/3/movie/" + id + "/recommendations?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US&page=" + p);
            }
        }

    }

    private void Intialization() {
        moviePoster = findViewById(R.id.moive_poster);
//        movieRank = findViewById(R.id.movie_rank);
        D_button = findViewById(R.id.download_link);
        getMovieServer = findViewById(R.id.get_movieserver);
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
        layout = findViewById(R.id.movieDetailsLayout);
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
        HindiLink = new ArrayList<>();
        modelList = new ArrayList<>();
        RMList = new ArrayList<>();
        RMList2 = new ArrayList<>();
        trailerList = new ArrayList<>();
        trailerRecycle = findViewById(R.id.movie_trailer_recycle);
        RMRecycle = findViewById(R.id.movie_similar_recycle);
        RMRecycle2 = findViewById(R.id.movie_recommended_recycle);
        progressBar = findViewById(R.id.m_progressBar);
        m_watch = findViewById(R.id.m_server);
        h_watch = findViewById(R.id.h_server);

        ServerLayout = findViewById(R.id.SeverLayout);
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
                            if (deepLink != null) {
                                String dynamicID = deepLink.getQueryParameter("id");
                                Log.i("LInk", "onSuccess: " + dynamicID);
                            }
                        }


                        // Handle the deep link. For example, open the linked
                        // content, or apply promotional credit to the user's
                        // account.
                        // ...

                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "getDynamicLink:onFailure", e);
                    }
                });
    }


//    private void ReciveDynamicLinks() {
//        FirebaseDynamicLinks.getInstance()
//                .getDynamicLink(getIntent())
//                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
//                    @Override
//                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
//                        // Get deep link from result (may be null if no link is found)
//                        Uri deepLink = null;
//                        if (pendingDynamicLinkData != null) {
//                            deepLink = pendingDynamicLinkData.getLink();
//                        }
//                        if (deepLink != null){
//                            id2 = deepLink.getQueryParameter("passID");
//                            Log.i("dynamic",id2);
//                        }
//                    }
//                })
//                .addOnFailureListener(this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("TAG", "getDynamicLink:onFailure", e);
//                    }
//                });
//
//    }

    private void createDynamicLinks() {
//
//        Toast.makeText(this, "Link Copied", Toast.LENGTH_SHORT).show();
//
//        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
//                .setLink(Uri.parse("https://www.example.com?passID="+tmdb_id))
//                .setDomainUriPrefix("https://animasium.page.link")
//                // Open links with this app on Android
//                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
//                // Open links with com.example.ios on iOS
////                .setIosParameters(new DynamicLink.IosParameters.Builder("com.example.ios").build())
//                .buildDynamicLink();
//
//        Uri dynamicLinkUri = dynamicLink.getUri();
//        Log.i("DynamicLInk","Short :"+dynamicLinkUri);
//
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
//                i.putExtra("watchID","https://curtstream.ir/movies/tmdb/"+tmdb_id);
//                i.putExtra("watchID","https://api.123movie.cc/jadeed.php?imdb="+imdb_id+"&server_name=vcu");

//                i.putExtra("watchID", "https://autoembed.xyz/movie/tmdb/"+tmdb_id);
                i.putExtra("watchID", "https://www.2embed.ru/embed/tmdb/movie?id=" + tmdb_id);


                startActivity(i);
            }
        });
        m_watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeatailsMovie.this, WebView.class);
//                i.putExtra("watchID","https://gomo.to/movie/"+imdb_id);
//                i.putExtra("watchID","https://api.123movie.cc/jadeed.php?imdb="+imdb_id+"&server_name=vcu");
                i.putExtra("watchID", watchonlineLink);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.fav_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.fav_add_barbtn) {
            Toast.makeText(this, "fav pressed", Toast.LENGTH_SHORT).show();
            dialog.show();
        } else if (item.getItemId() == R.id.share_btn) {

            shareIntent();
            Toast.makeText(this, "Work in Progress :)", Toast.LENGTH_SHORT).show();


        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    private void shareIntent() {

        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://www.moviesium.page.link/?id=" + tmdb_id))
                .setDomainUriPrefix("https://moviesium.page.link")
                // Set parameters
                // ...
                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();

                            Log.i("LInks", shortLink + "" + "\n" + flowchartLink);
                        } else {
                            // Error
                            // ...
                        }
                    }
                });

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/*");
        intent.putExtra(Intent.EXTRA_TEXT, "*" + name + "*\n" + des + "\n Watch Here :-" + watchonlineLink + "\n *SearchIT*");
        startActivity(Intent.createChooser(intent, "send"));


    }

    public void shareImage(Bitmap bitmap) {
        Uri contentUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentUri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        ContentValues newImageDetails = new ContentValues();
        newImageDetails.put(MediaStore.Images.Media.DISPLAY_NAME, "filename");
        Uri imageContentUri = contentResolver.insert(contentUri, newImageDetails);

        try (ParcelFileDescriptor fileDescriptor =
                     contentResolver.openFileDescriptor(imageContentUri, "w", null)) {
            FileDescriptor fd = fileDescriptor.getFileDescriptor();
            OutputStream outputStream = new FileOutputStream(fd);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bufferedOutputStream);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (IOException e) {
            Log.e("TAG", "Error saving bitmap", e);
        }

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_STREAM, imageContentUri);
//        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi, I would like to Share you this amazing movie : "+ "*"+name +"*"+ " \n here's a link to watch it online - " +
//                watchonlineLink);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi, I would like to Share you this amazing movie");
        sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sendIntent.setType("image/*");
        Intent shareIntent = Intent.createChooser(sendIntent, "Share with");
        startActivity(shareIntent);
    }

    public class GetLink extends AsyncTask<Void, String, Void> {
        String movieName;
        Context context;

        public GetLink(String movieName, Context context) {
            this.movieName = movieName;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    FancyToast.makeText(getApplicationContext(), "Searching Hindi Links Wait...", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();

                }
            });
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ServerLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            getMovieServer.setVisibility(View.GONE);
            super.onPostExecute(aVoid);

        }

        @Override
        protected Void doInBackground(Void... voids) {

//            Log.i(TAG, "doInBackground: " + getHindilink(name));
            BeatScraper beatScraper = new BeatScraper();
            HScraper hScraper = new HScraper();
            try {
                String separator = " -";
                String mN = "";
                if (movieName.contains(separator)) {
                    int sepPos = movieName.lastIndexOf(separator);
                    if (sepPos == -1) {
                        System.out.println("");
                    }
                    mN = movieName.substring(0, sepPos);
                } else {
                    mN = movieName;
                }

                linkList = hScraper.Hscraper(movieName);
                String streaminglink = beatScraper.scraper(mN);

                if (streaminglink != null) {
                    Log.i("linkfound", streaminglink);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("UI thread", "I am the UI thread");
                            FancyToast.makeText(getApplicationContext(), "Hindi Links Found", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

                        }
                    });
                    HlinkCLick(streaminglink, true);
                } else {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("UI thread", "I am the UI thread");
                            FancyToast.makeText(getApplicationContext(), "Hindi Links Not Found", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                        }
                    });
                    h_watch.setBackgroundColor(Color.RED);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

//    public String getHindilink(String searchKey) {
//
//
//        Log.i(TAG, "called");
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                //TODO your background code
//                Document gogoAnimePageDocument1 = null;
//                try {
//                    String search = searchKey.replace("\\s", "%20");
//                    String k = "https://111watcho.site/?s=" + search;
//                    gogoAnimePageDocument1 = Jsoup.connect(k).get();
//                    Log.i(TAG, k);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Elements vi = gogoAnimePageDocument1.getElementsByClass("ml-mask jt");
//
//                if (vi.isEmpty()) {
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.d("UI thread", "I am the UI thread");
//                            FancyToast.makeText(getApplicationContext(), "Hindi Links Not Found", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
//
//                        }
//                    });
//                } else {
//                    int k = 0;
//                    while (k < vi.size()) {
//                        String movie_linkPass = gogoAnimePageDocument1.getElementsByClass("ml-mask jt").get(k).getElementsByTag("a").get(0).attr("href");
//                        String movieTitlePass = gogoAnimePageDocument1.getElementsByClass("ml-mask jt").get(k).getElementsByTag("a").get(0).attr("oldtitle");
//                        Log.i(TAG, "MOVIETitle: " + movieTitlePass);
//                        Log.i(TAG, "MOVIEURl: " + movie_linkPass);
//                        Log.i(TAG, "run: " + name);
//                        Document fechlink = null;
//                        if (containsWord(movieTitlePass, searchKey) && (containsWord(movieTitlePass, "hindi") || containsWord(movieTitlePass, "hindi"))) {
//
//
//                            try {
//                                Log.i(TAG, "doInBackground: " + movie_linkPass);
//                                fechlink = Jsoup.connect(movie_linkPass).get();
//                                String buzz = fechlink.getElementsByTag("iframe").get(1).attr("src");
//                                Log.i(TAG, "doInBackground:" + fechlink);
//                                Log.i(TAG, "doInBackground:" + buzz);
//                                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Log.d("UI thread", "I am the UI thread");
//                                        FancyToast.makeText(getApplicationContext(), "Hindi Links Found", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
//
//                                    }
//                                });
//                                HlinkCLick(buzz, true);
////                                DownloadIT(Replacelink(buzz), true);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//                        } else {
//                            k++;
//                        }
//                    }
//                }
//
//            }
//        });
//
//        return "Done";
//    }

    public void
    HlinkCLick(String key, Boolean found) {


        h_watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (found) {
                    Intent i = new Intent(DeatailsMovie.this, WebView.class);
                    i.putExtra("watchID", "https:" + key);
                    startActivity(i);
                } else {
                    Toast.makeText(DeatailsMovie.this, "Hindi Dub Not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    static boolean containsWord(String mainString, String word) {

        Pattern pattern = Pattern.compile("\\b" + word + "\\b", Pattern.CASE_INSENSITIVE); // "\\b" represents any word boundary.
        Matcher matcher = pattern.matcher(mainString);
        return matcher.find();
    }

    public void DownloadIT(String key, Boolean found) {

        D_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (found) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(key));
                    // Whatever browser you are using
                    startActivity(browserIntent);
                } else {
                    Toast.makeText(DeatailsMovie.this, "No Download LinK Found", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public String Replacelink(String key) {
        int index = 16;
        char ch = 'd';
        return key.substring(0, index) + ch
                + key.substring(index + 1);

    }


//    public void ToggleBannerAd () {
//        // If no banner exists, show one; otherwise remove the existing one:
//        if (bannerView == null) {
//            // Optionally specify the banner’s anchor position:
//            UnityBanners.setBannerPosition (BannerPosition.BOTTOM_CENTER);
//            // Request ad content for your Placement, and load the banner:
//            Activity myActivity = this;
//            UnityBanners.loadBanner (myActivity, "banner");
//        } else {
//            UnityBanners.destroy ();
//        }
//    }

//    // Implement the banner listener interface methods:
//    private class UnityBannerListener implements IUnityBannerListener {
//
//        @Override
//        public void onUnityBannerLoaded (String placementId, View view) {
//            // When the banner content loads, add it to the view hierarchy:
//            bannerView = view;
//            ((ViewGroup) findViewById (R.id.banneradsUnity)).addView (view);
//        }
//
//        @Override
//        public void onUnityBannerUnloaded (String placementId) {
//            // When the banner’s no longer in use, remove it from the view hierarchy:
//            bannerView = null;
//        }
//
//        @Override
//        public void onUnityBannerShow (String placementId) {
//            // Called when the banner is first visible to the user.
//        }
//
//        @Override
//        public void onUnityBannerClick (String placementId) {
//            // Called when the banner is clicked.
//        }
//
//        @Override
//        public void onUnityBannerHide (String placementId) {
//            // Called when the banner is hidden from the user.
//        }
//
//        @Override
//        public void onUnityBannerError (String message) {
//            // Called when an error occurred, and the banner failed to load or show.
//        }
//    }
}