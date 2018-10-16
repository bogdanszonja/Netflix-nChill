package com.codecool.netflixandchill.dao;

import com.codecool.netflixandchill.model.Series;

import java.util.List;

public interface SeriesDao {
    void add(Series series);

    Series find(long seriesId);

    List<String> getAll();

    List<Series> findBySubstring(String substring);
}
