package com.codecool.netflixandchill.dao;

import com.codecool.netflixandchill.model.Season;

import java.util.List;

public interface SeasonDao {
    void add(Season season);

    Season find(long seasonId);

    List<Season> getAll();
}
