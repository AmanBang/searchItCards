package com.animasium.searchitcards.Scraper;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.animasium.searchitcards.SeasonListPOJO;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HindiTVShowScraper {



    private static final String TAG = "HindiTVShow";

    public List<SeasonListPOJO> scraper(String name) throws IOException {

        List<SeasonListPOJO> PassList = new ArrayList<>();
        List<String> foundLinks = new ArrayList<>();
        List<String> seasonName = new ArrayList<>();
        List<String> playLink = new ArrayList<>();
        Connection.Response response = Jsoup.connect("https://hindilinks4u.ws/?s=" + name)
                .timeout(10000)
                .execute();
        int statusCode = response.statusCode();
        if (statusCode == 200) {
            Log.d(TAG, "scraper: " + name);
            Document document = response.parse().ownerDocument();
            if (document != null) {

                Elements elements = document.getElementsByClass("ml-item");
                if (elements != null) {
                    for (Element e : elements) {
                        String checkName = e.getElementsByTag("a").get(0).attr("oldtitle");
//                        Log.d(TAG, "scraper: " + checkName);
                        if (checkName.contains(name) && checkName.contains("Hindi Dubbed") && checkName.contains("Season")) {
                            Log.d(TAG, "scraper forloop: " + e.getElementsByTag("a").get(0).attr("oldtitle"));
                            foundLinks.add(e.getElementsByTag("a").get(0).attr("href"));
//                            seasonName.add(e.getElementsByTag("a").get(0).attr("oldtitle"));
                        }
                    }
                } else {
                    PassList = null;
                }

                if (foundLinks.size() != 0) {

                    for (int i = 0; i < foundLinks.size(); i++) {
                        Log.d(TAG, "scraper: linktoJsoup: " + foundLinks.get(i));
                        Document doc = Jsoup.connect(foundLinks.get(i)).get();
                        Element playlinkElement = doc.getElementsByClass("movieplay").first();
                        if (playlinkElement != null) {
                            String playlink = playlinkElement.getElementsByTag("iframe").attr("src");
                            seasonName.add(doc.getElementsByClass("mvic-desc").get(0).getElementsByTag("h3").get(0).text());
                            Log.d(TAG, "scraper: linktoJsoup: " + playlink);
                            if (playlink.contains("speedostream")) {
                                Document speedo = Jsoup.connect(playlink).referrer("https://hindilinks4u.ws/").get();
                                String as = speedo.toString();
//                                Log.i(TAG, "scraper: DOC:"+ as);
                                String kl = printSubsInDelimiters(as);
                                playLink.add(kl);
                                Log.d(TAG, "scraper: playLInk" + kl);
                            } else {
                                playLink.add(playlink);
                                Log.d(TAG, "scraper: playLInk" + foundLinks.get(i));
                            }
                        }
                    }
                }
            } else {
                PassList = null;
            }
        } else {
            PassList = null;

        }
        if (seasonName.size() != 0) {
            for (int i = 0; i < playLink.size(); i++) {
                SeasonListPOJO seasonListPOJO = new SeasonListPOJO();
                seasonListPOJO.setShowName(seasonName.get(i));
                seasonListPOJO.setSeasons(playLink.get(i));
                PassList.add(seasonListPOJO);
            }
        }

        return PassList;
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
