package com.example.searchitcards;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class News_activity extends AppCompatActivity {

   private WebView webView;

    private TextView showName;
    private Toolbar toolbar;
    private TabAdapter tabAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_news_activity);
//
//        webView = findViewById(R.id.webview);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("https://www.animenewsnetwork.com/");
//
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setSafeBrowsingEnabled(true);

        toolbar = findViewById(R.id.myToolbar);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_news);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_movie:
                        startActivity(new Intent(getApplicationContext()
                                ,Movies_activity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_news:

                        return true;
                    case R.id.nav_dashboard:
                        startActivity(new Intent(getApplicationContext()
                                , DashboarduSER.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

    }

//    @Override
//    public void onBackPressed() {
//        if (webView.canGoBack()){
//            webView.goBack();
//        }else {
//            super.onBackPressed();
//        }
//
//    }
}