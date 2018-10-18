package com.codecool.netflixandchill.util;

import com.codecool.netflixandchill.dao.implementation.EpisodeDaoDB;
import com.codecool.netflixandchill.dao.implementation.SeasonDaoDB;
import com.codecool.netflixandchill.dao.implementation.SeriesDaoDB;
import com.codecool.netflixandchill.dao.implementation.UserDaoDB;
import com.codecool.netflixandchill.model.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonCreator {

    private static JsonCreator instance = null;
    private static Gson gson = new Gson();

    public static JsonCreator getInstance() {
        if (instance == null) {
            instance = new JsonCreator();
        }
        return instance;
    }

    public String getEpisodeById(long episodeId) {
        Map<String, Object> episodeJson = new HashMap<>();
        Episode episode = EpisodeDaoDB.getInstance().find(episodeId);

        episodeJson.put("id", episode.getId());
        episodeJson.put("title", episode.getTitle());
        episodeJson.put("releaseDate", episode.getReleaseDate());
        episodeJson.put("runtime", episode.getRuntime());
        episodeJson.put("episodeNumber", episode.getEpisodeNumber());

        return gson.toJson(episodeJson);
    }

    public String getAllEpisodes() {
        List<Episode> episodes = EpisodeDaoDB.getInstance().getAll();
        JsonArray episodeArray = new JsonArray();
        for (Episode episode : episodes) {
            JsonObject episodeJson = new JsonObject();
            addEpisodePropertiesToJson(episode, episodeJson);
            episodeArray.add(episodeJson);
        }
        return episodeArray.toString();
    }

    public String findEpisodeBySubstring(String subString) {
        List<Episode> episodes = EpisodeDaoDB.getInstance().findBySubstring(subString);
        JsonArray episodeArray = new JsonArray();
        try {
            for (Episode episode : episodes) {
                JsonObject episodeJson = new JsonObject();
                addEpisodePropertiesToJson(episode, episodeJson);
                episodeArray.add(episodeJson);
            }
        } catch (NullPointerException e) {
            return null;
        }
        return episodeArray.toString();
    }

    public String getSeasonById(long seasonId) {
        Map<String, Object> seasonJson = new HashMap<>();
        Season season = SeasonDaoDB.getInstance().find(seasonId);

        seasonJson.put("id", season.getId());
        seasonJson.put("title", season.getTitle());
        seasonJson.put("seasonNumber", season.getSeasonNumber());
        seasonJson.put("year", season.getYear());

        return gson.toJson(seasonJson);
    }

    public String getAllSeasons() {
        List<Season> seasons = SeasonDaoDB.getInstance().getAll();
        JsonArray seasonArray = new JsonArray();
        for (Season season: seasons) {
            JsonObject seasonJson = new JsonObject();
            addSeasonPropertiesToJson(season, seasonJson);
            seasonArray.add(seasonJson);
        }
        return seasonArray.toString();
    }

    public String getSeriesById(long seriesId) {
        Map<String, Object> showJson = new HashMap<>();
        Series show = SeriesDaoDB.getInstance().find(seriesId);

        showJson.put("id", show.getId());
        showJson.put("title", show.getTitle());
        showJson.put("description", show.getDescription());
        showJson.put("status", show.getStatus());
        showJson.put("airDate", show.getAirDate());
        showJson.put("seasons", show.getSeasons());
        showJson.put("genres", show.getGenres());

        return gson.toJson(showJson);
    }

    public String getAllShows() {
        JsonArray seriesArray = new JsonArray();
        List<Series> shows = SeriesDaoDB.getInstance().getAll();
        addAllShowPropertyToJson(shows, seriesArray);
        return seriesArray.toString();
    }

    public String findSeriesBySubstring(String subString) {
        List<Series> shows = SeriesDaoDB.getInstance().findBySubstring(subString);
        JsonArray seriesArray = new JsonArray();
        try {
            addAllShowPropertyToJson(shows, seriesArray);
        } catch (NullPointerException e) {
            return null;
        }
        return seriesArray.toString();
    }

    public String findUserById(long userId) {
        Map<String, Object> userJson = new HashMap<>();
        User user = UserDaoDB.getInstance().find(userId);
        putUserDataToJson(user, userJson);

        return gson.toJson(userJson);
    }

    public String findUserByEmail(String email) {
        User user = UserDaoDB.getInstance().find(email);
        JsonObject userJson = new JsonObject();
        userJson.addProperty("id", user.getId());
        userJson.addProperty("username", user.getUserName());
        userJson.addProperty("emailAddress", user.getEmailAddress());
        userJson.addProperty("registrationDate", user.getRegistrationDate().toString());
        JsonArray watchlistArray = new JsonArray();
        for (Series series: user.getWatchlist()) {
            JsonObject seriesJson = new JsonObject();
            seriesJson.addProperty("id", series.getId());
            seriesJson.addProperty("description", series.getDescription());
            seriesJson.addProperty("title", series.getTitle());
            seriesJson.addProperty("airDate", series.getAirDate().toString());
            seriesJson.addProperty("status", series.getStatus().toString());
            watchlistArray.add(seriesJson);
        }
        JsonArray favouritesArray = new JsonArray();
        for (Series series: user.getFavourites()) {
            JsonObject seriesJson = new JsonObject();
            seriesJson.addProperty("id", series.getId());
            seriesJson.addProperty("description", series.getDescription());
            seriesJson.addProperty("title", series.getTitle());
            seriesJson.addProperty("airDate", series.getAirDate().toString());
            seriesJson.addProperty("status", series.getStatus().toString());
            favouritesArray.add(seriesJson);
        }
        JsonArray watchedEpisodesArray = new JsonArray();
        for (Episode episode: user.getWatchedEpisodes()) {
            JsonObject episodeJson = new JsonObject();
            this.addEpisodePropertiesToJson(episode, episodeJson);
            watchedEpisodesArray.add(episodeJson);
        }
        userJson.add("watchlist", watchlistArray);
        userJson.add("favourites", favouritesArray);
        userJson.add("watchedEpisodes", watchedEpisodesArray);

        return userJson.toString();
    }

    public String getAllUsers() {
        List<User> users = UserDaoDB.getInstance().getAll();
        JsonArray userArray = new JsonArray();
        for (User user: users) {
            JsonObject userJson = new JsonObject();
            userJson.addProperty("id", user.getId());
            userJson.addProperty("emailAddress", user.getEmailAddress());
            userJson.addProperty("registrationDate", user.getRegistrationDate().toString());
            userJson.addProperty("username", user.getUserName());
            userArray.add(userJson);
        }
        return userArray.toString();
    }

    public String getWatchedEpisodesByUserId(long userId) {
        List<Episode> episodes = UserDaoDB.getInstance().getWatchedEpisodesById(userId);
        JsonArray episodeArray = new JsonArray();
        for (Episode episode: episodes) {
            JsonObject episodeJson = new JsonObject();
            addEpisodePropertiesToJson(episode, episodeJson);
            episodeArray.add(episodeJson);
        }
        return episodeArray.toString();
    }

    private void addEpisodePropertiesToJson(Episode episode, JsonObject episodeJson) {
        episodeJson.addProperty("id", episode.getId());
        episodeJson.addProperty("title", episode.getTitle());
        episodeJson.addProperty("releaseDate", episode.getReleaseDate().toString());
        episodeJson.addProperty("runtime", episode.getRuntime());
        episodeJson.addProperty("episodeNumber", episode.getEpisodeNumber());
    }

    private void addSeasonPropertiesToJson(Season season, JsonObject seasonJson) {
        seasonJson.addProperty("id", season.getId());
        seasonJson.addProperty("title", season.getTitle());
        seasonJson.addProperty("seasonNumber", season.getSeasonNumber());
        seasonJson.addProperty("year", season.getYear().toString());
    }

    private void addSeriesPropertiesToJson(Series show, JsonObject seriesJson) {
        seriesJson.addProperty("id", show.getId());
        seriesJson.addProperty("description", show.getDescription());
        seriesJson.addProperty("title", show.getTitle());
        seriesJson.addProperty("airDate", show.getAirDate().toString());
        seriesJson.addProperty("status", show.getStatus().toString());
    }

    private void putUserDataToJson(User user, Map<String,Object> userJson) {
        userJson.put("id", user.getId());
        userJson.put("emailAddress", user.getEmailAddress());
        userJson.put("registrationDate", user.getRegistrationDate());
        userJson.put("username", user.getUserName());
        userJson.put("watchlist", user.getWatchlist());
        userJson.put("favourite", user.getFavourites());
        userJson.put("watchedEpisodes", user.getWatchedEpisodes());
    }

    private void addAllShowPropertyToJson(List<Series> shows, JsonArray seriesArray) {
        for (Series show : shows) {
            JsonObject showJson = new JsonObject();
            addSeriesPropertiesToJson(show, showJson);
            JsonArray seasonArray = new JsonArray();
            for (Season season : show.getSeasons()) {
                JsonObject seasonJson = new JsonObject();
                addSeasonPropertiesToJson(season, seasonJson);
                JsonArray episodeArray = new JsonArray();
                for (Episode episode : season.getEpisodes()) {
                    JsonObject episodeJson = new JsonObject();
                    addEpisodePropertiesToJson(episode, episodeJson);
                    episodeArray.add(episodeJson);
                }
                seasonJson.add("episodes", episodeArray);
                seasonArray.add(seasonJson);
            }
            showJson.add("seasons", seasonArray);
            JsonArray genreArray = new JsonArray();
            for (Genre genre: show.getGenres()) {
                JsonObject genreJson = new JsonObject();
                genreJson.addProperty("name", String.valueOf(genre));
                genreArray.add(genreJson);
            }
            showJson.add("genres", genreArray);
            seriesArray.add(showJson);
        }
    }
}
