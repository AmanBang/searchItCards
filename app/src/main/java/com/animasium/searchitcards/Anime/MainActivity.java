package com.animasium.searchitcards.Anime;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.os.Handler;


import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.animasium.searchitcards.Anime.AdapterCLass.Topresults;
import com.animasium.searchitcards.DashboarduSER;
import com.animasium.searchitcards.Movie.Movies_activity;
import com.animasium.searchitcards.Favoutites.Favourites;
import com.animasium.searchitcards.R;
import com.animasium.searchitcards.TVshows.TVShows;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.material.bottomnavigation.BottomNavigationView;

//import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    TopAnime topAnime;

    RecyclerView topAnimeRecycle;
    RecyclerView upcomingAnimeRecycle;
    RecyclerView topAiringRecycle;
    RecyclerView popularAnimeRecycle;
    RecyclerView animeMovieRecycle;

    List<Topresults> animeResult;
    List<Topresults> upcomingAnimeList;
    List<Topresults> topAiringList;
    List<Topresults> popularAnimeList;
    List<Topresults> animeMovieList;

    EditText searchText;
    //  Object[] check ={};
    LinearLayout episodeLL;
    LinearLayout releasedLL;
    LinearLayout typeLL;

    //Show textView
    Button AM_top;
    Button AM_airing;
    Button AM_upcoming;
    Button AM_popular;
    Button AM_movie;

    String topUrl;
    String topUpcoming;
    String airingAnime;
    String popularAnime;
    String movie;
    private AdView mAdView;
    private RewardedInterstitialAd rewardedInterstitialAd;
    private String TAG = "MainActivity";
    private InterstitialAd mInterstitialAd;

    RequestQueue queue;
    private static final String ONESIGNAL_APP_ID = "bd44539b-9062-44db-863c-b232079ad253";

//=================================================================================================================================================//

    public void searchOnClick(View v) {
        searchText = findViewById(R.id.searchEditText);
        String search = searchText.getText().toString();

        Intent myIntent = new Intent(MainActivity.this, AnimeSearchResults.class);
        myIntent.putExtra("search_url", search); //Optional parameters
        this.startActivity(myIntent);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //====================================================more Buttton onclick Listner =============================================================//
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

//        }

    }
