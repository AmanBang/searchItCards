package com.example.searchitcards.Anime;

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
import com.example.searchitcards.Anime.AdapterCLass.Topresults;
import com.example.searchitcards.DashboarduSER;
import com.example.searchitcards.Movie.MovieSearchResult;
import com.example.searchitcards.Movie.Movies_activity;
import com.example.searchitcards.Favoutites.Favourites;
import com.example.searchitcards.R;
import com.example.searchitcards.TVshows.TVShows;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
        String  topUpcoming;
        String airingAnime ;
        String popularAnime;
        String movie;
//=================================================================================================================================================//

    public void searchOnClick(View v){
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

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                    try {
                        JSONArray jsonArray = response.getJSONArray("top");


                         Log.i("response",jsonArray.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject search = jsonArray.getJSONObject(i);


                            Topresults topresults = new Topresults();
                            topresults.setTitle(search.getString("title"));
//                            topresults.setScore(search.getInt("score"));
                            topresults.setStart_date(search.getString("start_date"));
                            topresults.setType(search.getString("type"));
                            topresults.setMal_id(search.getInt("mal_id"));
//                            topresults.setRank((search.getInt("rank")));
                            topresults.setImage_url(search.getString("image_url"));

                            animeResult.add(topresults);
                          //  Log.i("re", animeResult.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    topAnimeRecycle.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                    topAnime = new TopAnime(MainActivity.this,animeResult);
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

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
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

                    upcomingAnimeRecycle.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                    topAnime = new TopAnime(MainActivity.this,upcomingAnimeList);
                    upcomingAnimeRecycle.setAdapter(topAnime);




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
//


    }

    public void AiringAnime(String top) {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
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

                topAiringRecycle.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                topAnime = new TopAnime(MainActivity.this,topAiringList);
                topAiringRecycle.setAdapter(topAnime);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
//


    }

    public void PopularAnime(String top) {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
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

                popularAnimeRecycle.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                topAnime = new TopAnime(MainActivity.this,popularAnimeList);
                popularAnimeRecycle.setAdapter(topAnime);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
//


    }


    public void AnimeMovie(String top) {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
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

                animeMovieRecycle.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                topAnime = new TopAnime(MainActivity.this,animeMovieList);
                animeMovieRecycle.setAdapter(topAnime);






            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
//


    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

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
         topUrl = "https://api.jikan.moe/v3/top/anime";
          topUpcoming ="https://api.jikan.moe/v3/top/anime/1/upcoming";
         airingAnime ="https://api.jikan.moe/v3/top/anime/1/airing";
         popularAnime ="https://api.jikan.moe/v3/top/anime/1/bypopularity";
         movie ="https://api.jikan.moe/v3/top/anime/1/movie";
        HomeView homeView = new HomeView(topUrl);
        homeView.start();


//
//
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                AiringAnime(airingAnime);

            }
        }, 500);
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
        }, 2000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                PopularAnime(popularAnime);
            }
        },2000);
        // find show all button





//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()){
                   case R.id.nav_movie:
                       startActivity(new Intent(getApplicationContext()
                               , Movies_activity.class));
                       overridePendingTransition(0, 0);
                       finish();
                       return true;
                   case R.id.nav_home:

                       return true;
                   case R.id.nav_news:
                       startActivity(new Intent(getApplicationContext()
                               , Favourites.class));
                       overridePendingTransition(0, 0);
                       finish();
                       return true;
                   case R.id.nav_tv:
                       startActivity(new Intent(getApplicationContext()
                               , TVShows.class));
                       overridePendingTransition(0, 0);
                       finish();
                       return true;
                   case R.id.nav_dashboard:
                       startActivity(new Intent(getApplicationContext()
                               , DashboarduSER.class));
                       overridePendingTransition(0, 0);
                       finish();
                       return true;
               }
               return false;
            }
        });

    }
public void AMshowMore(View view){
            Intent intent = new Intent(this, AnimeShowMore.class);
//
        switch (view.getId()){
            case R.id.AM_top:
                intent.putExtra("key_for_more",topUrl);
                intent.putExtra("title_SM","Top Animes");
                startActivity(intent);
                break;
            case R.id.AM_airing:
                intent.putExtra("key_for_more",airingAnime);
                intent.putExtra("title_SM","Airing Animes");
                startActivity(intent);
                break;
            case R.id.AM_popular:
                intent.putExtra("key_for_more",popularAnime);
                intent.putExtra("title_SM","Popular Animes");
                startActivity(intent);
                break;
            case R.id.AM_upcoming:
                intent.putExtra("key_for_more",topUpcoming);
                intent.putExtra("title_SM","Upcoming Animes");
                startActivity(intent);
                break;
            case R.id.AM_Movie:
                intent.putExtra("key_for_more",movie);
                intent.putExtra("title_SM","Anime Movies");
                startActivity(intent);
                break;
}}
    TextView.OnEditorActionListener exampleListener = new TextView.OnEditorActionListener(){

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
}
