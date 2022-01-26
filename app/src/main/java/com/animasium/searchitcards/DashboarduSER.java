package com.animasium.searchitcards;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.animasium.searchitcards.Anime.MainActivity;
import com.animasium.searchitcards.Favoutites.Favourites;
import com.animasium.searchitcards.Movie.Movies_activity;
import com.animasium.searchitcards.Movie.mAdapter.MovieAdapter;
import com.animasium.searchitcards.Movie.mAdapterclasses.Movies;
import com.animasium.searchitcards.TVshows.TVShows;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.animasium.searchitcards.App.CHANNEL_1;

public class DashboarduSER extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

//    TextView Name;
//    Bitmap ntfPicture;
//    private NotificationManagerCompat notificationManager;
//    private RewardedAd mRewardedAd;
//    private AdView mAdView;
//    Button adsBtn;
//    TextView earning;
//    ParseQuery<ParseUser> parseQuery;
//    int pt;
//    int ear;
//    Button streamBtn;
//    public static Bitmap getBitmapFromURL(String src) {
//        try {
//            URL url = new URL(src);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            return myBitmap;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//    private String unityGameID = "4157281";
//    private Boolean testMode = true;
//    private String surfacingId = "DashBoard";
//    private Button rewardedButton;


//    public class ntf extends AsyncTask<Void, Void, Void> {
//        String url1 = "https://media.kitsu.io/manga/poster_images/14916/small.jpg?1491099787";
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            try {
//                URL url = new URL(url1);
//                ntfPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            } catch (IOException e) {
//                System.out.println(e);
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            Log.i("sucess", "Work Done");
//            super.onPostExecute(aVoid);
//        }
//    }

//    public void notifyBtn(View view) {
//
//        Intent OpenActivity = new Intent(this, MainActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, OpenActivity, 0);
//
//
//        //ntfPicture = BitmapFactory.decodeResource(getResources(),R.drawable.pain);
//        //  ;
//        // ntfPicture = BitmapFactory.decodeFile(Picasso.get().load("https://media.kitsu.io/episodes/thumbnails/28/original.jpg?1416327614"))
//        // ntfPicture = BitmapFactory.
//        // ntfPicture = getBitmapFromURL("https://media.kitsu.io/episodes/thumbnails/28/original.jpg?1416327614");
//
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1)
//                .setSmallIcon(R.drawable.ic_smicon)
//                .setContentTitle("MY HERO ACADAMIA")
//                .setLargeIcon(ntfPicture)
//                .setStyle(new NotificationCompat.BigPictureStyle()
//                        .bigPicture(ntfPicture)
//                        .bigLargeIcon(null))
//                .setContentText("New Episode of My hero acdamia is released")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_EVENT)
//                .setContentIntent(contentIntent)
//                .build();
//
//        notificationManager.notify(1, notification);
//
//
//    }
//==============================================================================================================================================================//

    RecyclerView liveTV;
    LiveTVAdapter liveTVAdapter;
    List<Channel> channelList;
    ProgressBar progressBar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