//--------------------------------------------------------------------------------------------------------------------------------------------------//

    public class HomeView extends Thread {

        public HomeView(String top) {


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                    try {
                        JSONArray jsonArray = response.getJSONArray("data");


                        Log.i("response", jsonArray.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject search = jsonArray.getJSONObject(i);
                            Topresults topresults = new Topresults();
                            topresults.setTitle(search.getString("title"));
//                            topresults.setScore(search.getInt("score"));
                            topresults.setStart_date(search.getString("status"));
                            topresults.setType(search.getString("type"));
                            topresults.setMal_id(search.getInt("mal_id"));
//                            topresults.setRank((search.getInt("rank")));
                            JSONObject object = search.getJSONObject("images");
                            JSONObject jpg = object.getJSONObject("jpg");

                            topresults.setImage_url(jpg.getString("image_url"));

                            animeResult.add(topresults);
                            //  Log.i("re", animeResult.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    topAnimeRecycle.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    topAnime = new TopAnime(MainActivity.this, animeResult);
                    topAnimeRecycle.setAdapter(topAnime);


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


    public void AnimeUpcoming(String top) {

        JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray = response.getJSONArray("top");
                    //  Log.i("response",jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject upcomingSearch = jsonArray.getJSONObject(i);


                        Topresults upcomingResults = new Topresults();
                        upcomingResults.setTitle(upcomingSearch.getString("title"));
//                        upcomingResults.setScore(upcomingSearch.getInt("score"));
                        upcomingResults.setStart_date(upcomingSearch.getString("start_date"));
                        upcomingResults.setType(upcomingSearch.getString("type"));
                        upcomingResults.setMal_id(upcomingSearch.getInt("mal_id"));
//                        upcomingResults.setRank((upcomingSearch.getInt("rank")));
                        upcomingResults.setImage_url(upcomingSearch.getString("image_url"));

                        upcomingAnimeList.add(upcomingResults);
                        //  Log.i("Popular", popularitySearch.getString("title"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                upcomingAnimeRecycle.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                topAnime = new TopAnime(MainActivity.this, upcomingAnimeList);
                upcomingAnimeRecycle.setAdapter(topAnime);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request2);
//


    }

    public void AiringAnime(String top) {


        JsonObjectRequest request3 = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray = response.getJSONArray("top");
                    //  Log.i("response",jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject airingSearch = jsonArray.getJSONObject(i);


                        Topresults airingResult = new Topresults();
                        airingResult.setTitle(airingSearch.getString("title"));
//                        airingResult.setScore(airingSearch.getInt("score"));
//
                        airingResult.setType(airingSearch.getString("type"));
                        airingResult.setStart_date(airingSearch.getString("start_date"));

                        airingResult.setMal_id(airingSearch.getInt("mal_id"));
//                        airingResult.setRank((airingSearch.getInt("rank")));
                        airingResult.setImage_url(airingSearch.getString("image_url"));

                        topAiringList.add(airingResult);
                        //  Log.i("Popular", popularitySearch.getString("title"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                topAiringRecycle.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                topAnime = new TopAnime(MainActivity.this, topAiringList);
                topAiringRecycle.setAdapter(topAnime);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request3);
//


    }

    public void PopularAnime(String top) {


        JsonObjectRequest request4 = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray = response.getJSONArray("top");
                    //  Log.i("response",jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject popularSearch = jsonArray.getJSONObject(i);


                        Topresults popularResult = new Topresults();
                        popularResult.setTitle(popularSearch.getString("title"));
//                        popularResult.setScore(popularSearch.getInt("score"));
                        popularResult.setType(popularSearch.getString("type"));
                        popularResult.setStart_date(popularSearch.getString("start_date"));
                        popularResult.setMal_id(popularSearch.getInt("mal_id"));
//                        popularResult.setRank((popularSearch.getInt("rank")));
                        popularResult.setImage_url(popularSearch.getString("image_url"));

                        popularAnimeList.add(popularResult);
                        //  Log.i("Popular", popularitySearch.getString("title"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                popularAnimeRecycle.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                topAnime = new TopAnime(MainActivity.this, popularAnimeList);
                popularAnimeRecycle.setAdapter(topAnime);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request4);
//


    }


    public void AnimeMovie(String top) {


        JsonObjectRequest request5 = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray = response.getJSONArray("top");
                    //  Log.i("response",jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject movieSearch = jsonArray.getJSONObject(i);


                        Topresults movieResult = new Topresults();
                        movieResult.setTitle(movieSearch.getString("title"));
//                        movieResult.setScore(movieSearch.getInt("score"));
                        movieResult.setStart_date(movieSearch.getString("start_date"));
                        movieResult.setType(movieSearch.getString("type"));
                        movieResult.setMal_id(movieSearch.getInt("mal_id"));
//                        movieResult.setRank((movieSearch.getInt("rank")));
                        movieResult.setImage_url(movieSearch.getString("image_url"));

                        animeMovieList.add(movieResult);
                        //  Log.i("Popular", popularitySearch.getString("title"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                animeMovieRecycle.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                topAnime = new TopAnime(MainActivity.this, animeMovieList);
                animeMovieRecycle.setAdapter(topAnime);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request5);
//


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//
//        mAdView = findViewById(R.id.adViewMain);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        // rewardedInterstitialAd.show(/* Activity */ MainActivity.this,/*
        // OnUserEarnedRewardListener */ (OnUserEarnedRewardListener) MainActivity.this);


//        InterstitialAd.load(this, "ca-app-pub-6544805297981669/7466458023", adRequest, new InterstitialAdLoadCallback() {
//            @Override
//            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                // The mInterstitialAd reference will be null until
//                // an ad is loaded.
//                mInterstitialAd = interstitialAd;
//                Log.i(TAG, "onAdLoaded");
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                // Handle the error
//                Log.i(TAG, loadAdError.getMessage());
//                mInterstitialAd = null;
//            }
//        });

        queue = Volley.newRequestQueue(getApplicationContext());
        ///////////========================================================================================================/////////////////////
// Obtain the FirebaseAnalytics instance.
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//        // Enable verbose OneSignal logging to debug issues if needed.
//        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
//
//        // OneSignal Initialization
//        OneSignal.initWithContext(this);
//        OneSignal.setAppId(ONESIGNAL_APP_ID);

        topAnimeRecycle = findViewById(R.id.top_anime_recycle);
        topAnimeRecycle.setHasFixedSize(true);
        upcomingAnimeRecycle = findViewById(R.id.top_upcoming_anime_recycle);
        upcomingAnimeRecycle.setHasFixedSize(true);
        topAiringRecycle = findViewById(R.id.top_anime_airing_recycle);
        topAiringRecycle.setHasFixedSize(true);
        popularAnimeRecycle = findViewById(R.id.popular_anime_recycle);
        popularAnimeRecycle.setHasFixedSize(true);
        animeMovieRecycle = findViewById(R.id.top_movie_recycle);
        animeMovieRecycle.setHasFixedSize(true);
        animeMovieRecycle.isAnimating();
//        episodeLL = findViewById(R.id.episode_ll);
        //  releasedLL =findViewById(R.id.released_ll);
        typeLL = findViewById(R.id.type_ll);

        EditText std = findViewById(R.id.searchEditText);

        std.setOnEditorActionListener(exampleListener);

//        AM_top = findViewById(R.id.AM_top);
//        AM_airing = findViewById(R.id.AM_airing);
//        AM_upcoming = findViewById(R.id.AM_upcoming);
//        AM_popular = findViewById(R.id.AM_popular);
//        AM_movie = findViewById(R.id.AM_Movie);


//        AM_top.setOnClickListener(this);
//        AM_airing.setOnClickListener(this);
//        AM_upcoming.setOnClickListener(this);
//        AM_popular.setOnClickListener(this);
//        AM_movie.setOnClickListener(this);
        //--------------------------------------------------------------------------------------///

        animeResult = new ArrayList<>();
        upcomingAnimeList = new ArrayList<>();
        topAiringList = new ArrayList<>();
        popularAnimeList = new ArrayList<>();
        animeMovieList = new ArrayList<>();
        topUrl = "https://api.jikan.moe/v4/top/anime";
        topUpcoming = "https://api.jikan.moe/v3/top/anime/1/upcoming";
        airingAnime = "https://api.jikan.moe/v3/top/anime/1/airing";
        popularAnime = "https://api.jikan.moe/v3/top/anime/1/bypopularity";
        movie = "https://api.jikan.moe/v3/top/anime/1/movie";
        HomeView homeView = new HomeView(topUrl);
        homeView.start();
        AiringAnime(airingAnime);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                AnimeMovie(movie);

            }
        }, 1000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms

                AnimeUpcoming(topUpcoming);
            }
        }, 1100);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                PopularAnime(popularAnime);
            }
        }, 1000);
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                showads();
//
//            }
//        }, 5000);

        // find show all button


