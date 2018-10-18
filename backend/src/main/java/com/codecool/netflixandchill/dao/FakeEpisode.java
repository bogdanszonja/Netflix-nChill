package com.codecool.netflixandchill.dao;

import com.codecool.netflixandchill.model.Episode;
import com.codecool.netflixandchill.model.Season;

import java.util.Date;

public class FakeEpisode {

    private long id;
    private String title;
    private String description;
    private Date releaseDate;
    private int runtime;
    private int serialNumber;
    private Season season;

    public FakeEpisode(Episode episode) {
        this.id = episode.getId();
        this.title = episode.getTitle();
        this.releaseDate = episode.getReleaseDate();
        this.runtime = episode.getRuntime();
        this.serialNumber = episode.getEpisodeNumber();
        this.season = episode.getSeason();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }
}
