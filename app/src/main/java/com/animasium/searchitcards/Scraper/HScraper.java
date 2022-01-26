package com.animasium.searchitcards.Scraper;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HScraper {

    public List<String> Hscraper(String movieName) throws NullPointerException, IOException {
        String link = "";
        String separator = ":";
        String mN = "";
       List<String> linkList = new ArrayList<>();
        if (movieName.contains(separator)) {

//            mN = "Spider-Man – No Way Home";
            mN = movieName.replace(separator," –");
            Log.d("elementHinf", "Hscraper: " + mN);
        } else {
            mN = movieName;
            Log.d("elementHinf", "else:Hscraper: " + mN);
        }
        try {
            Document html = Jsoup.connect("https://hindilinks4u.click/?s=" + mN).get();
            Element element = html.select("a:contains(" + mN + ")").first();
            link = element.attr("href");
            Log.d("elementHinf", element + "");

        } catch (NullPointerException e) {
            e.printStackTrace();
        }



        if (!link.isEmpty()){
            Document html2 = Jsoup.connect(link).get();

            Element movieplay = html2.select("a:contains(Server 4)").first();
            Element movieplay1 = html2.select("a:contains(Server 1)").first();

            String streaminglink = movieplay.attr("href");
            String streaminglink1 = movieplay1.attr("href");
            linkList.add(streaminglink);
            linkList.add(streaminglink1);
            Log.d("elementHinf",streaminglink+", "+streaminglink1);
            return linkList;
        }else {
            return  null;
        }


    }
}
