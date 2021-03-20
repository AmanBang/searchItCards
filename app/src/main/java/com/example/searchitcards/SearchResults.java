package com.example.searchitcards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class SearchResults extends AppCompatActivity {

    SearchShow Sea;

    RecyclerView searchRecycle;
    List<Topresults> searchResultList;
    private EditText edtSearchText;
    Button btnSearch;
ProgressBar progressBar;

    SearchMethod searchMethod;
    public class SearchMethod extends Thread {


    public SearchMethod(String top) {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
              //  Log.i("Popular",response.toString());

                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                     //Log.i("Popular",jsonArray.toString());
                    for (int i = 0; i < 20; i++) {
                        JSONObject Searchshow = jsonArray.getJSONObject(i);


                        Topresults searchIdk = new Topresults();
                        searchIdk.setTitle(Searchshow.getString("title"));
//                        searchIdk.setScore(Searchshow.getInt("score"));
                       // searchIdk.setEpisode(Searchshow.getInt("episodes"));
                        searchIdk.setType(Searchshow.getString("type"));
                        searchIdk.setMal_id(Searchshow.getInt("mal_id"));
                        // upcomingResults.setRank((upcomingSearch.getInt("rank")));
                        searchIdk.setImage_url(Searchshow.getString("image_url"));

                        searchResultList.add(searchIdk);
                        Log.i("Popular",searchResultList.toString());

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                searchRecycle.setLayoutManager(new LinearLayoutManager(SearchResults.this));
           //     searchRecycle.setLayoutManager(new GridLayoutManager(searchResultList,3));
               Sea = new SearchShow(SearchResults.this, searchResultList);
               searchRecycle.setAdapter(Sea);
                progressBar.setVisibility(View.GONE);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchResults.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);
//

    }
}
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Intent intent = getIntent();
        String value = intent.getStringExtra("search_url");

        edtSearchText = findViewById(R.id.edtSearch);
progressBar = findViewById(R.id.resultProgressBar);

        String Url = "https://api.jikan.moe/v3/search/anime?page=1&q=" + value;

        edtSearchText.setText(value);

        searchRecycle = findViewById(R.id.search_result_recycle);
        searchResultList = new ArrayList<>();
         searchMethod = new SearchMethod(Url);
        searchMethod.start();


        progressBar.setVisibility(View.VISIBLE);
    }

    public void setBtnSearch(View view){
    progressBar.setVisibility(View.VISIBLE);
        if (!edtSearchText.getText().toString().equals("")){

            searchResultList.clear();
            String Url = "https://api.jikan.moe/v3/search/anime?page=1&q=" + edtSearchText.getText().toString();
            searchMethod = new SearchMethod(Url);
            searchMethod.start();

        }

    }
}