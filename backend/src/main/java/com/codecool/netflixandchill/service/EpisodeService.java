package com.codecool.netflixandchill.service;

import com.codecool.netflixandchill.model.Episode;
import com.codecool.netflixandchill.repository.EpisodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EpisodeService {

    @Autowired
    private EpisodeRepository episodeRepository;

    public void add(Episode episode) {
        this.episodeRepository.save(episode);
    }

    public Episode getSingleEpisodeBySeasonId(long id) {
        return episodeRepository.findEpisodeBySeason(id);
    }
}
