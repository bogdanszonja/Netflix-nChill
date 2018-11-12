package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.model.Episode;
import com.codecool.netflixandchill.model.Series;
import com.codecool.netflixandchill.model.User;
import com.codecool.netflixandchill.service.EpisodeService;
import com.codecool.netflixandchill.service.SeasonService;
import com.codecool.netflixandchill.service.SeriesService;
import com.codecool.netflixandchill.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class JwtUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private EpisodeService episodeService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @PostMapping("/join")
    public void join(@RequestBody Map<String, String> requestJson) {
        this.userService.addUser(requestJson.get("username"),
                requestJson.get("email"),
                bCryptPasswordEncoder.encode(requestJson.get("password")));
    }

    @GetMapping("/{username}")
    public User getUserDetails(@PathVariable String username) {
        return this.userService.findByUsername(username);
    }

    @GetMapping("/{username}/watchlist")
    public List<Series> getWatchlistForUser(@PathVariable String username) {
        return this.userService.getWatchlistForUser(username);
    }

    @GetMapping("/{username}/favourites")
    public List<Series> getFavouritesForUser(@PathVariable String username) {
        return this.userService.getFavouritesForUser(username);
    }

    @GetMapping("/{username}/already-watched")
    public List<Episode> getWatchedEpisodesForUser(@PathVariable String username) {
        return this.userService.getWatchedEpisodesForUser(username);
    }

    @PutMapping("/{username}/add-episode-to-watched/episode/{id}")
    public ResponseEntity addEpisodeToWatched(@PathVariable String username, @PathVariable Long id) {
        if (!this.userService.getWatchedEpisodesForUser(username).contains(episodeService.getSingleEpisodeBySeasonId(id))) {
            this.userService.addEpisodeToWatched(username, id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.getWatchedEpisodesForUser(username));
        } else {
            JSONObject error = new JSONObject();
            error.put("error", "already exist");
            error.put("message", "This episode already in watched list");

            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                    .body(error);
        }
    }

    @PutMapping("/{username}/add-season-to-watched/season/{id}")
    public ResponseEntity addSeasonToWatched(@PathVariable String username, @PathVariable Long id) {
        if (!this.userService.getWatchedEpisodesForUser(username).contains(episodeService.getSingleEpisodeBySeasonId(id))) {
            this.userService.addSeasonToWatched(username, id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.getWatchedEpisodesForUser(username));
        } else {
            JSONObject error = new JSONObject();
            error.put("error", "already exist");
            error.put("message", "This season already in watched list");

            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                    .body(error);
        }
    }

    @PutMapping("/{username}/add-series-to-watched/series/{id}")
    public ResponseEntity addSeriesToWatched(@PathVariable String username, @PathVariable Long id) {
        if (!this.userService.getWatchedEpisodesForUser(username).contains(episodeService.getSingleEpisodeBySeasonId(seasonService.getSeasonBySeriesId(id).getId()))) {
            this.userService.addSeriesToWatched(username, id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.getWatchedEpisodesForUser(username));
        } else {
            JSONObject error = new JSONObject();
            error.put("error", "already exist");
            error.put("message", "This series already in watched list");

            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                    .body(error);
        }
    }

    @PutMapping("/{username}/add-series-to-favourites/series/{id}")
    public ResponseEntity addSeriesToFavourites(@PathVariable String username, @PathVariable Long id) {
        if(!this.userService.getFavouritesForUser(username).contains(seriesService.getSingleSeriesById(id))) {
            this.userService.addSeriesToFavourites(username, id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.getFavouritesForUser(username));
        } else {
            JSONObject error = new JSONObject();
            error.put("error", "already exist");
            error.put("message", "This series already in favourite list");

            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                    .body(error);
        }
    }

    @PutMapping("/{username}/add-series-to-watchlist/series/{id}")
    public ResponseEntity addSeriesToWatchlist(@PathVariable String username, @PathVariable Long id) {
        if (!this.userService.getWatchlistForUser(username).contains(seriesService.getSingleSeriesById(id))) {
            this.userService.addSeriesToWatchlist(username, id);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.getWatchlistForUser(username));
        } else {
            JSONObject error = new JSONObject();
            error.put("error", "already exist");
            error.put("message", "This series already in watchlist");

            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                    .body(error);
        }
    }
}
