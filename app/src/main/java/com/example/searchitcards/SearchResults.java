package com.example.searchitcards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
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
    ProgressBar progressBar, loadMoreProgressBar;
    private String value;
    SearchMethod searchMethod;

    private boolean isScrolled = false;
    int totalItem, visibleItems, scrolledItems;
    LinearLayoutManager manager;
    private String lastPage;
    private int i = 1;


    public class SearchMethod extends Thread {


        public SearchMethod(String top) {

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //  Log.i("Popular",response.toString());

                    try {


                        lastPage = response.getString("last_page");
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
                            searchIdk.setScore(Searchshow.getDouble("score"));
                            searchResultList.add(searchIdk);
                            Log.i("Popular", searchResultList.toString());

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    searchRecycle.setLayoutManager(new LinearLayoutManager(SearchResults.this));
                    //     searchRecycle.setLayoutManager(new GridLayoutManager(searchResultList,3));
                    Sea = new SearchShow(SearchResults.this, searchResultList);
                    searchRecycle.setAdapter(Sea);
                    searchRecycle.setLayoutManager(manager);
                    progressBar.setVisibility(View.GONE);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SearchResults.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            queue.add(request);
//

        }
    }

    private void loadData() {
//        searchResultList.add(null);
//        Sea.notifyItemInserted(searchResultList.size() - 1);
if (i<=  Integer.parseInt(lastPage)) {


    loadMoreProgressBar.setVisibility(View.VISIBLE);
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
//
//                searchResultList.remove(searchResultList.size() - 1);
//                Sea.notifyItemRemoved(searchResultList.size());
            Log.i("Working:", "True2");
            String Url = "https://api.jikan.moe/v3/search/anime?q=" + edtSearchText.getText().toString() + "&page=" + i;

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        lastPage = response.getString("last_page");
                        JSONArray jsonArray = response.getJSONArray("results");
                        for (int i = 0; i < 20; i++) {
                            JSONObject Searchshow = jsonArray.getJSONObject(i);


                            Topresults searchIdk = new Topresults();
                            searchIdk.setTitle(Searchshow.getString("title"));
                            searchIdk.setType(Searchshow.getString("type"));
                            searchIdk.setMal_id(Searchshow.getInt("mal_id"));
                            searchIdk.setImage_url(Searchshow.getString("image_url"));
                            searchIdk.setScore(Searchshow.getDouble("score"));
                            searchResultList.add(searchIdk);
                            Sea.notifyDataSetChanged();
                            loadMoreProgressBar.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SearchResults.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            queue.add(request);

        }
    }, 2000);
}
    }

//  OnLoadMoreListener onLoadMoreListener = new OnLoadMoreListener() {
//        @Override
//        public void onLoadMore() {
//            //Here adding null object to last position,check the condition in getItemViewType() method,if object is null then display progress
//            dataModels.add(null);
//            adapter.notifyItemInserted(dataModels.size() - 1);
//
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    dataModels.remove(dataModels.size() - 1);
//                    adapter.notifyItemRemoved(dataModels.size());
//
//                    // Call you API, then update the result into dataModels, then call adapter.notifyDataSetChanged().
//                    //Update the new data into list object
//                    loadData();
//                    adapter.notifyDataSetChanged();
//                    loading = false;
//                }
//            }, 1000);
//        }
//    };

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Intent intent = getIntent();
        value = intent.getStringExtra("search_url");

        edtSearchText = findViewById(R.id.edtSearch);
        progressBar = findViewById(R.id.resultProgressBar);
        loadMoreProgressBar = findViewById(R.id.loadmoreProgressBar);
        manager = new LinearLayoutManager(this);
        String Url = "https://api.jikan.moe/v3/search/anime?page=1&q=" + value;

        edtSearchText.setText(value);

        searchRecycle = findViewById(R.id.search_result_recycle);
        searchResultList = new ArrayList<>();
        searchMethod = new SearchMethod(Url);
        searchMethod.start();


        progressBar.setVisibility(View.VISIBLE);

        searchRecycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                if (isScrolled && (visibleItems + scrolledItems == totalItem)) {
                    isScrolled = false;
                    loadData();
                }
            }
        });


    }

    public void setBtnSearch(View view) {
        progressBar.setVisibility(View.VISIBLE);
        if (!edtSearchText.getText().toString().equals("")) {
            i = 1;
            totalItem = scrolledItems = visibleItems = 0;
            searchResultList.clear();
            String Url = "https://api.jikan.moe/v3/search/anime?page=1&q=" + edtSearchText.getText().toString();
            searchMethod = new SearchMethod(Url);
            searchMethod.start();

        }

    }


}



