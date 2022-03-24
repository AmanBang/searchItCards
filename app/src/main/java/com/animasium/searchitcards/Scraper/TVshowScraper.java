package com.animasium.searchitcards.Scraper;

import android.util.Log;

import androidx.room.Dao;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import static android.content.ContentValues.TAG;

public class TVshowScraper {


    public String scraper(String id,String season, String episode) throws IOException {
        Log.d("linkaspa", "scraper: "+id+"/"+season+"/"+episode);
        Document document = Jsoup.connect("https://getsuperembed.link/?video_id="+id+"&tmdb=1&season="+season+"&episode="+episode).get();
        String link = document.body().text();
        return link;
    }

    public TVshowScraper() throws IOException {
    }
}
