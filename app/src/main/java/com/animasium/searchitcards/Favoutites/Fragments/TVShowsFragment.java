package com.animasium.searchitcards.Favoutites.Fragments;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.animasium.searchitcards.AlarmBroadCustReciver;
import com.animasium.searchitcards.Anime.AnimeShowMore;
import com.animasium.searchitcards.Favoutites.Favourites;
import com.animasium.searchitcards.R;
import com.animasium.searchitcards.RecommendedMovies;
import com.animasium.searchitcards.TVshows.showAdapter.ShowRAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static android.content.Context.ALARM_SERVICE;
import static com.animasium.searchitcards.App.CHANNEL_1;
import static com.parse.Parse.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TVShowsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TVShowsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //====================================================================================================================================================================//

    List<RecommendedMovies> showRecivedList;
    List<RecommendedMovies> showOngoing;
    List<RecommendedMovies> showWatched;
    ArrayList<String> dateFirst;
    ArrayList<String> dateLast;
    ShowRAdapter showRAdapter;
    RecyclerView pendingRecycleView;
    RecyclerView ongoingRecycleView;
    RecyclerView watchedRecycleView;
    String NotificationDate = "";
    List<String> IDtoNotify = null;
    List<String> IDtoUpdate = null;
    Bitmap ntfPicture;
    private NotificationManagerCompat notificationManager;
    String Episodetitle = "";
    String Episode = "";
    String Season = "";
    String Title = "";
    String ImageUrl = "";
    String IDsPass = "";
    String updatedDate = "";
    String dateNow = "";
    Date EpDate;
    int NValue = 1;
    Calendar cal;
    AlarmManager alarmManager1;
    HashMap<String, String> showNxtDate;
    SimpleDateFormat sdfo;
    ParseQuery<ParseObject> parseQuery;
    View addsomething;
    ProgressBar progressBar;

    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";

    ///=============================================Notification Areas ============================================================================================/

    public class ntf extends AsyncTask<Void, Void, Void> {
        String url1 = ImageUrl;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(url1);
                ntfPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                System.out.println(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i("BitMap", "Work Done");
            super.onPostExecute(aVoid);
        }
    }
    //==============================================================                         ==========================================================================================//

