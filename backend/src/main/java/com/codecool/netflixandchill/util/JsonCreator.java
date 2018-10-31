package com.codecool.netflixandchill.util;

import com.codecool.netflixandchill.dao.EpisodeDao;
import com.codecool.netflixandchill.dao.SeasonDao;
import com.codecool.netflixandchill.dao.SeriesDao;
import com.codecool.netflixandchill.dao.UserDao;
import com.codecool.netflixandchill.model.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

public class JsonCreator {

    private EpisodeDao episodeDao;
    private SeasonDao seasonDao;
    private SeriesDao seriesDao;
    private UserDao userDao;


    public JsonCreator(EpisodeDao episodeDao, SeasonDao seasonDao, SeriesDao seriesDao, UserDao userDao) {
        this.episodeDao = episodeDao;
        this.seasonDao = seasonDao;
        this.seriesDao = seriesDao;
        this.userDao = userDao;
    }


    public JsonObject getEpisodeById(long episodeId) {
        Episode episode = episodeDao.find(episodeId);

        return (episode == null) ? null : this.createEpisodeJson(episode);
    }

    public JsonArray getAllEpisodes() {
        List<Episode> episodes = episodeDao.getAll();

        if (episodes.isEmpty()) return null;

        JsonArray episodeArray = new JsonArray();
        episodes.forEach(episode -> episodeArray.add(this.createEpisodeJson(episode)));

        return episodeArray;
    }

    public JsonArray findEpisodeBySubstring(String subString) {
        List<Episode> episodes = episodeDao.findBySubstring(subString);

        if (episodes.isEmpty()) return null;

        JsonArray episodeArray = new JsonArray();
        episodes.forEach(episode -> episodeArray.add(this.createEpisodeJson(episode)));

        return episodeArray;
    }

    public JsonObject getSeasonById(long seasonId) {
        Season season = seasonDao.find(seasonId);

        return (season == null) ? null : this.createSeasonJson(season);
    }

    public JsonArray getAllSeasons() {
        List<Season> seasons = seasonDao.getAll();

        if (seasons.isEmpty()) return null;

        JsonArray seasonArray = new JsonArray();
        seasons.forEach(season -> seasonArray.add(this.createSeasonJson(season)));

        return seasonArray;
    }

    public JsonObject getSeriesById(long seriesId) {
        Series series = seriesDao.find(seriesId);

        return (series == null) ? null : this.createSeriesJson(series);
    }

    public JsonArray getAllSeries() {
        List<Series> seriesList = seriesDao.getAll();

        if (seriesList.isEmpty()) return null;

        JsonArray seriesArray = new JsonArray();
        seriesList.forEach(series -> seriesArray.add(this.createSeriesJson(series)));

        return seriesArray;
    }

    public JsonArray findSeriesBySubstring(String subString) {
        List<Series> seriesList = seriesDao.findBySubstring(subString);

        if (seriesList.isEmpty()) return null;

        JsonArray seriesArray = new JsonArray();
        seriesList.forEach(series -> seriesArray.add(this.createSeriesJson(series)));

        return seriesArray;
    }

    public JsonObject findUserById(long userId) {
        User user = userDao.find(userId);

        return (user == null) ? null : this.createUserJson(user);
    }

    public JsonObject findUserByEmail(String email) {
        User user = userDao.find(email);

        return (user == null) ? null : this.createUserJson(user);
    }

    public JsonArray getAllUsers() {
        List<User> users = userDao.getAll();

        if (users.isEmpty()) return null;

        JsonArray userArray = new JsonArray();
        users.forEach(user -> userArray.add(this.createUserJson(user)));

        return userArray;
    }

    public JsonArray getWatchedEpisodesByUserId(long userId) {
        List<Episode> episodes = userDao.getWatchedEpisodesById(userId);

        if (episodes.isEmpty()) return null;

        JsonArray episodeArray = new JsonArray();
        episodes.forEach(episode -> episodeArray.add(this.createEpisodeJson(episode)));

        return episodeArray;
    }

    private JsonObject createEpisodeJson(Episode episode) {
        JsonObject episodeJson = new JsonObject();

        episodeJson.addProperty("id", episode.getId());
        episodeJson.addProperty("title", episode.getTitle());
        episodeJson.addProperty("releaseDate", episode.getReleaseDate().toString());
        episodeJson.addProperty("runtime", episode.getRuntime());
        episodeJson.addProperty("episodeNumber", episode.getEpisodeNumber());

        return episodeJson;
    }

    private JsonObject createSeasonJson(Season season) {
        JsonObject seasonJson = new JsonObject();

        seasonJson.addProperty("id", season.getId());
        seasonJson.addProperty("title", season.getTitle());
        seasonJson.addProperty("seasonNumber", season.getSeasonNumber());
        seasonJson.addProperty("year", season.getYear().toString());

        JsonArray episodeArray = new JsonArray();
        season.getEpisodes().forEach(episode -> episodeArray.add(this.createEpisodeJson(episode)));
        seasonJson.add("episodes", episodeArray);

        return seasonJson;
    }

    private JsonObject createSeriesJson(Series series) {
        JsonObject seriesJson = new JsonObject();

        seriesJson.addProperty("id", series.getId());
        seriesJson.addProperty("title", series.getTitle());
        seriesJson.addProperty("image", series.getImage());
        seriesJson.addProperty("trailer", series.getTrailer());
        seriesJson.addProperty("description", series.getDescription());
        seriesJson.addProperty("airDate", series.getAirDate().toString());
        seriesJson.addProperty("status", series.getStatus().toString());

//        JsonArray seasonArray = new JsonArray();
//        series.getSeasons().forEach(season -> seasonArray.add(this.createSeasonJson(season)));
//        seriesJson.add("seasons", seasonArray);
//
//        JsonArray genreArray = new JsonArray();
//        series.getGenres().forEach(genre -> genreArray.add(this.createGenreJson(genre)));
//        seriesJson.add("genres", genreArray);

        return seriesJson;
    }

    private JsonObject createGenreJson(Genre genre) {
        JsonObject genreJson = new JsonObject();

        genreJson.addProperty("name", String.valueOf(genre));

        return genreJson;
    }

    private JsonObject createUserJson(User user) {
        JsonObject userJson = new JsonObject();

        userJson.addProperty("id", user.getId());
        userJson.addProperty("username", user.getUserName());
        userJson.addProperty("emailAddress", user.getEmailAddress());
        userJson.addProperty("registrationDate", user.getRegistrationDate().toString());

        JsonArray watchlistArray = new JsonArray();
        user.getWatchlist().forEach(series -> watchlistArray.add(this.createSeriesJson(series)));
        userJson.add("watchlist", watchlistArray);

        JsonArray favouritesArray = new JsonArray();
        user.getFavourites().forEach(series -> favouritesArray.add(this.createSeriesJson(series)));
        userJson.add("favourites", favouritesArray);

        JsonArray watchedEpisodesArray = new JsonArray();
        user.getWatchedEpisodes().forEach(episode -> watchedEpisodesArray.add(this.createEpisodeJson(episode)));
        userJson.add("watchedEpisodes", watchedEpisodesArray);

        return userJson;
    }

}
