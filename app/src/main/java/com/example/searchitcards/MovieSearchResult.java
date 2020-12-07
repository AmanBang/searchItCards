package com.example.searchitcards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieSearchResult extends AppCompatActivity {

    RecyclerView showRecycleView;
    MovieShowAdapter mSA;
    List<Show> showList;

    public void showMovieResult(String url){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray movieArray = response.getJSONArray("results");

                    for (int i = 0;i<movieArray.length();i++){
                        JSONObject object = movieArray.getJSONObject(i);

                        Show mov = new Show();
                        mov.setTitle(object.getString("title"));
                        mov.setPoster_path(object.getString("poster_path"));
                        mov.setVote_average(object.getInt("vote_average"));
                        if (String.valueOf(object.getInt("vote_average")).equals("0")){
                            mov.setRelease_date("Upcoming");
                        }else{
                            mov.setRelease_date(object.getString("release_date"));
                        }
                       //
                        mov.setId(object.getString("id"));

                        showList.add(mov);
                        Log.i("sjshdk",object.getString("id"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                showRecycleView.setLayoutManager(new LinearLayoutManager(MovieSearchResult.this));
                mSA = new MovieShowAdapter(MovieSearchResult.this,showList);
                showRecycleView.setAdapter(mSA);




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search_result);


        showRecycleView = findViewById(R.id.movie_search_result_recycle);
        showList = new ArrayList<>();

        Intent mac = getIntent();
        String query = mac.getStringExtra("query");




String url = "https://api.themoviedb.org/3/search/movie?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US&page=1&query="+query+"&include_adult=true";
        Log.i("sjshdk",url);
showMovieResult(url);



    }
}