//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_movie:
                        startActivity(new Intent(getApplicationContext()
                                , Movies_activity.class));
                        overridePendingTransition(0, 0);

                        return true;
                    case R.id.nav_home:

                        return true;
                    case R.id.nav_news:
                        startActivity(new Intent(getApplicationContext()
                                , Favourites.class));
                        overridePendingTransition(0, 0);

                        return true;
                    case R.id.nav_tv:
                        startActivity(new Intent(getApplicationContext()
                                , TVShows.class));
                        overridePendingTransition(0, 0);

                        return true;
                    case R.id.nav_dashboard:
                        startActivity(new Intent(getApplicationContext()
                                , DashboarduSER.class));
                        overridePendingTransition(0, 0);

                        return true;
                }
                return false;
            }
        });

    }

    public void AMshowMore(View view) {
        Intent intent = new Intent(this, AnimeShowMore.class);
//
        switch (view.getId()) {
            case R.id.AM_top:

                intent.putExtra("key_for_more", topUrl);
                intent.putExtra("title_SM", "Top Animes");
                startActivity(intent);

                break;
            case R.id.AM_airing:

                intent.putExtra("key_for_more", airingAnime);
                intent.putExtra("title_SM", "Airing Animes");
                startActivity(intent);

                break;
            case R.id.AM_popular:

                intent.putExtra("key_for_more", popularAnime);
                intent.putExtra("title_SM", "Popular Animes");
                startActivity(intent);

                break;
            case R.id.AM_upcoming:

                intent.putExtra("key_for_more", topUpcoming);
                intent.putExtra("title_SM", "Upcoming Animes");
                startActivity(intent);

                break;
            case R.id.AM_Movie:

                intent.putExtra("key_for_more", movie);
                intent.putExtra("title_SM", "Anime Movies");
                startActivity(intent);

                break;
        }
    }

    TextView.OnEditorActionListener exampleListener = new TextView.OnEditorActionListener() {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH
            ) {
                searchText = findViewById(R.id.searchEditText);
                String search = searchText.getText().toString();

                Intent myIntent = new Intent(MainActivity.this, AnimeSearchResults.class);
                myIntent.putExtra("search_url", search); //Optional parameters
                startActivity(myIntent);


            }
            return true;
        }
    };

//    public void showads() {
//        try {
//            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//                @Override
//                public void onAdDismissedFullScreenContent() {
//                    // Called when fullscreen content is dismissed.
//                    Log.d(TAG, "The ad was dismissed.");
//                }
//
//                @Override
//                public void onAdFailedToShowFullScreenContent(AdError adError) {
//                    // Called when fullscreen content failed to show.
//                    Log.d(TAG, "The ad failed to show.");
//                }
//
//                @Override
//                public void onAdShowedFullScreenContent() {
//                    // Called when fullscreen content is shown.
//                    // Make sure to set your reference to null so you don't
//                    // show it a second time.
//                    mInterstitialAd = null;
//                    Log.d(TAG, "The ad was shown.");
//                }
//            });
//            if (mInterstitialAd != null) {
//                mInterstitialAd.show(MainActivity.this);
//            } else {
//                Log.d(TAG, "The interstitial ad wasn't ready yet.");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }
}
