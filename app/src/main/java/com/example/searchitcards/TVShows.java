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
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.searchitcards.Movie.mAdapter.MovieAdapter;
import com.example.searchitcards.Movie.mAdapter.Movies;
import com.example.searchitcards.Movie.mAdapter.Show;
import com.example.searchitcards.Movie.mAdapter.ShowAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TVShows extends AppCompatActivity {
    RecyclerView TodayRecycle;
    RecyclerView UpcomigRecycle;//upcoming
    RecyclerView topRatedRecycle;
    RecyclerView popularRecycle;

    ShowAdapter showAdapter;
    List<Show> showList;
    List<Show> topRatedList;
    List<Show> AiringList;
    List<Show> TodayList;

    public void searchShows(View view){
        EditText searchText = findViewById(R.id.showQuery);
        String search = searchText.getText().toString();

        Intent myIntent = new Intent(this, MovieSearchResult.class);
        myIntent.putExtra("key-show", search); //Optional parameters
        this.startActivity(myIntent);
    }


    public void TopRated(String top){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray = response.getJSONArray("results");


                    Log.i("response",jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject search = jsonArray.getJSONObject(i);


                        Show topresults = new Show();
                        topresults.setName(search.getString("name"));
                        topresults.setId(search.getString("id"));
                        topresults.setPoster_path(search.getString("poster_path"));

                        topRatedList.add(topresults);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                topRatedRecycle.setLayoutManager(new LinearLayoutManager(TVShows.this,LinearLayoutManager.HORIZONTAL,false));
                showAdapter = new ShowAdapter(TVShows.this,topRatedList);
                topRatedRecycle.setAdapter(showAdapter);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

    public void TodayShow(String top){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray = response.getJSONArray("results");


                    Log.i("response",jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject search = jsonArray.getJSONObject(i);


                        Show topresults = new Show();
                        topresults.setName(search.getString("name"));
                        topresults.setId(search.getString("id"));
                        topresults.setPoster_path(search.getString("poster_path"));

                        TodayList.add(topresults);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                TodayRecycle.setLayoutManager(new LinearLayoutManager(TVShows.this,LinearLayoutManager.HORIZONTAL,false));
                showAdapter = new ShowAdapter(TVShows.this,TodayList);
                TodayRecycle.setAdapter(showAdapter);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

    public void Popular(String top){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray = response.getJSONArray("results");


                    Log.i("response",jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject search = jsonArray.getJSONObject(i);


                        Show topresults = new Show();
                        topresults.setName(search.getString("name"));
                        topresults.setId(search.getString("id"));
                        topresults.setPoster_path(search.getString("poster_path"));

                        showList.add(topresults);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                popularRecycle.setLayoutManager(new LinearLayoutManager(TVShows.this,LinearLayoutManager.HORIZONTAL,false));
                showAdapter = new ShowAdapter(TVShows.this,showList);
                popularRecycle.setAdapter(showAdapter);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

    public void UpcomingShows(String top){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray = response.getJSONArray("results");


                    Log.i("response",jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject search = jsonArray.getJSONObject(i);


                        Show topresults = new Show();
                        topresults.setPoster_path(search.getString("poster_path"));
                        topresults.setId(search.getString("id"));
                        topresults.setName(search.getString("name"));
//                            topresults.setScore(search.getInt("score"));
                    //    topresults.setRelease_date(search.getString("release_date"));
//                    topresults.setType(search.getString("type"));

                           // topresults.setRank((search.getInt("rank")));


                        showList.add(topresults);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                UpcomigRecycle.setLayoutManager(new LinearLayoutManager(TVShows.this,LinearLayoutManager.HORIZONTAL,false));
                showAdapter = new ShowAdapter(TVShows.this,showList);
                UpcomigRecycle.setAdapter(showAdapter);



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
        setContentView(R.layout.activity_t_v_shows);

        UpcomigRecycle = findViewById(R.id.Upcoming_show_recycle);
      topRatedRecycle = findViewById(R.id.top_rated_shows_recycle);
       popularRecycle = findViewById(R.id.popular_show_recycle);
       TodayRecycle = findViewById(R.id.todays_show_airing_recycle);
        showList = new ArrayList<>();
        topRatedList = new ArrayList<>();
        AiringList = new ArrayList<>();
        TodayList = new ArrayList<>();

        UpcomingShows("https://api.themoviedb.org/3/tv/on_the_air?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US&page=1");
        TopRated("https://api.themoviedb.org/3/tv/top_rated?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US&page=1");
    Popular("https://api.themoviedb.org/3/tv/popular?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US&page=1");
       TodayShow("https://api.themoviedb.org/3/tv/airing_today?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US&page=1");
        //https://api.themoviedb.org/3/tv/on_the_air?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US&page=1

//---------------------------------nav Bar-----------------------------------------------------------------------------------------------------------//

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_tv);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_movie:
                        startActivity(new Intent(getApplicationContext()
                                , Movies_activity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext()
                                ,MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_news:
                        startActivity(new Intent(getApplicationContext()
                                ,News_activity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_tv:

                        return true;
                }
                return false;
            }
        });

    }
}