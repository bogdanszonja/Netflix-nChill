package com.codecool.netflixandchill.config;

import com.codecool.netflixandchill.json.Show;
import com.codecool.netflixandchill.json.TvShow;
import com.codecool.netflixandchill.model.*;
import com.codecool.netflixandchill.util.EMFManager;
import com.codecool.netflixandchill.util.RemoteURLReader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class InitializerDB {
    public static void populateDb(EntityManager em) throws IOException {

        RemoteURLReader urlReader = new RemoteURLReader();
        Initializer init = new Initializer(urlReader);
        Show tvShow = new Show();
        init.addTvShowToMyTvSHowsList();

        EntityTransaction transactionTest = em.getTransaction();
        transactionTest.begin();

        Series series1 = Series.builder()
                .title("szonja").description("sz").status(Status.RUNNING).build();
        Series series2 = Series.builder()
                .title("zoli").description("z").status(Status.RUNNING).build();
        Series series3 = Series.builder()
                .title("anita").description("a").status(Status.RUNNING).build();

        Episode episode1 = Episode.builder()
                .title("baszó").releaseDate(new Date()).runtime(5).episodeNumber(1).build();
        Episode episode2 = Episode.builder()
                .title("fantom").releaseDate(new Date()).runtime(10).episodeNumber(2).build();
        Episode episode3 = Episode.builder()
                .title("bögöly").releaseDate(new Date()).runtime(15).episodeNumber(3).build();

        User user = User.builder()
                .userName("oli").emailAddress("oli").password("oli").registrationDate(new Date()).build();
        user.addWatchedEpisodes(episode1);
        user.addWatchedEpisodes(episode2);
        user.addWatchedEpisodes(episode3);
        user.addSeriesToFavouriteList(series1);
        user.addSeriesToFavouriteList(series2);
        user.addSeriesToWatchList(series3);

        em.persist(user);
        transactionTest.commit();

        for (int i = 0; i < tvShow.getTvShowList().size(); i++) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            Episode episode;
            Season season;

            TvShow currentShow = tvShow.getTvShowList().get(i).getTvShow();

            Series series = Series.builder().title(currentShow.getName()).description(currentShow.getDescription()).
                    airDate(init.formatStringToDate(currentShow.getStart_date())).status(Status.valueOf(currentShow.getStatus().
                    replace(" ", "_").toUpperCase())).genres((init.convertStringToEnumGenre(currentShow.getGenres()))).build();

            Set<Integer> seasonSet = new HashSet<>();

            for (int j = 0; j < currentShow.getEpisodes().length ; j++) {
                seasonSet.add(Integer.parseInt(currentShow.getEpisodes()[j].getSeason()));
            }

            for (int j = 0; j < seasonSet.size(); j++) {
                season = Season.builder().title("Season").year(init.formatStringToDate(currentShow.
                        getEpisodes()[j].getAir_date())).seasonNumber(j + 1).build();

                series.addSeason(season);

                    for (int k = 0; k < currentShow.getEpisodes().length; k++) {
                        if (Integer.parseInt(currentShow.getEpisodes()[k].getSeason()) == season.getSeasonNumber()) {
                            episode = Episode.builder().title(currentShow.getEpisodes()[k].getName()).
                                releaseDate(init.formatStringToDate(currentShow.getEpisodes()[k].getAir_date())).
                                runtime(Integer.parseInt(currentShow.getRuntime())).
                                episodeNumber(Integer.parseInt(currentShow.getEpisodes()[k].getEpisode())).build();
                            season.addEpisode(episode);
                            em.persist(episode);
                    }
                }
                em.persist(season);
            }
            em.persist(series);
            transaction.commit();
        }
    }

    public static void main(String[] args) throws IOException {
        EntityManagerFactory entityManager =  EMFManager.getInstance();
        EntityManager em = entityManager.createEntityManager();

        populateDb(em);
        em.clear();
        em.close();
        entityManager.close();
    }
}
