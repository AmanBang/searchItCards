package com.example.searchitcards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.searchitcards.Anime.Adapter.AdapterClass.RecommendedAnime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EpisodeList extends AppCompatActivity {

    RecyclerView episodeRecycle;
    EpisodeAdpater episodeAdpater;
    List<Episode> episodeList;

    CardView cardView;
    int color;
    TextView filler;
    JSONObject object;

    public void getList(String Urly){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, Urly, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("episodes");
                    for (int i = 0; i < jsonArray.length(); i++) {
                         object = jsonArray.getJSONObject(i);
                        Episode anime = new Episode();
                        anime.setEpisode_id(object.getString("episode_id"));
                        anime.setAired(object.getString("aired"));
                        anime.setTitle(object.getString("title"));
                        if (object.getBoolean("filler")) {
                            anime.setFiller(" Filler ");
                        }else {
                            Log.i("sda","asda");
                        }

                        episodeList.add(anime);



                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }




                episodeRecycle.setLayoutManager(new LinearLayoutManager(EpisodeList.this));
                episodeAdpater = new EpisodeAdpater(EpisodeList.this, episodeList);
                episodeRecycle.setAdapter(episodeAdpater);


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
        setContentView(R.layout.activity_episode_list);

        episodeRecycle = findViewById(R.id.episode_list_recycle);
        episodeList = new ArrayList<>();
        cardView = findViewById(R.id.episode_cardView);
        filler = findViewById(R.id.filler);
//        filler.setVisibility(View.INVISIBLE);

        Intent i = getIntent();
        String eUrl = i.getStringExtra("episodeList_1");

        color = Color.parseColor("#ec5858");
        Log.i("helloe",eUrl);

        getList(eUrl+"/episodes");



    }
}