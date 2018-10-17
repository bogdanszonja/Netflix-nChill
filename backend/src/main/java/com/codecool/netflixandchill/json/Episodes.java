package com.codecool.netflixandchill.json;

public class Episodes {
    private String episode;

    private String season;

    private String air_date;

    private String name;

    public String getEpisode ()
    {
        return episode;
    }

    public void setEpisode (String episode)
    {
        this.episode = episode;
    }

    public String getSeason ()
    {
        return season;
    }

    public void setSeason (String season)
    {
        this.season = season;
    }

    public String getAir_date ()
    {
        return air_date;
    }

    public void setAir_date (String air_date)
    {
        this.air_date = air_date;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "[Episode: " + episode + " Season: " + season + " AirDate: " +
                air_date + " Title: " + name + "]";
    }
}