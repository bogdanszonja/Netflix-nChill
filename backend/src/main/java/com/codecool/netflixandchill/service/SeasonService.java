package com.codecool.netflixandchill.service;

import com.codecool.netflixandchill.model.Season;
import com.codecool.netflixandchill.repository.SeasonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeasonService {
    private static Logger logger = LoggerFactory.getLogger(SeasonService.class);

    @Autowired
    private SeasonRepository seasonRepository;

    public void add(Season season) {
        this.seasonRepository.save(season);
    }

    public Season getSeasonBySeriesId(long id) {
        return seasonRepository.findFirstBySeriesId(id);
    }
}
