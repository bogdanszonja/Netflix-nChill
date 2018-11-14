package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.model.Series;
import com.codecool.netflixandchill.model.Status;
import com.codecool.netflixandchill.service.EpisodeService;
import com.codecool.netflixandchill.service.SeasonService;
import com.codecool.netflixandchill.service.SeriesService;
import com.codecool.netflixandchill.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SeriesController.class)
public class SeriesControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private SeasonService seasonService;

    @MockBean
    private EpisodeService episodeService;

    @MockBean
    private SeriesService service;


    @Test
    void testGetAllSeries() throws Exception {
        Series series = new Series("title", "image.jpg", "trailer", "description", Status.ENDED, new Date(), null);
        List<Series> seriesList = new ArrayList<>();
        seriesList.add(series);

        Mockito.when(service.getAllSeries())
                .thenReturn(seriesList);

        mvc.perform(get("/series"))
                .andExpect(status().isOk());
    }


    @Test
    void testGetSingleSeries() throws Exception {
        Series series = new Series("title", "image.jpg", "trailer", "description", Status.ENDED, new Date(), null);
        List<Series> seriesList = new ArrayList<>();
        seriesList.add(series);

        Mockito.when(service.getSingleSeriesById(series.getId()))
                .thenReturn(series);

        mvc.perform(get("/{id}", series.getId()))
                .andExpect(status().isOk());
    }
}