//    public void Recommend(String Urly) {
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, Urly, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try {
//                    JSONArray jsonArray = response.getJSONArray("results");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject object = jsonArray.getJSONObject(i);
//
//
//                        Log.i("Request Recommended", showRecivedList.toString());
//
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(objectRequest);
//    }

    //================================================================================================================================================================================//

    public TVShowsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TVShowsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TVShowsFragment newInstance(String param1, String param2) {
        TVShowsFragment fragment = new TVShowsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //============================================================================================================================================================================================================//
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_t_v_shows, container, false);

        pendingRecycleView = view.findViewById(R.id.FV_pending_recycleView);
        addsomething = view.findViewById(R.id.addSomething);
//        ongoingRecycleView = view.findViewById(R.id.FV_ongoing_recycleView);
//        watchedRecycleView = view.findViewById(R.id.FV_watched_recycleView);
        showRecivedList = new ArrayList<>();
        showOngoing = new ArrayList<>();
        showWatched = new ArrayList<>();
        IDtoNotify = new ArrayList<>();
        IDtoUpdate = new ArrayList<>();
        progressBar = view.findViewById(R.id.fav_progressbar);


        //Calender Code.............

//        cal = Calendar.getInstance();
//        cal.setTimeInMillis(System.currentTimeMillis());
//        cal.clear();

        //  cal.set(2021, 3, 19);
        parseQuery = new ParseQuery<ParseObject>("tShow");


//        getTodaysDate();
        //=============================================Date And Time============================================================================//

//========================================================================================================================================================//


//        notificationManager = NotificationManagerCompat.from(getApplicationContext());
//        showNxtDate = new HashMap<String, String>();


//        parseQuery.findInBackground(new FindCallback<ParseObject>() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if (e == null) {
//                    if (objects == null) {
//                        progressBar.setVisibility(View.GONE);
//                        addsomething.setVisibility(View.VISIBLE);
//                    } else {
//                        for (ParseObject parseObject : objects) {
//
//                            RecommendedMovies showReciver = new RecommendedMovies();
//                            String id = parseObject.get("showID") + "";
//                            showReciver.setId(id);
//                            showReciver.setPoster_path(parseObject.get("posterPath") + "");
//                            showReciver.setTitle(parseObject.get("showName") + "");
//                            Object dt = parseObject.get("nextEpDate");
////                        if (dt != null) {
////                            showNxtDate.put((id), dt.toString());
////                        }
//                        }
//                    }
//                    Log.i("ShowNext", showNxtDate + "");
//                    //======================================================================================================================================//
//
//                    //Sorting Dates in List..........
////                    sortDates();
//
//                    //Notification Date......
////                    try {
////                        NotificationDate = dateFirst.get(0);
////                        //Getting the Id from date...
////                        EpDate = sdfo.parse(NotificationDate);
////                        Log.i("EpDates", EpDate + "");
////
////                        if (dateNow.compareTo(String.valueOf(EpDate)) == 0 || dateNow.compareTo(String.valueOf(EpDate)) < 0) {
////                            Log.i("Episode Date", EpDate + "");
////
////                            cal.setTime(EpDate);
//////                            cal.
////
////                        }
////                    } catch (Exception e1) {
////                        e1.printStackTrace();
////                    }
//
////                    Log.i("TIME23", "TOBESend:" + (cal.getTimeInMillis() / 1000 - System.currentTimeMillis() / 1000));
////
////                    alarmManager1 = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
////
////                    for (String M : getKeys(showNxtDate, NotificationDate)) {
////                        IDtoNotify.add(M);
////                        Log.i("IND", IDtoNotify + "");
////                    }
//
//                    ////==============================================================================================================================////
//                } else {
//                    progressBar.setVisibility(View.GONE);
//                    addsomething.setVisibility(View.VISIBLE);
//                    e.printStackTrace();
//                }
////                new Thread(new Runnable() {
////                    @Override
////                    public void run() {
////
////                        ServiceNotify();
////                        SystemClock.sleep(2000);
////                    }
////                }).start();
//
//
//            }
//        });
        try {
//            parseQuery.whereMatches("type", "Ongoing");
//            parseQuery.findInBackground(new FindCallback<ParseObject>() {
//                @Override
//                public void done(List<ParseObject> objects, ParseException e) {
//
//                    if (e == null) {
//                        for (ParseObject parseObject : objects) {
//                            Log.i("recivedObjects", parseObject.get("showID") + "");
//
//                            RecommendedMovies showReciver = new RecommendedMovies();
//                            showReciver.setId(parseObject.get("showID") + "");
//                            showReciver.setPoster_path(parseObject.get("posterPath") + "");
//                            showReciver.setTitle(parseObject.get("showName") + "");
//
//                            showOngoing.add(showReciver);
//                            ongoingRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//                            showRAdapter = new ShowRAdapter(getContext(), showOngoing);
//                            ongoingRecycleView.setAdapter(showRAdapter);
//                        }
//                    } else {
//                        e.printStackTrace();
//                    }
//
//                }
//            });
//            parseQuery.whereMatches("type", "Pending");
            parseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    Log.i("recivedObjects", objects+"");

                    if (e == null) {
                        if (!objects.isEmpty()) {

                            for (ParseObject parseObject : objects) {
                                Log.i("recivedObjects", parseObject.get("showID") + "");
                                RecommendedMovies showReciver = new RecommendedMovies();
                                showReciver.setId(parseObject.get("showID") + "");
                                showReciver.setPoster_path(parseObject.get("posterPath") + "");
                                showReciver.setTitle(parseObject.get("showName") + "");
                                showRecivedList.add(showReciver);
                                try {
                                    LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 3);
                                    linearLayoutManager.setReverseLayout(false);
                                    pendingRecycleView.setLayoutManager(linearLayoutManager);
                                    showRAdapter = new ShowRAdapter(getContext(), showRecivedList);
                                    pendingRecycleView.setAdapter(showRAdapter);
                                    progressBar.setVisibility(View.GONE);
                                } catch (Exception z) {
                                    z.printStackTrace();
                                }
                            }


                        } else {
                            progressBar.setVisibility(View.GONE);
                            addsomething.setVisibility(View.VISIBLE);
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        addsomething.setVisibility(View.VISIBLE);
                        e.printStackTrace();
                    }

                }
            });

