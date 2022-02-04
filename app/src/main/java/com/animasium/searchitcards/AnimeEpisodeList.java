package com.animasium.searchitcards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.animasium.searchitcards.Anime.MainActivity;
import com.animasium.searchitcards.Anime.TopAnime;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnimeEpisodeList extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText editText;
    final String Constants = "https://www1.gogoanime.pe";
    private ArrayList<String> mEpisodeList = new ArrayList<>();
    HashMap<Integer, String> AnimeHash;
    private String link;
    private String animename;
    private ArrayList<String> mSiteLink = new ArrayList<>();
    private String id;
    int end;
    List<AnimePOJO> animePOJOList = new ArrayList<>();

    AnimeEpiListAdapter animeEpisodeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_episode_list);
        recyclerView = findViewById(R.id.xyza);

        editText = findViewById(R.id.episodeno);
        Button button = findViewById(R.id.episodeselector);
        AnimeHash = new HashMap<>();

        Intent intent = getIntent();
        link = intent.getStringExtra("animelink");
        id = intent.getStringExtra("episodes_id");

        Log.d("passId", "onCreate: "+link+", "+ id);

        StringBuilder b = new StringBuilder();
        for (int i = 0; i < link.length(); i++) {
            if (link.charAt(i) == 'y') {
                if (link.charAt(i + 1) == '/') {
                    for (int j = i + 2; j < link.length(); j++)
                        b.append(link.charAt(j));
                    break;
                }
            }

        }
        animename = b.toString();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!editText.getText().toString().equals("")) {
                    //=================================ads/////////////////////////////////////////

//                    if (UnityAds.isReady(intesitailid)) {
//                        UnityAds.show(com.example.searchitwatcher.selectEpisode.this, intesitailid);
//                    }
//
//
//                    int episodeno = Integer.parseInt(String.valueOf(editText.getText()));
//                    Intent intent = new Intent(getApplicationContext(), WatchVideo.class);
//                    intent.putExtra("link", mSiteLink.get(episodeno - 1));
//                    intent.putExtra("noofepisodes", String.valueOf(mEpisodeList.size()));
//                    AnimeDatabase database = AnimeDatabase.getInstance(getApplicationContext());
//                    Anime temp = database.animeDao().getAnimeByNameAndEpisodeNo(animenameforrecents, String.valueOf(episodeno));
//                    String time = "0";
//                    if (temp != null)
//                        time = temp.getTime();
//                    database.animeDao().deleteAnimeByNameAndEpisodeNo(animenameforrecents, String.valueOf(episodeno));
//                    Anime anime = new Anime(animenameforrecents, mSiteLink.get(episodeno - 1), String.valueOf(episodeno), imagelink, time);
//                    database.animeDao().insertAnime(anime);
//                    intent.putExtra("Multiquality", PassString);
//                    intent.putExtra("imagelink", imagelink);
//                    intent.putExtra("animename", animenameforrecents);
//                    intent.putExtra("animenames", animenameforrecents);
//                    intent.putExtra("camefrom", "selectepisode");
//
//                       Toast.makeText(getApplicationContext(),"condition",Toast.LENGTH_LONG).show();
//                    intent.putExtra("selectepisodelink", link);
//                    Log.i("linkPass", link);
//                    intent.putExtra("time", time);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
                    Intent i = new Intent(view.getContext(), AnimeWeb.class);
                    i.putExtra("AnimewatchID", animePOJOList.get(Integer.parseInt(editText.getText().toString())).getLink());
                    view.getContext().startActivity(i);

                } else {
                    editText.requestFocus();
                    editText.setError("Enter episode no first");
                }
            }
        });
        new SearchingEpisodes().execute();



    }


    @SuppressLint("StaticFieldLeak")
    private class SearchingEpisodes extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            linearLayout.setVisibility(View.GONE);
//            mRecyclerView = findViewById(R.id.xyza);
//            mRecyclerView.setVisibility(View.GONE);
//            plotsummary.setVisibility(View.GONE);
//            imageofanime.setVisibility(View.GONE);
//            bar = findViewById(R.id.loading);
//            bar.setVisibility(View.VISIBLE);

            getResponce();


        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document searching = Jsoup.connect(link).get();

                Elements li = searching.select("div[class=anime_video_body]").select("ul[id=episode_page]").select("li");
                String a = String.valueOf(li.select("a").eq(li.size() - 1).html());

                if (a.contains("-")) {
                    int index = a.indexOf('-');
                    end = Integer.parseInt(a.substring(index + 1));
                } else {
                    end = Integer.parseInt(a);
                }
                Log.d("episodesare", String.valueOf(end));

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i("asdsds", (AnimeHash) + "\n");

            if (end != 0)
                for (int i = 1; i <= end; i++) {

                    AnimePOJO animePOJO = new AnimePOJO();
                    String c = Constants + "/" + animename + "-episode-" + i;
                    Log.i("asdsds", c);
                    Log.i("asdsds", AnimeHash.get(i) + "");

                    if (AnimeHash.get(i) == null) {
                        animePOJO.setName(animename);
                    } else {
                        animePOJO.setName((String) AnimeHash.get(i));
                    }
                    animePOJO.setLink(c);
                    animePOJO.setEpisode(String.valueOf(i));
                    animePOJOList.add(animePOJO);
                    mEpisodeList.add(String.valueOf(i));
                    mSiteLink.add(c);
                }
            else {
                String c = Constants + "/" + animename + "-episode-" + 0;
                mEpisodeList.add(String.valueOf(0));
                Log.i("cis", c);
                mSiteLink.add(c);
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(AnimeEpisodeList.this, LinearLayoutManager.VERTICAL, false));
            animeEpisodeListAdapter = new AnimeEpiListAdapter(AnimeEpisodeList.this, animePOJOList,AnimeEpisodeList.this);
            recyclerView.setAdapter(animeEpisodeListAdapter);

            editText.setHint("Episode no between 1 to " + mEpisodeList.size());
            editText.setFilters(new InputFilter[]{
                    new InputFilterMinMax(1, mEpisodeList.size())
            })
            ;

            editText.requestFocus();
        }
    }

    private void getResponce() {
        String url = "https://api.jikan.moe/v4/anime/" + id + "/episodes";

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        int k = object.getInt("mal_id");
                        String v = object.getString("title");
                        Log.i("asdsds", k + "+" + v);
                        AnimeHash.put(k, v);
                    }

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