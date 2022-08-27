package com.animasium.searchitcards.Scraper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.room.Dao;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovieScraper {

    private final String TAG = "Hindilinks4u";

    public String scraper(String name, String year) throws IOException {
        Log.d(TAG, "screaper Started");
        String link;
        String itemLink = null;
        String k = null;
        Connection.Response response = Jsoup.connect("https://hindilinks4u.icu/?s=" + name + " (" + year + ")")
                .timeout(100000)
                .execute();
        int statusCode = response.statusCode();
        Log.d(TAG, "screaper Started" + statusCode);
        if (statusCode == 200) {
            Document document = response.parse().ownerDocument();
            Log.d(TAG, "scraper: doc" + document);
            assert document != null;
            if (document.getElementsByClass("ml-item").get(0) !=null){
            Element elements = document.getElementsByClass("ml-item").get(0);
            Log.d(TAG, "scraper: elements " + elements);
            if (elements != null) {
                itemLink = elements.getElementsByTag("a").attr("href");
                Log.d(TAG, "scraper:itemLInk " + itemLink);
            }
            }else
                return null;
        }
//        else {
//            FancyToast.makeText(context,"VPN maybe required to get links", Toast.LENGTH_LONG,FancyToast.CONFUSING,false).show();
//            return null;
//        }


        if (itemLink != null) {

            Document doc = Jsoup.connect(itemLink).get();
            Element moviePlay = doc.getElementsByClass("movieplay").first();
            if (moviePlay != null) {
                link = moviePlay.getElementsByTag("iframe").attr("src");
                if (link.contains("speedostream")) {
                    Document speedo = Jsoup.connect(link).referrer("https://hindilinks4u.icu/").get();
                    Log.d(TAG, "scraper: playLInk" + speedo);
                    String as = speedo.toString();
                    k = printSubsInDelimiters(as);
                    Log.d(TAG, "scraper: playLInk" + k);

                } else {
                    k = link;
                    Log.d(TAG, "scraper: playLInk" + k);
                }
            }
        }
        return k;
    }

    public String printSubsInDelimiters(String str) {
        String r = null;
        Pattern pattern = Pattern.compile("file:\"(.*?)\"", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            r = (matcher.group(1));
        }
        return r;
    }
}