progressBar = findViewById(R.id.dashboard_progressbar);
//        adsBtn = findViewById(R.id.ADS_button);
//        earning = findViewById(R.id.Rewardsads);
//        rewardedButton = findViewById(R.id.UnityAds);
//        parseQuery = new ParseQuery<ParseUser>("Earning");
//
//
//        Name = findViewById(R.id.UserNameDisplay);
//        notificationManager = NotificationManagerCompat.from(this);
//
//        new ntf().execute();
//
//        parseQuery.findInBackground(new FindCallback<ParseUser>() {
//            @Override
//            public void done(List<ParseUser> objects, ParseException e) {
//                if (e == null) {
//                    Name.setText(ParseUser.getCurrentUser().getUsername() + " Wellcome to search it");
//                    for (ParseObject object : objects) {
//                        pt = Integer.parseInt(object.get("income")+"");
//                        earning.setText(object.get("income") + "");
//                    }
//                } else {
//                    Log.d("tp", "asd");
//                }
//            }
//        });
//
//        final UnityAdsListener myAdsListener = new UnityAdsListener();
//        //Add the listener to the SDK:
//        UnityAds.addListener(myAdsListener);
//        // Initialize the SDK:
//        UnityAds.initialize(this, unityGameID, testMode);
//
//        // Find the button in the view hierarchy and set its click function to show ads:
//
//        rewardedButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DisplayRewardedAd();
//                rewardedButton.setEnabled(false);
//            }
//        });
//
//        streamBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
////======================================================ads=============================================================================================//
//        mAdView = findViewById(R.id.adView);
//
//
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//        RewardedAd.load(DashboarduSER.this, "ca-app-pub-6544805297981669/3497754430",
//                adRequest, new RewardedAdLoadCallback() {
//                    @Override
//                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                        // Handle the error.
//                        Log.d("TAG", loadAdError.getMessage());
//                        Toast.makeText(getApplicationContext(), loadAdError.getMessage(), Toast.LENGTH_LONG).show();
//                        mRewardedAd = null;
//                    }
//
//                    @Override
//                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
//                        mRewardedAd = rewardedAd;
//                        Log.d("TAG", "Ad was loaded.");
//                        adsBtn.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplicationContext(), "Ad was loaded.", Toast.LENGTH_LONG).show();
//                    }
//                });
//
//
//        adsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//                    @Override
//                    public void onAdShowedFullScreenContent() {
//                        // Called when ad is shown.
//                        Log.d("TAG", "Ad was shown.");
//                        Toast.makeText(getApplicationContext(), "Ad was shown.", Toast.LENGTH_LONG).show();
//                        mRewardedAd = null;
//
//                    }
//
//                    @Override
//                    public void onAdFailedToShowFullScreenContent(AdError adError) {
//                        // Called when ad fails to show.
//                        Log.d("TAG", "Ad failed to show.");
//                        Toast.makeText(getApplicationContext(), "Ad failed to show.", Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onAdDismissedFullScreenContent() {
//                        // Called when ad is dismissed.
//                        // Don't forget to set the ad reference to null so you
//                        // don't show the ad a second time.
//                        Log.d("TAG", "Ad was dismissed.");
//                        Toast.makeText(getApplicationContext(), "Ad was dismissed..", Toast.LENGTH_LONG).show();
//                        recreate();
//
//                    }
//                });
//                if (mRewardedAd != null) {
//                    Activity activityContext = DashboarduSER.this;
//                    mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
//                        @Override
//                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
//                            // Handle the reward.
//                            //earning.setText((Integer.parseInt((String) earning.getText()) + rewardItem.getAmount()));
//
//                            String rewardType = rewardItem.getType();
//                            parseQuery.whereEqualTo("UserName", ParseUser.getCurrentUser().getUsername());
//                            parseQuery.findInBackground(new FindCallback<ParseUser>() {
//                                @Override
//                                public void done(List<ParseUser> objects, ParseException e) {
//                                    if (e == null) {
//                                        ear = pt+1;
//                                        if (objects.isEmpty()) {
//                                            ParseObject object = new ParseObject("Earning");
//                                            object.put("income", ear + "");
//                                            object.put("UserName", ParseUser.getCurrentUser().getUsername());
//                                            object.saveInBackground();
//                                        } else {
//                                            for (ParseObject parseObject : objects) {
//                                                parseObject.put("income", ear + "");
//                                                parseObject.saveInBackground();
//                                            }
//                                        }
//                                    } else {
//                                        Log.d("tp", "asd");
//                                    }
//                                }
//                            });
//
//
//                        }
//                    });
//                } else {
//                    Log.d("TAG", "The rewarded ad wasn't ready yet.");
//                    Toast.makeText(getApplicationContext(), "The rewarded ad wasn't ready yet", Toast.LENGTH_LONG).show();
//                }
//
//
//            }
//        });
//=====================================================================================================================================================//

        liveTV = findViewById(R.id.live_recycle);
        channelList = new ArrayList<>();
            String apilink = "https://api.npoint.io/3467798e35da38adfc47";
        getLiveChannel(apilink);


