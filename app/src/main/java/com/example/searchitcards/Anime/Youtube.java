package com.example.searchitcards.Anime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.searchitcards.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class Youtube extends YouTubeBaseActivity {
    YouTubePlayer.OnInitializedListener onInitializedListener;
    YouTubePlayerView youTubePlayerView;
    private YouTubePlayer youTubePlayer;
    String pass ="";
    String url;


    public void youtubeID(String urls){

        for (int l= 30;l<=40;l++){
            char c = urls.charAt(l);
            pass = pass + c;}
        Log.i("pass",pass);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_youtube);

   youTubePlayerView = findViewById(R.id.youtubeActivity);

        Intent kali = getIntent();
            url = kali.getStringExtra("youtube_url");
            Log.i("pass",kali.getStringExtra("youtube_url"));
            youtubeID(url);

//
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.loadVideo(pass);
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.i("responce", youTubeInitializationResult.toString());
                //Toast.makeText(AnimeDeatails.this, "ERROR:" + youTubeInitializationResult.toString(), Toast.LENGTH_LONG).show();
            }
        };

        youTubePlayerView.initialize("AIzaSyCXIXfoD0Vm50mL6f-Dsdax5-IhRGXIOds",onInitializedListener);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        youTubePlayer.release();
//    }
}