package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.model.Series;
import com.codecool.netflixandchill.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SeriesController {

    @Autowired
    private SeriesService seriesService;


    @GetMapping
    public List<Series> getAllSeries() {
        return this.seriesService.getAllSeries();
    }

    @GetMapping("/{id}")
    public Series getSingleSeries(@PathVariable Long id) {
        return this.seriesService.getSingleSeriesById(id);
    }

    @GetMapping("/search")
    public List<Series> getSeriesBySubstring(@RequestParam("searchTerm") String substring) {
        return this.seriesService.getSeriesBySubstring(substring);
    }

    @GetMapping("/trending")
    public List<Series> getTrendingSeries() {
        return this.seriesService.getTrendingSeries();
    }

}
