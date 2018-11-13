package com.codecool.netflixandchill.controller;


import com.codecool.netflixandchill.model.Series;
import com.codecool.netflixandchill.model.Status;
import com.codecool.netflixandchill.model.User;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SeriesService seriesService;

    @MockBean
    private SeasonService seasonService;

    @MockBean
    private EpisodeService episodeService;

    @MockBean
    private UserService service;

    @Test
    @WithMockUser(username = "tivadar", password = "tivadar", roles = "USER")
    public void testGetUserDetails() throws java.lang.Exception {
        User tivadar = new User("tivadar", "tivadar", "tivadar", null, null, null, new Date());

        Mockito.when(service.findByUsername(tivadar.getUserName()))
                .thenReturn(tivadar);

        mvc.perform(get("/users/tivadar"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "tivadar", password = "tivadar", roles = "USER")
    public void testGetWatchListForUser() throws java.lang.Exception {
        List<Series> watchList = new ArrayList<>();
        Series series = new Series("title", "image.jpg", "trailer", "description", Status.ENDED, new Date(), null);
        watchList.add(series);
        User tivadar = new User("tivadar", "tivadar", "tivadar", watchList, null, null, new Date());

        Mockito.when(service.getWatchlistForUser(tivadar.getUserName()))
                .thenReturn(watchList);

        mvc.perform(get("/users/tivadar/watchlist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

}
