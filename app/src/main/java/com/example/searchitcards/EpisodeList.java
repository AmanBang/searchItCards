package com.example.searchitcards;

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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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


    Spinner spinner;

    LinearLayoutManager mLayoutManager;
    private boolean loading = true;

    List<Integer> page;
boolean first1= true;

LinearLayout pageLayout;
    Handler handler;



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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_list);

        episodeRecycle = findViewById(R.id.episode_list_recycle_hello);
        episodeList = new ArrayList<>();
        cardView = findViewById(R.id.episode_cardView);
        filler = findViewById(R.id.filler);
        page = new ArrayList<>();
        spinner= findViewById(R.id.episode_page_spinner);
//        filler.setVisibility(View.INVISIBLE);
        spinner.setOnItemSelectedListener(this);
        pageLayout = findViewById(R.id.page_layout);

        Intent i = getIntent();
        eUrl = i.getStringExtra("episodeList_1");

        getList(eUrl + "/episodes");

         handler= new Handler();


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() ==  R.id.episode_page_spinner){
//           String p = String.valueOf();
//           Log.i("pod",position+"");
           getList(eUrl + "/episodes/"+(position+1));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}















