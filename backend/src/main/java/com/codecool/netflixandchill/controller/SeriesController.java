package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.model.Series;
import com.codecool.netflixandchill.service.SeriesService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity getSeriesBySubstring(@RequestParam("searchTerm") String substring) {
        if ( this.seriesService.getSeriesBySubstring(substring).isEmpty()) {
            JSONObject error = new JSONObject();
            error.put("error", "cannot find");
            error.put("message", "This series not in series list");

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(error);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.seriesService.getSeriesBySubstring(substring));
    }

    @GetMapping("/trending")
    public List<Series> getTrendingSeries() {
        return this.seriesService.getTrendingSeries();
    }

}
