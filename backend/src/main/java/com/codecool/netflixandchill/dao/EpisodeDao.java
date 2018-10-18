package com.codecool.netflixandchill.dao;

import com.codecool.netflixandchill.model.Episode;

import java.util.List;

public interface EpisodeDao {
    void add(Episode episode);

    Episode find(long episodeId);

    List<Episode> getAll();

    List<Episode> findBySubstring(String substring);
}
