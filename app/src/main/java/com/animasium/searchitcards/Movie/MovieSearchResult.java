package com.animasium.searchitcards.Movie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
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
import com.animasium.searchitcards.Movie.mAdapter.MovieShowAdapter;
import com.animasium.searchitcards.Movie.mAdapterclasses.MSearchResultConst;
import com.animasium.searchitcards.R;
import com.animasium.searchitcards.TVshows.showAdapter.showSearchAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieSearchResult extends AppCompatActivity {

    RecyclerView showRecycleView;
    MovieShowAdapter mSA;
    showSearchAdapter searchAdapter;
    List<MSearchResultConst> showList;
    List<MSearchResultConst> List;
//====================================================================================//
    private EditText edtSearchText;
    Button btnSearch;
    ProgressBar progressBar, loadMoreProgressBar;
    private String value;
//    AnimeSearchResults.SearchMethod searchMethod;

    private boolean isScrolled = false;
    int totalItem, visibleItems, scrolledItems;
    LinearLayoutManager manager;
    LinearLayoutManager manage;

    private String lastPage;
    private int i = 1;

    boolean isMovie = true;
//=======================================================================================//
    public void showMovieResult(String url){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    lastPage = response.getString("total_pages");
                    Log.i("lastPage",lastPage);
                    JSONArray movieArray = response.getJSONArray("results");
                    for (int i = 0;i<movieArray.length();i++){
                        JSONObject object = movieArray.getJSONObject(i);

                        MSearchResultConst mov = new MSearchResultConst();
                       mov.setTitle(object.getString("title"));
                        mov.setPoster_path(object.getString("poster_path"));
                        try {
                            mov.setVote_average(object.getDouble("vote_average"));
                            if (String.valueOf(object.getDouble("vote_average")).equals("0")){
                                mov.setRelease_date("N/A");
                            }else{
                                mov.setRelease_date(object.getString("release_date"));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }


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
                showRecycleView.setLayoutManager(manager);
                progressBar.setVisibility(View.GONE);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);

    }

    public void shows(String url){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    lastPage = response.getString("total_pages");

                    JSONArray movieArray = response.getJSONArray("results");
                    Log.i("LastPage",lastPage);
                    for (int i = 0;i<movieArray.length();i++){
                        JSONObject object = movieArray.getJSONObject(i);

                        MSearchResultConst mov = new MSearchResultConst();
                        mov.setId(object.getString("id"));
                       mov.setTitle(object.getString("name"));
                        mov.setPoster_path(object.getString("poster_path"));
                         mov.setVote_average(object.getDouble("vote_average"));
//                        if (String.valueOf(object.getInt("vote_average")).equals("0")){
//                            mov.setRelease_date("N/A");
//                        }else{
                         //   mov.setRelease_date(object.getString("release_date"));
                      //  }



                        showList.add(mov);
                        Log.i("sjshdk",object.getString("id"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                showRecycleView.setLayoutManager(new LinearLayoutManager(MovieSearchResult.this));
                searchAdapter = new showSearchAdapter(MovieSearchResult.this,showList);
                showRecycleView.setAdapter(searchAdapter);
                showRecycleView.setLayoutManager(manager);
                progressBar.setVisibility(View.GONE);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);

    }
    private void loadData() {
//        searchResultList.add(null);
//        Sea.notifyItemInserted(searchResultList.size() - 1);
        if (i<=  Integer.parseInt(lastPage)) {

            Log.i("Working2:", "True");

            loadMoreProgressBar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//
//                searchResultList.remove(searchResultList.size() - 1);
//                Sea.notifyItemRemoved(searchResultList.size());
                    Log.i("Working:", "True2");
                    if (isMovie){

                        String url = "https://api.themoviedb.org/3/search/movie?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US&page="+i+"&query="+edtSearchText.getText().toString()+"&include_adult=true";


                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONArray movieArray = response.getJSONArray("results");

                                    for (int i = 0;i<movieArray.length();i++){
                                        JSONObject object = movieArray.getJSONObject(i);

                                        MSearchResultConst mov = new MSearchResultConst();
                                        mov.setTitle(object.getString("title"));
                                        mov.setPoster_path(object.getString("poster_path"));
                                        mov.setVote_average(object.getDouble("vote_average"));
                                        if (String.valueOf(object.getDouble("vote_average")).equals("0")){
                                            mov.setRelease_date("N/A");
                                        }else{
                                            mov.setRelease_date(object.getString("release_date"));
                                        }

                                        mov.setId(object.getString("id"));

                                        showList.add(mov);
                                        mSA.notifyDataSetChanged();
                                        loadMoreProgressBar.setVisibility(View.GONE);

                                        Log.i("sjshdk",object.getString("id"));
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        queue.add(request);
                    }else {
                        String Url = "https://api.themoviedb.org/3/search/tv?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US&page=" + i + "&query=" + edtSearchText.getText().toString();
                        // String Url = "https://api.jikan.moe/v3/search/anime?q=" + edtSearchText.getText().toString() + "&page=" + i;
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray movieArray = response.getJSONArray("results");

                                    for (int i = 0;i<movieArray.length();i++){
                                        JSONObject object = movieArray.getJSONObject(i);

                                        MSearchResultConst mov = new MSearchResultConst();
                                        mov.setId(object.getString("id"));
                                        mov.setTitle(object.getString("name"));
                                        mov.setPoster_path(object.getString("poster_path"));
                                        mov.setVote_average(object.getInt("vote_average"));

                                        showList.add(mov);
                                        searchAdapter.notifyDataSetChanged();
                                        loadMoreProgressBar.setVisibility(View.GONE);
                                        Log.i("sjshdk",object.getString("id"));
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MovieSearchResult.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                        queue.add(request); }


                }
            }, 2000);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search_result);

        Intent mac = getIntent();
        String query = mac.getStringExtra("key-123");
        String queryShow = mac.getStringExtra("key-show");

//        Intent intent = getIntent();
//        value = intent.getStringExtra("search_url");

        edtSearchText = findViewById(R.id.M_edtSearch);
        progressBar = findViewById(R.id.M_resultProgressBar);
        loadMoreProgressBar = findViewById(R.id.M_loadmoreProgressBar);
        manager = new LinearLayoutManager(getApplicationContext());
        manage = new LinearLayoutManager(MovieSearchResult.this);

        //        String Url = "https://api.jikan.moe/v3/search/anime?page=1&q=" + value;

//        InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        showRecycleView = findViewById(R.id.movie_search_result_recycle);
        showList = new ArrayList<>();
        List = new ArrayList<>();


if (query == null){
    isMovie = false;
    String url1 = "https://api.themoviedb.org/3/search/tv?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US&page=1&query="+queryShow;

    shows(url1);
    edtSearchText.setText(queryShow);
}else {
    isMovie = true;
    String url = "https://api.themoviedb.org/3/search/movie?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US&page=1&query="+query+"&include_adult=true";
    Log.i("sjshdk",url);
    edtSearchText.setText(query);
    showMovieResult(url);
}
        progressBar.setVisibility(View.VISIBLE);
        edtSearchText.setOnEditorActionListener(exampleListener);

        showRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolled = true;
                    i = i + 1;
                    Log.i("Working:", "True");
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItems = manager.getChildCount();
                totalItem = manager.getItemCount();
                scrolledItems = manager.findFirstVisibleItemPosition();
//                Log.i("Working:visibleItems:",visibleItems+"" );
//                Log.i("Working:totalItem:",totalItem+"" );
//                Log.i("Working:scrolledItems:",scrolledItems+"" );

                if (isScrolled && (visibleItems + scrolledItems == totalItem)) {
                    isScrolled = false;
                    loadData();
                }
            }
        });



    }
    public void M_setBtnSearch(View view) {

        searchclick();
}
    TextView.OnEditorActionListener exampleListener = new TextView.OnEditorActionListener(){

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH
            ) {
               searchclick();

            }
            return true;
        }
    };

    public void searchclick(){
        InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        if (!edtSearchText.getText().toString().equals("")) {
            progressBar.setVisibility(View.VISIBLE);
            i = 1;
            totalItem = scrolledItems = visibleItems = 0;
            showList.clear();
            //   String Url = "https://api.jikan.moe/v3/search/anime?page=1&q=" + edtSearchText.getText().toString();

            if (isMovie){
                String url = "https://api.themoviedb.org/3/search/movie?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US&page=1&query="+edtSearchText.getText().toString()+"&include_adult=true";
                showMovieResult(url);
            }else {
                String url1 = "https://api.themoviedb.org/3/search/tv?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US&page=1&include_adult=true&query="+edtSearchText.getText().toString();
                shows(url1);
            }

        }
    }
}