package com.example.searchitcards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.searchitcards.Movie.mAdapter.MovieAdapter;
import com.example.searchitcards.Movie.mAdapter.Movies;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Movies_activity extends AppCompatActivity {

RecyclerView popularMovieRecycle;
RecyclerView nowPlayingRecycle;
RecyclerView topRatedRecycle;
RecyclerView upcomingRecycle;

MovieAdapter movieAdapter;

List<Movies> pList;
List<Movies> nList;
List<Movies> tList;
List<Movies> uList;

ImageView Search;


public void SMovies(View view){
    EditText searchText = findViewById(R.id.searchEditTextMovie);
    String search = searchText.getText().toString();

    Intent myIntent = new Intent(this, MovieSearchResult.class);
 myIntent.putExtra("key-123", search); //Optional parameters
    this.startActivity(myIntent);
}

public void pMethod(String top){

    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {


            try {
                JSONArray jsonArray = response.getJSONArray("results");


                Log.i("response",jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject search = jsonArray.getJSONObject(i);


                    Movies topresults = new Movies();
                    topresults.setTitle(search.getString("title"));
//                            topresults.setScore(search.getInt("score"));
                    topresults.setRelease_date(search.getString("release_date"));
//                    topresults.setType(search.getString("type"));
                    topresults.setId(search.getInt("id"));
//                            topresults.setRank((search.getInt("rank")));
                    topresults.setPoster_path(search.getString("poster_path"));

                    pList.add(topresults);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            popularMovieRecycle.setLayoutManager(new LinearLayoutManager(Movies_activity.this,LinearLayoutManager.HORIZONTAL,false));
            movieAdapter = new MovieAdapter(Movies_activity.this,pList);
            popularMovieRecycle.setAdapter(movieAdapter);



        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });
    queue.add(request);
//
}

    public void uMethod(String top){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray = response.getJSONArray("results");


                    Log.i("response",jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject search = jsonArray.getJSONObject(i);


                        Movies topresults = new Movies();
                        topresults.setTitle(search.getString("title"));
//                            topresults.setScore(search.getInt("score"));
                        topresults.setRelease_date(search.getString("release_date"));
//                    topresults.setType(search.getString("type"));
                        topresults.setId(search.getInt("id"));
//                            topresults.setRank((search.getInt("rank")));
                        topresults.setPoster_path(search.getString("poster_path"));

                        uList.add(topresults);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                upcomingRecycle.setLayoutManager(new LinearLayoutManager(Movies_activity.this,LinearLayoutManager.HORIZONTAL,false));
                movieAdapter = new MovieAdapter(Movies_activity.this,uList);
                upcomingRecycle.setAdapter(movieAdapter);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
//
    }

    public void tMethod(String top){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray = response.getJSONArray("results");


                    Log.i("response",jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject search = jsonArray.getJSONObject(i);


                        Movies topresults = new Movies();
                        topresults.setTitle(search.getString("title"));
//                            topresults.setScore(search.getInt("score"));
                        topresults.setRelease_date(search.getString("release_date"));
//                    topresults.setType(search.getString("type"));
                        topresults.setId(search.getInt("id"));
//                            topresults.setRank((search.getInt("rank")));
                        topresults.setPoster_path(search.getString("poster_path"));

                        tList.add(topresults);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                topRatedRecycle.setLayoutManager(new LinearLayoutManager(Movies_activity.this,LinearLayoutManager.HORIZONTAL,false));
                movieAdapter = new MovieAdapter(Movies_activity.this,tList);
                topRatedRecycle.setAdapter(movieAdapter);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
//
    }

    public void nMethod(String top){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray = response.getJSONArray("results");


                    Log.i("response",jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject search = jsonArray.getJSONObject(i);


                        Movies topresults = new Movies();
                        topresults.setTitle(search.getString("title"));
//                            topresults.setScore(search.getInt("score"));
                        topresults.setRelease_date(search.getString("release_date"));
//                    topresults.setType(search.getString("type"));
                        topresults.setId(search.getInt("id"));
//                            topresults.setRank((search.getInt("rank")));
                        topresults.setPoster_path(search.getString("poster_path"));

                        nList.add(topresults);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                nowPlayingRecycle.setLayoutManager(new LinearLayoutManager(Movies_activity.this,LinearLayoutManager.HORIZONTAL,false));
                movieAdapter = new MovieAdapter(Movies_activity.this,nList);
                nowPlayingRecycle.setAdapter(movieAdapter);



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
        setContentView(R.layout.activity_movies_activity);


        popularMovieRecycle = findViewById(R.id.popular_movie_recycle);
        nowPlayingRecycle = findViewById(R.id.now_playing_recycle);
        topRatedRecycle = findViewById(R.id.top_rated_recycle);
        upcomingRecycle = findViewById(R.id.upcoming_movie_recycle);

        pList = new ArrayList<>();
        uList = new ArrayList<>();
        tList = new ArrayList<>();
        nList = new ArrayList<>();

        Search = findViewById(R.id.movieSearchIcon);


        Date date = new Date();
        String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);

        for (int p = 1;p<5;p++){
            pMethod("https://api.themoviedb.org/3/movie/popular?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US&page="+p);
            tMethod("https://api.themoviedb.org/3/movie/top_rated?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US&page="+p);
            uMethod("https://api.themoviedb.org/3/discover/movie?api_key=e707c6ad620e69cda284fbbc6af06e43&language=us-US&region=US&release_date.gte="+modifiedDate+"&with_release_type=2|3&page="+p);
            nMethod("https://api.themoviedb.org/3/movie/now_playing?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US&page="+p);

        }




        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_movie);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_movie:

                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0, 0);
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
                }
                return false;
            }
        });



    }


}