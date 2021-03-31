package com.example.searchitcards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.searchitcards.Anime.AdapterCLass.Topresults;
import com.example.searchitcards.Anime.AdapterCLass.WantMCLass;
import com.example.searchitcards.Anime.AnimeSearchResults;
import com.example.searchitcards.Anime.AnimeShowMore;
import com.example.searchitcards.Movie.mAdapter.AdapterRMovies;
import com.example.searchitcards.Movie.mAdapter.ShowAdapter;
import com.example.searchitcards.TVshows.Show;
import com.example.searchitcards.TVshows.TVShows;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TMShowMore extends AppCompatActivity {

    RecyclerView recyclerView;
    List<WantMCLass> lists12;
    TMSMAdpter adapter;

    Boolean isScrolling = false;
    int i = 1, vissibleItem, totalItem, scrolledItem;
    GridLayoutManager manager;
    String lastPage;
    String Url;
    ProgressBar TM_loadMoreProgressBar;
    Boolean isMovie = false;
    String UrlM;
    String Urlw;

    public void TopRated(String top){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    lastPage = response.getString("total_pages");
                    JSONArray jsonArray = response.getJSONArray("results");


                    Log.i("response",jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject search = jsonArray.getJSONObject(i);


                        WantMCLass getshows  = new WantMCLass();
                        if (isMovie){
                            getshows.setTitle(search.getString("title"));
                            getshows.setMovie(true);
                        }else {
                            getshows.setTitle(search.getString("name"));
                            getshows.setMovie(false);
                        }
                        getshows.setId(search.getString("id"));
                        getshows.setPoster_path(search.getString("poster_path"));

                        lists12.add(getshows);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("erroeor",e.getMessage());
                }

                recyclerView.setLayoutManager(new GridLayoutManager(TMShowMore.this,3));
                adapter = new TMSMAdpter(TMShowMore.this,lists12);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);



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
        setContentView(R.layout.activity_t_m_show_more);

        recyclerView = findViewById(R.id.TM_recycle);
        lists12 = new ArrayList<>();
        TM_loadMoreProgressBar = findViewById(R.id.TM_loadMoreProgressBar);

        Intent intent = getIntent();
        Intent movie = getIntent();
         Url = intent.getStringExtra("key_175");
         UrlM = movie.getStringExtra("key_21");

         if (Url == null){
             isMovie = true;
             TopRated(UrlM);
         }else{
             isMovie = false;
             TopRated(Url);
         }

        String title = intent.getStringExtra("title_shows");
        manager = new GridLayoutManager(this,3);
      setTitle(title);

        TopRated(Url);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                    i = i+1;
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItem = manager.getItemCount();
                scrolledItem = manager.findFirstVisibleItemPosition();
                vissibleItem = manager.getChildCount();
                if (isScrolling && (vissibleItem + scrolledItem == totalItem)) {
                    isScrolling = false;
                    loadData();
                }
            }
        });
    }

    private void loadData() {

        if (i <= Integer.parseInt(lastPage)){
            TM_loadMoreProgressBar.setVisibility(View.VISIBLE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//
//                searchResultList.remove(searchResultList.size() - 1);
//                Sea.notifyItemRemoved(searchResultList.size());
                    Log.i("Working:", "True2");
                    if (isMovie){
                        Urlw = UrlM +"&page="+ i;
                    }else {
                         Urlw = Url + "&page="+ i;
                    }

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Urlw, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                JSONArray jsonArray = response.getJSONArray("results");


                                Log.i("response",jsonArray.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject search = jsonArray.getJSONObject(i);


                                    WantMCLass tmsmAdpter= new WantMCLass();
                                    tmsmAdpter.setPoster_path(search.getString("poster_path"));
                                    tmsmAdpter.setId(search.getString("id"));
                                    if (isMovie){
                                        tmsmAdpter.setTitle(search.getString("title"));
                                        tmsmAdpter.setMovie(true);
                                    }else {
                                        tmsmAdpter.setTitle(search.getString("name"));
                                        tmsmAdpter.setMovie(false);
                                    }
                                    lists12.add(tmsmAdpter);
                                    adapter.notifyDataSetChanged();
                                    TM_loadMoreProgressBar.setVisibility(View.GONE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(TMShowMore.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                    queue.add(request);

                }
            }, 1000);

        }


    }
}
















