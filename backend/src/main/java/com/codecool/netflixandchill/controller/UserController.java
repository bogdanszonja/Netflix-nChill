package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.model.Episode;
import com.codecool.netflixandchill.model.Series;
import com.codecool.netflixandchill.model.User;
import com.codecool.netflixandchill.service.EpisodeService;
import com.codecool.netflixandchill.service.SeasonService;
import com.codecool.netflixandchill.service.SeriesService;
import com.codecool.netflixandchill.service.UserService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

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
    public ResponseEntity join(@RequestBody Map<String, String> requestJson) {
        if (userService.checkIfEmailAlreadyExists(requestJson.get("email")) || userService.checkIfUserNameAlreadyExists("username")) {
            JsonObject error = new JsonObject();
            error.addProperty("error", "already exist");
            error.addProperty("message", "This username or email already exist");
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(error.toString());
        } else {
            this.userService.addUser(requestJson.get("username"),
                    requestJson.get("email"),
                    bCryptPasswordEncoder.encode(requestJson.get("password")));
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @GetMapping("/{username}")
    public User getUserDetails(@PathVariable String username, Authentication authentication) {
        System.out.println("****************************************");
        System.out.println(authentication.getName());
        System.out.println(this.userService.findByUsername(authentication.getName()));
        return this.userService.findByUsername(username);
    }

    @GetMapping("/{username}/watchlist")
    public List<Series> getWatchlistForUser(@PathVariable String username) {
        return userService.getWatchlistForUser(username);
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
            this.userService.addRunTimeToTimeWastedWhenWatchedEpisode(username, episodeService.findEpisodeById(id));

            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.getWatchedEpisodesForUser(username));
        } else {
            JsonObject error = new JsonObject();
            error.addProperty("error", "already exist");
            error.addProperty("message", "This episode already in watched list");

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(error.toString());
        }
    }

    @PutMapping("/{username}/add-season-to-watched/season/{id}")
    public ResponseEntity addSeasonToWatched(@PathVariable String username, @PathVariable Long id) {
        if (userService.isSeasonNotInWatchlist(username, id)) {
            userService.addSeasonToWatched(username, id);
            userService.addRunTimeToTimeWastedWhenWatchedSeason(username, seasonService.findSeasonById(id));

            return ResponseEntity.status(HttpStatus.OK)
                    .body(getWatchedEpisodesForUser(username));
        }
        else {
            JsonObject error = new JsonObject();
            error.addProperty("error", "already exist");
            error.addProperty("message", "This season already in watched list");

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(error.toString());
        }
    }

    @PutMapping("/{username}/add-series-to-watched/series/{id}")
    public ResponseEntity addSeriesToWatched(@PathVariable String username, @PathVariable Long id) {
        if (!this.userService.getWatchedEpisodesForUser(username).contains(episodeService.getSingleEpisodeBySeasonId(seasonService.getSeasonBySeriesId(id).getId()))) {
            this.userService.addSeriesToWatched(username, id);
            this.userService.addRunTimeToTimeWastedWhenWatchedSeries(username, seriesService.getSingleSeriesById(id));

            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.getWatchedEpisodesForUser(username));
        } else {
            JsonObject error = new JsonObject();
            error.addProperty("error", "already exist");
            error.addProperty("message", "This series already in watched list");

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(error.toString());
        }
    }

    @PutMapping("/{username}/add-series-to-favourites/series/{id}")
    public ResponseEntity addSeriesToFavourites(@PathVariable String username, @PathVariable Long id) {
        if (!this.userService.getFavouritesForUser(username).contains(seriesService.getSingleSeriesById(id))) {
            this.userService.addSeriesToFavourites(username, id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.getFavouritesForUser(username));
        } else {
            JsonObject error = new JsonObject();
            error.addProperty("error", "already exist");
            error.addProperty("message", "This series already in favourite list");

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(error.toString());
        }
    }

    @PutMapping("/{username}/add-series-to-watchlist/series/{id}")
    public ResponseEntity addSeriesToWatchlist(@PathVariable String username, @PathVariable Long id) {
        if (!this.userService.isSeriesNotInWatchlist(username, id)) {
            this.userService.addSeriesToWatchlist(username, id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.getWatchlistForUser(username));
        } else {
            JsonObject error = new JsonObject();
            error.addProperty("error", "already exist");
            error.addProperty("message", "This series already in watchlist");

            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                    .body(error.toString());
        }
    }

    @DeleteMapping("/{username}/remove-episode-from-watched/episode/{id}")
    public void removeEpisodeFromWatched(@PathVariable String username, @PathVariable Long id) {
        userService.removeEpisodeFromWatched(username, id);
    }

    @DeleteMapping("/{username}/remove-season-from-watched/season/{id}")
    public void removeSeasonFromWatched(@PathVariable String username, @PathVariable Long id) {
        userService.removeSeasonFromWatched(username, id);
    }

    @DeleteMapping("/{username}/remove-series-from-watched/series/{id}")
    public void removeSeriesFromWatched(@PathVariable String username, @PathVariable Long id) {
        userService.removeSeriesFromWatched(username, id);
    }

    @DeleteMapping("/{username}/remove-series-from-favourites/series/{id}")
    public void removeSeriesFromFavourite(@PathVariable String username, @PathVariable Long id) {
        userService.removeSeriesFromFavourite(username, id);
    }

    @DeleteMapping("/{username}/remove-series-from-watchlist/series/{id}")
    public void removeSeriesFromWatchlist(@PathVariable String username, @PathVariable Long id) {
        userService.removeSeriesFromWatchlist(username, id);
    }



}
