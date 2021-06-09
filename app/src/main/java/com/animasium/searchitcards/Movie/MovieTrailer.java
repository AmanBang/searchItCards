package com.animasium.searchitcards.Movie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.animasium.searchitcards.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MovieTrailer extends YouTubeBaseActivity {
    YouTubePlayer.OnInitializedListener onInitializedListener;
    YouTubePlayerView youTubePlayerView;
    private YouTubePlayer youTubePlayer;
    String movieUrl ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_movie_trailer);


    youTubePlayerView = findViewById(R.id.movieYoutubeVideoPlay);

        Intent kal = getIntent();
       movieUrl = kal.getStringExtra("movie_trailer");

Log.i("helle",movieUrl);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.loadVideo(movieUrl);
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.i("responce", youTubeInitializationResult.toString());
                //Toast.makeText(AnimeDeatails.this, "ERROR:" + youTubeInitializationResult.toString(), Toast.LENGTH_LONG).show();
            }
        };

        youTubePlayerView.initialize("AIzaSyCXIXfoD0Vm50mL6f-Dsdax5-IhRGXIOds",onInitializedListener);
    }
}