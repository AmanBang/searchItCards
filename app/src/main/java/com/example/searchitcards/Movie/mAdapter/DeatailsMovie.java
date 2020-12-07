package com.example.searchitcards.Movie.mAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.searchitcards.Anime.Adapter.YoutubeVideoAdapter;

import com.example.searchitcards.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DeatailsMovie extends AppCompatActivity {


    TextView mTitle;
    TextView description;
    TextView releaseDate;
    TextView shortDeatails;
    ImageView poster;
    String id;


    RecyclerView trailerRecycle;
    TrailerAdapter trailerAdapter;
    List<Trailer> trailerList;


    public class MoviesDetails extends Thread{
        public MoviesDetails(String src) {

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, src, null, new Response.Listener<JSONObject>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        mTitle.setText(response.getString("title"));
                        String des ="";
                     des += "popularity : " + response.getString("popularity") +"\n";
                     des += "status : " + response.getString("status") +"\n";
                     des += "Rating : " + String.valueOf(response.getInt("vote_average")) +"\n";
                        shortDeatails.setText(des);
                        releaseDate.setText("Release-Date :"+response.getString("release_date"));
                        description.setText(response.getString("overview"));
                        Picasso.get().load("https://image.tmdb.org/t/p/w500"+response.getString("poster_path")).into(poster);


                        //Log.i("title", string);
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

        }
    }

    public void trailerFetch(String src){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, src, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray mJ = response.getJSONArray("results");
                    for (int g= 0;g<mJ.length();g++){
                        JSONObject object = mJ.getJSONObject(g);
                        Trailer trailer = new Trailer();
                        Log.i("trailer",object.getString("key"));
                    trailer.setKey(object.getString("key"));
                    trailer.setName(object.getString("name"));
                    trailer.setType(object.getString("type"));
                    trailerList.add(trailer);
                    }


                    //Log.i("title", string);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                trailerRecycle.setLayoutManager(new LinearLayoutManager(DeatailsMovie.this,LinearLayoutManager.HORIZONTAL,false));
                trailerAdapter = new TrailerAdapter(DeatailsMovie.this,trailerList);
                trailerRecycle.setAdapter(trailerAdapter);

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
        setContentView(R.layout.activity_deatails_movie);

        poster = findViewById(R.id.posterMovieView);
        mTitle = findViewById(R.id.movies_title);
        releaseDate = findViewById(R.id.airDateMovie);
        description = findViewById(R.id.MoviesDetails);
        shortDeatails = findViewById(R.id.short_details_movie);

        trailerList = new ArrayList<>();
        trailerRecycle = findViewById(R.id.movies_Video);




        Intent intent = getIntent();
        Intent jio = getIntent();

             id = intent.getStringExtra("movieId");
            String id2  = jio.getStringExtra("searchToDetails");

if(id == null){
    String videoUrl = "https://api.themoviedb.org/3/movie/"+id2+"/videos?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US";

    String url = "https://api.themoviedb.org/3/movie/"+id2+"?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US";
    MoviesDetails moviesDetails = new MoviesDetails(url);
    moviesDetails.start();
    trailerFetch(videoUrl);

}else {

    String videoUrl = "https://api.themoviedb.org/3/movie/"+id+"/videos?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US";

    String url = "https://api.themoviedb.org/3/movie/"+id+"?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US";
    MoviesDetails moviesDetails = new MoviesDetails(url);
    moviesDetails.start();
    trailerFetch(videoUrl);
}

    }
}