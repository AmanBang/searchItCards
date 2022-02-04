package com.animasium.searchitcards.Favoutites;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.animasium.searchitcards.Anime.MainActivity;
import com.animasium.searchitcards.DashboarduSER;
import com.animasium.searchitcards.Login.LoginActivity;
import com.animasium.searchitcards.Movie.Movies_activity;
import com.animasium.searchitcards.R;
import com.animasium.searchitcards.TVshows.TVShows;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Favourites extends AppCompatActivity {

   private WebView webView;

    private TextView showName;
    private Toolbar toolbar;
    private TabAdapter tabAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    ProgressDialog progressDialog;
    Button logOut;
    private AdView mAdView;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.favourite_activity);

        setTitle("Favourite");
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });

//        mAdView = findViewById(R.id.Favourites_ads);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

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

        progressDialog = new ProgressDialog(this);




        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_news);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_movie:
                        startActivity(new Intent(getApplicationContext()
                                , Movies_activity.class));
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


                    case R.id.nav_tv:
                        startActivity(new Intent(getApplicationContext()
                                , TVShows.class));
                        overridePendingTransition(0, 0);

                        return true;

                }
                return false;
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu,menu);

        return super.onCreateOptionsMenu(menu);
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout_user_icon){
            progressDialog.show();
            progressDialog.setMessage("Loging Out...");
            ParseUser.logOutInBackground(e -> {
                progressDialog.dismiss();
                if (e == null)
                    showAlert("So, you're going...", "Ok...Bye-bye then",false);
            });
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlert(String title, String message, boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Favourites.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    // don't forget to change the line below with the names of your Activities
                    if (!error) {
                        Intent intent = new Intent(Favourites.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
        FancyToast.makeText(this,"LogIn",FancyToast.LENGTH_LONG,FancyToast.INFO,false);

    }
}