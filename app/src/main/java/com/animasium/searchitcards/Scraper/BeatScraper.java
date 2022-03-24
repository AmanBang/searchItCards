package com.animasium.searchitcards.Scraper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class BeatScraper {

    public String scraper(String movieName) throws NullPointerException, IOException {
        String link = "";
        try {
            Connection.Response response = Jsoup.connect("https://www.hindimoviestv.net/?s=" + movieName)
                    .timeout(10000)
                    .execute();

            int statusCode = response.statusCode();

            if (statusCode == 200) {
                Document html = response.parse().ownerDocument();
                Element movieTitle = html.getElementsByClass("ml-item").first();

                link = movieTitle.getElementsByTag("a").attr("href");
            }else {
                return null;
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        if (!link.isEmpty()) {
            Document html2 = Jsoup.connect(link).get();

            Element movieplay = html2.getElementsByClass("movieplay").first();

            String streaminglink = movieplay.getElementsByTag("iframe").attr("src");
            return streaminglink;
        } else {
            return null;
        }


    }
}
