package com.animasium.searchitcards.AnimeShowEPList;

public class Episode {


    private String episode_id;
    private String title;
    private String aired;
    private String filler;
    private String episodeImage;
    private String episodeDetails;
    private String ShowID;
    private String Season;
    private String subLink;
    private String dubLink;
    private String downloadLink;

    public String getSubLink() {
        return subLink;
    }

    public void setSubLink(String subLink) {
        this.subLink = subLink;
    }

    public String getDubLink() {
        return dubLink;
    }

    public void setDubLink(String dubLink) {
        this.dubLink = dubLink;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getEpisodeImage() {
        return episodeImage;
    }

    public void setEpisodeImage(String episodeImage) {
        this.episodeImage = episodeImage;
    }

    public String getEpisodeDetails() {
        return episodeDetails;
    }

    public void setEpisodeDetails(String episodeDetails) {
        this.episodeDetails = episodeDetails;
    }

    public String getEpisode_id() {
        return episode_id;
    }

    public void setEpisode_id(String episode_id) {
        this.episode_id = episode_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAired() {
        return aired;
    }

    public void setAired(String aired) {
        this.aired = aired;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    public String getShowID() {
        return ShowID;
    }

    public void setShowID(String showID) {
        ShowID = showID;
    }

    public String getSeason() {
        return Season;
    }

    public void setSeason(String season) {
        Season = season;
    }
}
