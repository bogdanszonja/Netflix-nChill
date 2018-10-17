package com.codecool.netflixandchill.json;

import java.util.LinkedList;
import java.util.List;

public class Show {
    private TvShow tvShow;
    private static List<Show> tvShowList = new LinkedList<>();


    public TvShow getTvShow ()
    {
        return tvShow;
    }

    public void setTvShow (TvShow tvShow)
    {
        this.tvShow = tvShow;
    }

    public void addShowToMyTvShow(Show tvShow) {
        tvShowList.add(tvShow);
    }

    public List<Show> getTvShowList() {
        return tvShowList;
    }

    @Override
    public String toString()
    {
        return tvShow.toString();
    }
}
