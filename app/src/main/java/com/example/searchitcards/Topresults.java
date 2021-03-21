package com.example.searchitcards;

public class Topresults {
//"mal_id": 5114,
//        "rank": 1,
//        "title": "Fullmetal Alchemist: Brotherhood",
//        "url": "https://myanimelist.net/anime/5114/Fullmetal_Alchemist__Brotherhood",
//        "image_url": "https://cdn.myanimelist.net/images/anime/1223/96541.jpg?s=faffcb677a5eacd17bf761edd78bfb3f",
//        "type": "TV",
//        "episodes": 64,
//        "start_date": "Apr 2009",
//        "end_date": "Jul 2010",
//        "members": 2034891,
//        "score": 9.22
    private int mal_id;
//    private int rank;
//    private String episode;
    private String title;
    private double score;
    private String image_url;
    private String type;
    private String start_date;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getMal_id() {
        return mal_id;
    }

    public void setMal_id(int mal_id) {
        this.mal_id = mal_id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    //    public int getRank() {
//        return rank;
//    }
//
//    public void setRank(int rank) {
//        this.rank = rank;
//    }
//
//    public String getEpisode() {
//        return episode;
//    }
//
//    public void setEpisode(String episode) {
//        this.episode = episode;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
//
//    public float getScore() {
//        return score;
//    }
//
//    public void setScore(float score) {
//        this.score = score;
//    }
}
