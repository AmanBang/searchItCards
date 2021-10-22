package com.animasium.searchitcards;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.monstertechno.adblocker.AdBlockerWebView;
import com.monstertechno.adblocker.util.AdBlocker;
import com.unity3d.services.ads.webplayer.WebPlayerView;

public class WebView extends AppCompatActivity {

//    private android.webkit.WebView webPlayerView;
    String id;
    String Imdb;

    private android.webkit.WebView webView;
    private FrameLayout customViewContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private View mCustomView;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_web_view);



        Intent i = getIntent();
       id =  i.getStringExtra("watchID");

        customViewContainer = (FrameLayout) findViewById(R.id.customViewContainer);
        webView = findViewById(R.id.ASZ);
//        new AdBlockerWebView.init(this).initializeWebView(webView);
//        webView.setWebViewClient(new Browser_home());
//        webView.setWebChromeClient(new MyWebClient());
//        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        webView.getSettings().setSafeBrowsingEnabled(true);
        webView.getSettings().setBlockNetworkImage(true);
//        webView.getSettings().getForceDark();
        webView.loadUrl(id);


//        webView = findViewById(R.id.ASZ);
//        customViewContainer = findViewById(R.id.customViewContainer);
//        webView.getSettings().setJavaScriptEnabled(true);
//        WebSettings settings = webView.getSettings();
//        webView.getWebChromeClient();
//        settings.setSafeBrowsingEnabled(true);
//        settings.setJavaScriptCanOpenWindowsAutomatically(false);
//        settings.setBlockNetworkImage(true);
//        settings.setLoadsImagesAutomatically(false);
//        webView.setVerticalScrollBarEnabled(false);
//        webView.setHorizontalScrollBarEnabled(false);

    }
    private class Browser_home extends WebViewClient {

        Browser_home() {}

        @SuppressWarnings("deprecation")
        @Override
        public WebResourceResponse shouldInterceptRequest(android.webkit.WebView view, String url) {

            return AdBlockerWebView.blockAds(view,url) ? AdBlocker.createEmptyResource() :
                    super.shouldInterceptRequest(view, url);

        }

    }

    class Browser extends WebViewClient
    {
        Browser() {}


    }

    public class MyWebClient
            extends WebChromeClient
    {

        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        public MyWebClient() {}

        public void onHideCustomView()
        {
            ((FrameLayout)WebView.this.getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            WebView.this.getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            WebView.this.setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = WebView.this.getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = WebView.this.getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)WebView.this.getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            WebView.this.getWindow().getDecorView().setSystemUiVisibility(3846);
        }

        public boolean shouldOverrideUrlLoading(android.webkit.WebView paramWebView, String paramString)
        {
            paramWebView.loadUrl(paramString);
            return true;
        }

    }
    //
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}