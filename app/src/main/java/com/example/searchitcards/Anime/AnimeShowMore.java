package com.example.searchitcards.Anime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.searchitcards.Anime.AdapterCLass.AShowMore;
import com.example.searchitcards.Anime.AdapterCLass.SMAdapter;
import com.example.searchitcards.Anime.AdapterCLass.Topresults;
import com.example.searchitcards.Movie.DeatailsMovie;
import com.example.searchitcards.Movie.mAdapter.AdapterRMovies;
import com.example.searchitcards.R;
import com.example.searchitcards.RecommendedMovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AnimeShowMore extends AppCompatActivity {
String Url;
RecyclerView recyclerView;
List<AShowMore> showList;
    SMAdapter adapterRMovies;

    GridLayoutManager manager;

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


                            AShowMore anime = new AShowMore();
                            anime.setTitle(search.getString("title"));
                            anime.setId(search.getString("mal_id"));
                            anime.setPoster_path(search.getString("image_url"));
                            showList.add(anime);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    recyclerView.setLayoutManager(new GridLayoutManager(AnimeShowMore.this,3));
                    adapterRMovies = new SMAdapter(AnimeShowMore.this, showList);
                    recyclerView.setAdapter(adapterRMovies);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(request);
        }

    }


//    private void loadData() {
////        searchResultList.add(null);
////        Sea.notifyItemInserted(searchResultList.size() - 1);
//        if (i<=  Integer.parseInt(lastPage)) {
//
//
//            loadMoreProgressBar.setVisibility(View.VISIBLE);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
////
////                searchResultList.remove(searchResultList.size() - 1);
////                Sea.notifyItemRemoved(searchResultList.size());
//                    Log.i("Working:", "True2");
//                    String Url = "https://api.jikan.moe/v3/search/anime?q=" + edtSearchText.getText().toString() + "&page=" + i;
//
//                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            try {
//                                lastPage = response.getString("last_page");
//                                JSONArray jsonArray = response.getJSONArray("results");
//                                for (int i = 0; i < 20; i++) {
//                                    JSONObject Searchshow = jsonArray.getJSONObject(i);
//
//
//                                    Topresults searchIdk = new Topresults();
//                                    searchIdk.setTitle(Searchshow.getString("title"));
//                                    searchIdk.setType(Searchshow.getString("type"));
//                                    searchIdk.setMal_id(Searchshow.getInt("mal_id"));
//                                    searchIdk.setImage_url(Searchshow.getString("image_url"));
//                                    searchIdk.setScore(Searchshow.getDouble("score"));
//                                    searchResultList.add(searchIdk);
//                                    Sea.notifyDataSetChanged();
//                                    loadMoreProgressBar.setVisibility(View.GONE);
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(AnimeSearchResults.this, error.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    });
//                    queue.add(request);
//
//                }
//            }, 2000);
//        }
//    }




    //=====================================================================================================================================//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_show_more);


        Intent intent = getIntent();
        Url = intent.getStringExtra("key_for_more");
       String Name = intent.getStringExtra("title_SM");
        Log.i("URLPass",Url);

        setTitle(Name);

        showList = new ArrayList<>();
        recyclerView = findViewById(R.id.RCV_animeShowMore);

        HomeView homeView = new HomeView(Url);
        homeView.start();

    }
}