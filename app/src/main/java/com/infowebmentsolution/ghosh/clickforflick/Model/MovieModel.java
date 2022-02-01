package com.infowebmentsolution.ghosh.clickforflick.Model;

public class MovieModel {
    private String Episode;
    private String Poster_url;
    private String description;

    /* renamed from: id */
    private String id;
    private String lang;
    private String title;
    private String trailer_url;
    private String video_url;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideo_url() {
        return this.video_url;
    }

    public void setVideo_url(String video_url2) {
        this.video_url = video_url2;
    }

    public String getPoster_url() {
        return this.Poster_url;
    }

    public void setPoster_url(String poster_url) {
        this.Poster_url = poster_url;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description2) {
        this.description = description2;
    }

    public String getLang() {
        return this.lang;
    }

    public void setLang(String lang2) {
        this.lang = lang2;
    }

    public String getEpisode() {
        return this.Episode;
    }

    public void setEpisode(String episode) {
        this.Episode = episode;
    }

    public String getTrailer_url() {
        return this.trailer_url;
    }

    public void setTrailer_url(String trailer_url2) {
        this.trailer_url = trailer_url2;
    }
}