package com.codecool.netflixandchill.service;

import com.codecool.netflixandchill.model.Episode;
import com.codecool.netflixandchill.model.Season;
import com.codecool.netflixandchill.model.Series;
import com.codecool.netflixandchill.model.User;
import com.codecool.netflixandchill.repository.EpisodeRepository;
import com.codecool.netflixandchill.repository.SeasonRepository;
import com.codecool.netflixandchill.repository.SeriesRepository;
import com.codecool.netflixandchill.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    public void addUser(String username, String email, String hashedPassword) {
        User user = User.builder()
                .userName(username)
                .emailAddress(email)
                .password(hashedPassword)
                .registrationDate(new Date())
                .build();
        this.userRepository.save(user);
    }

    public User findByUsername(String username) {
        return this.userRepository.findByUserName(username);
    }

    public boolean checkIfEmailAlreadyExists(String email) {
        return userRepository.findUserByEmailAddress(email) != null;
    }

    public boolean checkIfUserNameAlreadyExists(String userName) {
        return userRepository.findByUserName(userName) != null;
    }

    public List<Series> getWatchlistForUser(String username) {
        return (List<Series>) this.userRepository.findByUserName(username).getWatchlist();
    }

    public List<Series> getFavouritesForUser(String username) {
        return (List<Series>) this.userRepository.findByUserName(username).getFavourites();
    }

    public List<Episode> getWatchedEpisodesForUser(String username) {
        return (List<Episode>) this.userRepository.findByUserName(username).getWatchedEpisodes();
    }

    public void addEpisodeToWatched(String username, Long id) {
        User user = this.userRepository.findByUserName(username);
        user.addWatchedEpisodes(this.episodeRepository.findById(id).get());
        this.userRepository.save(user);
    }

    public void addSeasonToWatched(String username, Long id) {
        User user = this.userRepository.findByUserName(username);
        this.seasonRepository.findById(id).get().getEpisodes().forEach(user::addWatchedEpisodes);
        this.userRepository.save(user);
    }

    public void addSeriesToWatched(String username, Long id) {
        User user = this.userRepository.findByUserName(username);
        List<Season> seasonsToAdd = this.seriesRepository.findById(id).get().getSeasons();
        seasonsToAdd.forEach(season -> season.getEpisodes()
                .forEach(user::addWatchedEpisodes));
        this.userRepository.save(user);
    }

    public void addSeriesToFavourites(String username, Long id) {
        User user = this.userRepository.findByUserName(username);
        user.addSeriesToFavouriteList(this.seriesRepository.findById(id).get());
        this.userRepository.save(user);
    }

    public void addSeriesToWatchlist(String username, Long id) {
        User user = this.userRepository.findByUserName(username);
        user.addSeriesToWatchList(this.seriesRepository.findById(id).get());
        this.userRepository.save(user);
    }

    public void addRunTimeToTimeWasted(String username, Series series) {
        for (Episode episode : episodeRepository.findAllBySeasonId((seasonRepository.findFirstBySeriesId(series.getId()).getId()))) {
            findByUsername(username).setTimeWasted(findByUsername(username).getTimeWasted() + episode.getRuntime());
        }
    }

    public void removeEpisodeFromWatched(String username, long id) {
        User user = this.userRepository.findByUserName(username);
        user.removeFromWatchedEpisode(episodeRepository.findById(id).get());
        this.userRepository.save(user);

    }

    public void removeSeasonFromWatched(String username, long id) {
        User user = this.userRepository.findByUserName(username);
        seasonRepository.findById(id).get().getEpisodes().forEach(user::removeFromWatchedEpisode);
        userRepository.save(user);
    }

    public void removeSeriesFromWatched(String username, long id) {
        User user = this.userRepository.findByUserName(username);
        List<Season> seasonToRemove = seriesRepository.findById(id).get().getSeasons();
        seasonToRemove.forEach(season -> season.getEpisodes()
                .forEach(user::removeFromWatchedEpisode));
        userRepository.save(user);
    }

    public void removeSeriesFromFavourite(String username, long id) {
        User user = this.userRepository.findByUserName(username);
        user.removeFromFavouriteList(seriesRepository.findById(id).get());
        userRepository.save(user);
    }

    public void removeSeriesFromWatchlist(String username, long id) {
        User user = this.userRepository.findByUserName(username);
        user.removeFromWatchList(seriesRepository.findById(id).get());
        userRepository.save(user);
    }
}
