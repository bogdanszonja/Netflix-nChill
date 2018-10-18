package com.codecool.netflixandchill.util;

import com.codecool.netflixandchill.dao.FakeEpisode;
import com.codecool.netflixandchill.dao.implementation.EpisodeDaoDB;
import com.codecool.netflixandchill.dao.implementation.SeasonDaoDB;
import com.codecool.netflixandchill.dao.implementation.SeriesDaoDB;
import com.codecool.netflixandchill.dao.implementation.UserDaoDB;
import com.codecool.netflixandchill.model.Episode;
import com.codecool.netflixandchill.model.Season;
import com.codecool.netflixandchill.model.Series;
import com.codecool.netflixandchill.model.User;
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
        Episode episode = EpisodeDaoDB.getInstance().find(episodeId);
        return gson.toJson(episode);
    }

    public String getAllEpisodes() {
        List<Episode> episodes = EpisodeDaoDB.getInstance().getAll();
        return gson.toJson(episodes);
    }

    public String findEpisodeBySubstring(String subString) {
        List<FakeEpisode> episodes = EpisodeDaoDB.getInstance().findBySubstring(subString);
        return gson.toJson(episodes);
    }

    public String getSeasonById(long seasonId) {
        Season season = SeasonDaoDB.getInstance().find(seasonId);
        return gson.toJson(season);
    }

    public String getAllSeasons() {
        List<Season> seasons = SeasonDaoDB.getInstance().getAll();
        return gson.toJson(seasons);
    }

    public String getSeriesById(long seriesId) {
        Map<String, Object> showJson = new HashMap<>();

        Series show = SeriesDaoDB.getInstance().find(seriesId);

        showJson.put("id", show.getId());
        showJson.put("title", show.getTitle());
        showJson.put("description", show.getDescription());
        showJson.put("status", show.getStatus());
        showJson.put("airDate", show.getAirDate());
        showJson.put("seasons", new ArrayList<>());
        showJson.put("genres", new ArrayList<>());

        return gson.toJson(showJson);
    }

    public String getAllShows() {
        JsonArray seriesArray = new JsonArray();
//        List<Map<String, Object>> showsJson = new ArrayList<>();

        List<Series> shows = SeriesDaoDB.getInstance().getAll();

        for (Series show: shows) {
//            Map<String, Object> showDetails = new HashMap<>();

            JsonObject showJson = new JsonObject();
            showJson.addProperty("id", show.getId());
            showJson.addProperty("title", show.getTitle());
            showJson.addProperty("description", show.getDescription());
            showJson.addProperty("status", show.getStatus().toString());
            showJson.addProperty("airDate", show.getAirDate().toString());
            JsonArray seasonArray = new JsonArray();
            for (Season season: show.getSeasons()) {
                JsonObject seasonJson = new JsonObject();
                seasonJson.addProperty("id", season.getId());
                seasonJson.addProperty("title", season.getTitle());
                seasonJson.addProperty("seasonNumber", season.getSeasonNumber());
                seasonJson.addProperty("year", season.getYear().toString());
                JsonArray episodeArray = new JsonArray();
                for (Episode episode: season.getEpisodes()) {
                    JsonObject episodeJson = new JsonObject();
                    episodeJson.addProperty("id", episode.getId());
                    episodeJson.addProperty("title", episode.getTitle());
                    episodeArray.add(episodeJson);
                }
                seasonJson.add("episodes", episodeArray);
                seasonArray.add(seasonJson);
            }
            showJson.add("seasons", seasonArray);
            showJson.addProperty("genres", new ArrayList<>().toString());
            seriesArray.add(showJson);
        }

        return seriesArray.toString();

    }

    public String findSeriesBySubstring(String subString) {
        List<Series> shows = SeriesDaoDB.getInstance().findBySubstring(subString);
        return gson.toJson(shows);
    }

    public String findUserById(long userId) {
        User user = UserDaoDB.getInstance().find(userId);
        return gson.toJson(user);
    }

    public String findUserByEmail(String email) {
        User user = UserDaoDB.getInstance().find(email);
        return gson.toJson(user);
    }

    public String getAllUsers() {
        List<User> users = UserDaoDB.getInstance().getAll();
        return gson.toJson(users);
    }

    public String getWatchedEpisodesByUserId(long userId) {
        List<Episode> users = UserDaoDB.getInstance().getWatchedEpisodesById(userId);
        return gson.toJson(users);
    }

}