//            parseQuery.whereMatches("type", "Watched");
//            parseQuery.findInBackground(new FindCallback<ParseObject>() {
//                @Override
//                public void done(List<ParseObject> objects, ParseException e) {
//
//                    if (e == null) {
//                        for (ParseObject parseObject : objects) {
//                            Log.i("recivedObjects", parseObject.get("showID") + "");
//
//                            RecommendedMovies showReciver = new RecommendedMovies();
//                            showReciver.setId(parseObject.get("showID") + "");
//                            showReciver.setPoster_path(parseObject.get("posterPath") + "");
//                            showReciver.setTitle(parseObject.get("showName") + "");
//
//                            showWatched.add(showReciver);
//                            watchedRecycleView.setLayoutManager(new GridLayoutManager(getContext(), 3));
//
////                        watchedRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//                            showRAdapter = new ShowRAdapter(getContext(), showWatched);
//                            watchedRecycleView.setAdapter(showRAdapter);
//                        }
//                    } else {
//                        e.printStackTrace();
//                    }
//
//                }
//            });


        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private void sortDates() {
//
//        class DateItem {
//            final String datetime;
//
//            DateItem(String date) {
//                this.datetime = date;
//            }
//        }
//        class SortByDate implements Comparator<DateItem> {
//            @Override
//            public int compare(DateItem a, DateItem b) {
//                return a.datetime.compareTo(b.datetime);
//            }
//        }
//
//        dateFirst = new ArrayList<>();
//        dateLast = new ArrayList<>();
//
//        Collection<String> valueSet = showNxtDate.values();
//        List<DateItem> Dlist = new ArrayList<DateItem>();
//        for (String v : valueSet) {
//            Dlist.add(new DateItem(v));
//        }
//        Collections.sort(Dlist, new SortByDate());
//        Dlist.forEach(date -> {
//            Log.i("sorted", date.datetime);
//            if (dateNow.compareTo(String.valueOf(date.datetime)) == 0) {
//                dateFirst.add(date.datetime);
//                Log.i("Datefirst", date.datetime + "");
//            } else if (dateNow.compareTo(String.valueOf(date.datetime)) > 0) {
//                if (date.datetime != "") {
//                    dateLast.add(date.datetime);
//                }
//
//                Log.i("DateLast0", dateLast + "");
//            } else {
//                Log.i("asd", "asda");
//            }
//        });
//        if (dateLast != null) {
//            ServiceUpdatedDates();
//
//        }
//
//    }

//    public void getNotification(String content, int channelId) {
//        try {
//            Intent notificationIntent = new Intent(getApplicationContext(), AlarmBroadCustReciver.class);
//            Intent OpenActivity = new Intent(getApplicationContext(), Favourites.class);
//            PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, OpenActivity, 0);
//            Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1)
//                    .setContentTitle(Title)
//                    .setContentText(content)
//                    .setSmallIcon(R.drawable.ic_launcher_foreground)
//                    .setLargeIcon(ntfPicture)
//                    .setStyle(new NotificationCompat.BigPictureStyle()
//                            .bigPicture(ntfPicture)
//                            .bigLargeIcon(null))
//                    .setContentIntent(contentIntent)
//                    .setCategory(NotificationCompat.CATEGORY_EVENT)
//                    .build();
//
//            notificationIntent.putExtra(AlarmBroadCustReciver.NOTIFICATION_ID, channelId);
//            notificationIntent.putExtra(AlarmBroadCustReciver.NOTIFICATION, notification);
//            int dummyuniqueInt = new Random().nextInt(5430254);
//
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), dummyuniqueInt, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
//
//            assert alarmManager != null;
//            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

