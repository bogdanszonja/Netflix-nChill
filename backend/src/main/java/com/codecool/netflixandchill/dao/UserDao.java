package com.codecool.netflixandchill.dao;

import com.codecool.netflixandchill.model.Episode;
import com.codecool.netflixandchill.model.User;

import java.util.List;

public interface UserDao {
    void add(User user);

    User find(long userId);

    User find(String email);

    void remove(long userId);

    List<User> getAll();

    void addEpisode(long episodeId, long userId);

    boolean validRegister(String email, String password, String confirmedPassword);

    boolean validLogin(String email, String password);

    List<Episode> getWatchedEpisodesById(long userId);
}
