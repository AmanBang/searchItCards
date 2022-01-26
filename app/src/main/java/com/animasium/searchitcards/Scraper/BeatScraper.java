package com.animasium.searchitcards.Scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class BeatScraper {

    public String scraper(String movieName) throws NullPointerException, IOException {
        String link = "";
        try {
            Document html = Jsoup.connect("https://www.hindimoviestv.net/?s="+movieName).get();

            Element movieTitle = html.getElementsByClass("ml-item").first();

           link = movieTitle.getElementsByTag("a").attr("href");

        }catch (NullPointerException e){
            e.printStackTrace();
        }




        if (!link.isEmpty()){
            Document html2 = Jsoup.connect(link).get();

            Element movieplay = html2.getElementsByClass("movieplay").first();

            String streaminglink = movieplay.getElementsByTag("iframe").attr("src");
            return streaminglink;
        }else {
            return  null;
        }



    }
}
