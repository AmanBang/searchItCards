package com.animasium.searchitcards.Anime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.animasium.searchitcards.AnimeShowEPList.Episode;
import com.animasium.searchitcards.AnimeShowEPList.EpisodeAdpater;
import com.animasium.searchitcards.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EpisodeList extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    RecyclerView episodeRecycle;
    EpisodeAdpater episodeAdpater;
    List<Episode> episodeList;

    CardView cardView;
    int color;
    TextView filler;
    JSONObject object;
    int numb = 0;
    int k=1;
    int lastPage;
    String eUrl;

    TextView episdoeDetails;
    ImageView episodeImage;

    ProgressBar progressBar;

    Spinner spinner;
    Spinner spinner2;

    LinearLayoutManager mLayoutManager;
    private final boolean loading = true;

    List<Integer> page;
    List<Integer> seasons;
boolean first1= true;

LinearLayout pageLayout;
LinearLayout seasonsLayout;
    Handler handler;
    String seasonNumber;
    String eUrl2;
    private String sea ="1";

    public void getList(String Urly){

          episodeList.clear();
          RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
          RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
          JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, Urly, null, new Response.Listener<JSONObject>() {
              @SuppressLint("SetTextI18n")
              @Override
              public void onResponse(JSONObject response) {

                  try {
                      lastPage = response.getInt("episodes_last_page");
                      JSONArray jsonArray = response.getJSONArray("episodes");
                      for (int i = 0; i < jsonArray.length(); i++) {
                          object = jsonArray.getJSONObject(i);
                          Episode anime = new Episode();
                          anime.setEpisode_id(object.getString("episode_id"));
                          anime.setAired(object.getString("aired"));
                          anime.setTitle(object.getString("title"));
                          if (object.getBoolean("filler")) {
                              anime.setFiller(" Filler ");
                          } else {
                              Log.i("sda", "asda");
                          }

                          episodeList.add(anime);


                      }


                  } catch (JSONException e) {
                      e.printStackTrace();
                  }


                  if (lastPage == 1){
                      pageLayout.setVisibility(View.GONE);
                  }

                  if (first1){
                      for (int m=1;m<=lastPage;m++){
                          page.add(m);
                      }
                      ArrayAdapter<Integer> integerArrayAdapter = new ArrayAdapter<Integer>(EpisodeList.this,
                              android.R.layout.simple_gallery_item,page);
                      integerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                      spinner.setAdapter(integerArrayAdapter);
                      first1 = false;
                  }else {
                      Log.i("sd","asda");
                  }

                  handler.postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          episodeRecycle.setLayoutManager(new LinearLayoutManager(EpisodeList.this));
                          episodeAdpater = new EpisodeAdpater(EpisodeList.this, episodeList);
                          episodeRecycle.setAdapter(episodeAdpater);
                          progressBar.setVisibility(View.GONE);
                      }
                  },200);



              }
          }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {

              }
          });
          requestQueue.add(objectRequest);

      }

    public void getList2(String Urly){

        episodeList.clear();
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

                        anime.setAired(object.getString("air_date"));
                        anime.setTitle(object.getString("name"));
                        anime.setEpisodeImage(object.getString("still_path"));
                        anime.setEpisodeDetails(object.getString("overview"));
                        anime.setEpisode_id(object.getString("episode_number"));
                        anime.setShowID(eUrl2);
                        anime.setSeason(sea);
                        episodeList.add(anime);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                if (lastPage == 1){
//                    pageLayout.setVisibility(View.GONE);
//                }
//

//
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        episodeRecycle.setLayoutManager(new LinearLayoutManager(EpisodeList.this));
                        episodeAdpater = new EpisodeAdpater(EpisodeList.this, episodeList);
                        episodeRecycle.setAdapter(episodeAdpater);
                        progressBar.setVisibility(View.GONE);
                    }
                },500);



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

        episodeImage = findViewById(R.id.episode_image);
        episdoeDetails = findViewById(R.id.episode_details);
        progressBar = findViewById(R.id.episodelist_probressBar);


        episodeRecycle = findViewById(R.id.episode_list_recycle_hello);
        episodeList = new ArrayList<>();
        cardView = findViewById(R.id.episode_cardView);
        filler = findViewById(R.id.filler);
        page = new ArrayList<>();
        seasons = new ArrayList<>();
        spinner= findViewById(R.id.episode_page_spinner);
        spinner2= findViewById(R.id.episode_season_spinner);

//        filler.setVisibility(View.INVISIBLE);
     spinner.setOnItemSelectedListener(this);
     spinner2.setOnItemSelectedListener(this);
        pageLayout = findViewById(R.id.page_layout);
        seasonsLayout = findViewById(R.id.season_layout);

        Intent i = getIntent();
        eUrl = i.getStringExtra("episodeList_1");
         eUrl2 = i.getStringExtra("episodeList_2");
         seasonNumber = i.getStringExtra("season_Numbers");

//60574
        if (eUrl !=null){

            seasonsLayout.setVisibility(View.GONE);
            getList(eUrl + "/episodes");
        }else {
            pageLayout.setVisibility(View.GONE);

            String k = "https://api.themoviedb.org/3/tv/"+eUrl2+"/season/1?api_key=e707c6ad620e69cda284fbbc6af06e43";
            getList2(k);
            Log.i("sd",k);


            if (first1){
                for (int m=1;m<=Integer.parseInt(seasonNumber);m++){
                    seasons.add(m);
                }
                ArrayAdapter<Integer> integerArrayAdapter = new ArrayAdapter<Integer>(EpisodeList.this,
                        android.R.layout.simple_gallery_item,seasons);
                integerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(integerArrayAdapter);
                first1 = false;
            }else {
                Log.i("sd","asda");
            }
        }


         handler= new Handler();


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId() ==  R.id.episode_page_spinner){
//           String p = String.valueOf();
//           Log.i("pod",position+"");
            if (eUrl !=null){
                getList(eUrl + "/episodes/"+(position+1));
            }

        }
        if(parent.getId() ==  R.id.episode_season_spinner){
            episodeList.clear();
            if ((position+1) != 1){
                String m = "https://api.themoviedb.org/3/tv/"+eUrl2+"/season/"+(position+1)+"?api_key=e707c6ad620e69cda284fbbc6af06e43";
//                Log.i("hell",(position+1)  + "");
                sea = position+1+"";
                getList2(m);
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}















