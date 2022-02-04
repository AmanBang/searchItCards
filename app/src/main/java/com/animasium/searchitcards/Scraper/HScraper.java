package com.animasium.searchitcards.Scraper;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HScraper {

    public List<String> Hscraper(String movieName, String year) throws NullPointerException, IOException {
        String link = "";
        String separator = ":";
        String mN = "";
        List<String> linkList = new ArrayList<>();
        if (movieName.contains(separator)) {
            mN = movieName.replace(separator, " –");
            if (mN.contains(" - ")){
                mN = mN.replace(" - "," – ");
                if (mN.contains(".")){
                    mN = mN.replace(".","");
                }
            }
            if (mN.contains(".")){
                mN = mN.replace(".","");
            }
            Log.d("elementHinf", "Hscraper: " + mN);
        } else {
            mN = movieName;
            if (mN.contains(" - ")){
                mN = mN.replace(" - "," – ");
                if (mN.contains(".")){
                    mN = mN.replace(".","");
                }
            }
            if (mN.contains(".")){
                mN = mN.replace(".","");
            }
            Log.d("elementHinf", "else:Hscraper: " + mN);
        }
        try {
            String y =  mN +" ("+year+")";

            Document html = Jsoup.connect("https://hindilinks4u.click/?s=" + y).get();
            if (html != null){
            Log.d("elementHinf", "Hscraper: "+ y);
            Element element = html.select("a:contains(" +y+ ")").first();
            if (element == null){
                return null;
            }else {
            link = element.attr("href");
            Log.d("elementHinf", element + "");

            }

            }else {
                return null;
            }


        } catch (Error | Exception e) {
            e.printStackTrace();
        }


        if (!link.isEmpty()) {
            Document html2 = Jsoup.connect(link).get();

            Element movieplay = html2.select("a:contains(Server 4)").first();
            Element movieplay1 = html2.select("a:contains(Server 1)").first();

            String streaminglink = movieplay.attr("href");
            String streaminglink1 = movieplay1.attr("href");



            Document Doc = Jsoup.connect(streaminglink).get();

            String html = Doc.toString();
            String l = printSubsInDelimiters(html);
            linkList.add(l);
            if (streaminglink1.contains("stream")){
            linkList.add(streaminglink1.replace("/v/", "/e/"));
            }else {
                linkList.add(streaminglink1);
            }
            Log.d("elementHinf", l + ", " + streaminglink1);
            return linkList;
        } else {
            return null;
        }


    }

    public String printSubsInDelimiters(String str) {
        String r = null;
        Pattern pattern = Pattern.compile("src: \"(.*?)\"", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            r = (matcher.group(1));
        }
        return r;
    }
}
