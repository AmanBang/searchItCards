package com.example.searchitcards;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.os.Handler;


import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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



    public void searchOnClick(View v){
            searchText = findViewById(R.id.searchEditText);
            String search = searchText.getText().toString();

        Intent myIntent = new Intent(MainActivity.this, SearchResults.class);
        myIntent.putExtra("search_url", search); //Optional parameters
        this.startActivity(myIntent);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


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

        animeResult = new ArrayList<>();
        upcomingAnimeList = new ArrayList<>();
        topAiringList = new ArrayList<>();
        popularAnimeList = new ArrayList<>();
        animeMovieList = new ArrayList<>();
        String topUrl = "https://api.jikan.moe/v3/top/anime";
        String  topUpcoming ="https://api.jikan.moe/v3/top/anime/1/upcoming";
        String airingAnime ="https://api.jikan.moe/v3/top/anime/1/airing";
        String popularAnime ="https://api.jikan.moe/v3/top/anime/1/bypopularity";
        String movie ="https://api.jikan.moe/v3/top/anime/1/movie";
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
                       return true;
                   case R.id.nav_home:

                       return true;
                   case R.id.nav_news:
                       startActivity(new Intent(getApplicationContext()
                               ,News_activity.class));
                       overridePendingTransition(0, 0);
                       return true;
                   case R.id.nav_tv:
                       startActivity(new Intent(getApplicationContext()
                               ,TVShows.class));
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


}
