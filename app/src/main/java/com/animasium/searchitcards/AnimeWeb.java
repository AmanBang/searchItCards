package com.animasium.searchitcards;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class AnimeWeb extends AppCompatActivity {

    String vidStreamUrl;
    String link;
    private String animeName;
    int episodeNumber;
    String tempGogoAnimeLink;
    //=============================================================================================================================================================//
    private WebView webView;
    private FrameLayout customViewContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    ProgressBar progressBar;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_web);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        link = getIntent().getStringExtra("AnimewatchID");
        Log.i("linkis", link);
        progressBar = findViewById(R.id.bufferAnime);
        webView = findViewById(R.id.AnimeWebview);

        webView.getSettings().setJavaScriptEnabled(true);
        WebSettings settings = webView.getSettings();
        webView.getWebChromeClient();
        settings.setSafeBrowsingEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setBlockNetworkImage(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            settings.setForceDark(WebSettings.FORCE_DARK_AUTO);
        }
        settings.setLoadsImagesAutomatically(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);

        new ScrapeVideoLink(link,this).execute();
    }

    @SuppressLint("StaticFieldLeak")
    class ScrapeVideoLink extends AsyncTask<Void, Void, Void> {
        String gogoAnimeUrl;
        Context context;

        ScrapeVideoLink(String gogoAnimeUrl, Context context) {
            this.gogoAnimeUrl = gogoAnimeUrl;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tempGogoAnimeLink = gogoAnimeUrl;
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            webView.loadUrl(vidStreamUrl);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Document gogoAnimePageDocument = null;


            try {
                gogoAnimePageDocument = Jsoup.connect(link).get();
//                gogoAnimePageDocument = Jsoup.connect(link).referrer()
                vidStreamUrl = "https:" + gogoAnimePageDocument.getElementsByClass("play-video").get(0).getElementsByTag("iframe").get(0).attr("src");
                Log.i("Linktoplay", vidStreamUrl);


            } catch (Exception e) {
                Log.i("gogoanimeerror", e.toString());
            }
            return null;
        }


    }
}