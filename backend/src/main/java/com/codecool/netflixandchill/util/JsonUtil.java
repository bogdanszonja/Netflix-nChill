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

import java.util.List;

public class JsonUtil {

    private static JsonUtil instance = null;
    private static Gson gson = new Gson();

    public static JsonUtil getInstance() {
        if (instance == null) {
            instance = new JsonUtil();
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
        Series show = SeriesDaoDB.getInstance().find(seriesId);
        return gson.toJson(show);
    }

    public String getAllShows() {
        List<String> shows = SeriesDaoDB.getInstance().getAll();
        System.out.println(shows);
        return gson.toJson(shows);

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
