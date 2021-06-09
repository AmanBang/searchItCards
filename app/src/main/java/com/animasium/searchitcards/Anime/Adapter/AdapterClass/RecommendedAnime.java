package com.animasium.searchitcards.Anime.Adapter.AdapterClass;

public class RecommendedAnime {
//    "mal_id": 31964,
//            "url": "https://myanimelist.net/anime/31964/Boku_no_Hero_Academia",
//            "image_url": "https://cdn.myanimelist.net/images/anime/10/78745.jpg?s=8ea4cb2e8a861e63757d3c05aa5d32c2",
//            "recommendation_url": "https://myanimelist.net/recommendations/anime/24833-31964",
//            "title": "Boku no Hero Academia"

   private String mal_id;
    private String url;
    private String image_url;
    private String title;

    public String getMal_id() {
        return mal_id;
    }

    public void setMal_id(String mal_id) {
        this.mal_id = mal_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
