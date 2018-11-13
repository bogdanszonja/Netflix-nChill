package com.codecool.netflixandchill.service;

import com.codecool.netflixandchill.model.Series;
import com.codecool.netflixandchill.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesService {

    @Autowired
    private SeriesRepository seriesRepository;

    public void addSeries(Series series) {
        this.seriesRepository.save(series);
    }

    public Series getSingleSeriesById(Long id) {
        return this.seriesRepository.findById(id).get();
    }

    public List<Series> getAllSeries() {
        return this.seriesRepository.findAll();
    }

    public List<Series> getSeriesBySubstring(String substring) {
        return this.seriesRepository.findByTitleContainingIgnoreCase(substring);
    }

    public List<Series> getTrendingSeries() {
        return this.seriesRepository.findTop3ByOrderByAirDateDesc();
    }

}
