package com.codecool.netflixandchill.json;

public class TvShow {
    private String[] genres;

    private String status;

    private String[] pictures;

    private String runtime;

    private Episodes[] episodes;

    private String description_source;

    private String youtube_link;

    private String end_date;

    private String rating_count;

    private String description;

    private String name;

    private String rating;

    private String start_date;


    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getPictures() {
        return pictures;
    }

    public void setPictures(String[] pictures) {
        this.pictures = pictures;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public Episodes[] getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Episodes[] episodes) {
        this.episodes = episodes;
    }


    public String getDescription_source() {
        return description_source;
    }

    public void setDescription_source(String description_source) {
        this.description_source = description_source;
    }


    public String getYoutube_link() {
        return youtube_link;
    }

    public void setYoutube_link(String youtube_link) {
        this.youtube_link = youtube_link;
    }


    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getRating_count() {
        return rating_count;
    }

    public void setRating_count(String rating_count) {
        this.rating_count = rating_count;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    @Override
    public String toString() {
        return "[Genres: " + genres + " Status: " + status +
                " Pictures: " + pictures + " Runtime: " + runtime +
                " Episodes: " + episodes + " Youtube_link: " + youtube_link + " EndDate: " + end_date +
                " Description: " + description + " Title: " + name + " Image_thumbnail_path: " +
                " Rating: " + rating + " StartDate: " + start_date + "]";
    }
}