//    public static <K, V> Set<K> getKeys(Map<K, V> map, V value) {
//        Set<K> keys = new HashSet<>();
//        for (Map.Entry<K, V> entry : map.entrySet()) {
//            if (value.equals(entry.getValue())) {
//                keys.add(entry.getKey());
//            }
//        }
//        return keys;
//    }

//    public void ServiceNotify() {
//        Log.i("doneN", "service Called");
//        if (IDtoNotify != null) {
//            Log.i("doneN", "GotIDto Notify");
//
//            for (String IDs : IDtoNotify) {
//                IDsPass = IDs;
//
//                String url = "https://api.themoviedb.org/3/tv/" + IDs + "?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US";
//                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        try {
//                            Log.i("doneN", "Got Json responce");
//                            JSONObject nxtEpisode = response.getJSONObject("next_episode_to_air");
//                            Episodetitle = nxtEpisode.getString("name");
//                            Episode = nxtEpisode.getString("episode_number");
//                            Season = nxtEpisode.getString("season_number");
//                            Title = response.getString("name");
//                            Log.i("EPisodeName", Title);
//                            JSONObject lstEpisode = response.getJSONObject("last_episode_to_air");
//                            ImageUrl = "https://image.tmdb.org/t/p/w500" + lstEpisode.getString("still_path");
//                            new ntf().execute();
//                            Log.i("ImageURL", ImageUrl);
//                            NValue = NValue + 1;
//                            Log.i("NVal", NValue + "");
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//                try {
//                    queue.add(request);
//                    Log.i("doneN", "Notfying");
//                    SystemClock.sleep(2000);
//                    getNotification("Episode " + Episode + " of Season " + Season + " is Releasing today.", NValue);
//                    Log.i("doneN", "Notification Started");
//                    SystemClock.sleep(2000);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//
//
//            }
//
//        }
//        IDtoNotify.clear();
//    }

//    public void ServiceUpdatedDates() {
//        if (dateLast != null) {
//            Log.i("doneU", "checked If");
//
//            for (String dates : dateLast) {
//
//                IDtoUpdate.addAll(getKeys(showNxtDate, dates));
//
//                Log.i("doneU", IDtoUpdate + "");
//
//                for (String id : IDtoUpdate) {
//                    Log.i("UpdateDate", "for loop started");
//                    Log.i("UpdateDate", id);
//
//                    String url1 = "https://api.themoviedb.org/3/tv/" + id + "?api_key=e707c6ad620e69cda284fbbc6af06e43&language=en-US";
//                    RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());
//                    JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
//                        @SuppressLint("SetTextI18n")
//                        @Override
//                        public void onResponse(JSONObject response) {
//
//                            try {
//                                Log.i("UpdateDate", "Resposnce Generated");
//                                JSONObject nxtEpisode = response.getJSONObject("next_episode_to_air");
//                                updatedDate = nxtEpisode.getString("air_date");
//                                Log.i("UpdateDate", "air Generated " + nxtEpisode.getString("air_date"));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//                        }
//                    });
//                    queue1.add(request1);
//                    SystemClock.sleep(500);
//                    ParseQuery<ParseUser> userParseQuery = ParseQuery.getQuery("tShow");
//                    userParseQuery.whereMatches("showID", id);
//                    userParseQuery.findInBackground(new FindCallback<ParseUser>() {
//                        @Override
//                        public void done(List<ParseUser> objects, ParseException e) {
//                            if (e == null) {
//                                ParseObject user = objects.get(0);
//                                user.put("nextEpDate", updatedDate);
//                                user.saveInBackground();
//
//                            } else {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                }
//                SystemClock.sleep(500);
//                System.out.println("Date1 is after Date2");
//            }
//        }
//
//        IDtoUpdate.clear();
//    }


//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void getTodaysDate() {
//        sdfo = new SimpleDateFormat("yyyy-MM-dd");
//
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDateTime now = LocalDateTime.now();
//        String adsd = dtf.format(now);
//
//        try {
//            dateNow = sdfo.format(sdfo.parse(adsd));
//        } catch (java.text.ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println();
//        Log.i("DateNow", dateNow + "");
//
//
//    }
}