//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
////        bottomNavigationView.setSelectedItemId(R.id.nav_dashboard);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.nav_dashboard:
//
//                        return true;
//                    case R.id.nav_movie:
//                        startActivity(new Intent(getApplicationContext()
//                                , Movies_activity.class));
//                        overridePendingTransition(0, 0);
//                        finish();
//                        return true;
//
//                    case R.id.nav_home:
//                        startActivity(new Intent(getApplicationContext()
//                                , MainActivity.class));
//                        overridePendingTransition(0, 0);
//                        finish();
//                        return true;
//                    case R.id.nav_news:
//                        startActivity(new Intent(getApplicationContext()
//                                , Favourites.class));
//                        overridePendingTransition(0, 0);
//                        finish();
//                        return true;
//                    case R.id.nav_tv:
//                        startActivity(new Intent(getApplicationContext()
//                                , TVShows.class));
//                        overridePendingTransition(0, 0);
//                        finish();
//                        return true;
//
//                }
//                return false;
//            }
//        });
    }

    private void getLiveChannel(String top) {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, top, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray = response.getJSONArray("liveChannel");


                    Log.i("response",jsonArray.toString());
                    for (int i = 0; i <= jsonArray.length(); i++) {
                        JSONObject search = jsonArray.getJSONObject(i);


                        Channel topresults = new Channel();
                        topresults.setName(search.getString("name"));
                        topresults.setLogo(search.getString("logo"));
                        topresults.setUrl(search.getString("url"));

                        channelList.add(topresults);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                liveTV.setLayoutManager(new GridLayoutManager(DashboarduSER.this,2));
                liveTVAdapter =new LiveTVAdapter(DashboarduSER.this,channelList);
                liveTV.setAdapter(liveTVAdapter);
                progressBar.setVisibility(View.GONE);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

//    public void DisplayRewardedAd() {
//        if (UnityAds.isReady(surfacingId)) {
//            UnityAds.show(this, surfacingId);
//        }
//    }

    // Implement the IUnityAdsListener interface methods:
//    private class UnityAdsListener implements IUnityAdsListener {
//
//        public void onUnityAdsReady(String surfacingId) {
//            // Implement functionality for an ad being ready to show.
//            Toast.makeText(getApplicationContext(), "Unity Ads is ready To Show", Toast.LENGTH_SHORT).show();
//            rewardedButton.setEnabled(true);
////            rewardedButton.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        public void onUnityAdsStart(String s) {
//
//        }
//
//        @Override
//        public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {
//            // Implement conditional logic for each ad completion status:
//            if (finishState.equals(UnityAds.FinishState.COMPLETED)) {
//                // Reward the user for watching the ad to completion.
//                Toast.makeText(getApplicationContext(), "UserEarned The reward Unity", Toast.LENGTH_SHORT).show();
//
//                int ear = Integer.parseInt((String) earning.getText()) + 2;
//
//                parseQuery.whereMatches("UserName", ParseUser.getCurrentUser().getUsername());
//                parseQuery.findInBackground(new FindCallback<ParseUser>() {
//                    @Override
//                    public void done(List<ParseUser> objects, ParseException e) {
//                        if (e == null) {
//                            if (objects.isEmpty()) {
//                                ParseObject object = new ParseObject("Earning");
//                                object.put("income", ear + "");
//                                object.put("UserName", ParseUser.getCurrentUser().getUsername());
//                                object.saveInBackground();
//                            } else if (!objects.isEmpty()) {
//                                for (ParseObject parseObject : objects) {
//                                    parseObject.put("income", ear + "");
//                                    parseObject.saveInBackground();
//                                }
//                            }
//
//                        } else {
//                            Log.d("tp", "asd");
//                        }
//                    }
//                });
//                try {
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            recreate();
//                        }
//                    }, 100);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else if (finishState.equals(UnityAds.FinishState.SKIPPED)) {
//                // Do not reward the user for skipping the ad.
//                Toast.makeText(getApplicationContext(), "Ad Was skipped", Toast.LENGTH_SHORT).show();
//
//            } else if (finishState.equals(UnityAds.FinishState.ERROR)) {
//                // Log an error.
//                Toast.makeText(getApplicationContext(), "Error Loading AD", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        @Override
//        public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {
//
//        }
//
//
//    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}