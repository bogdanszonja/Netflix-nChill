package com.codecool.netflixandchill.config;

import com.codecool.netflixandchill.json.Show;
import com.codecool.netflixandchill.json.TvShow;
import com.codecool.netflixandchill.model.Episode;
import com.codecool.netflixandchill.model.Season;
import com.codecool.netflixandchill.model.Series;
import com.codecool.netflixandchill.model.Status;
import com.codecool.netflixandchill.util.EMFManager;
import com.codecool.netflixandchill.util.RemoteURLReader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

public class InitializerDB {
    public static void populateDb(EntityManager em) throws IOException, ParseException {

        RemoteURLReader urlReader = new RemoteURLReader();
        Initializer init = new Initializer(urlReader);
        Show tvShow = new Show();
        init.addTvShowToMyTvSHowsList();

        for (int i = 0; i < tvShow.getTvShowList().size(); i++) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            Episode episode;
            Season season;

            TvShow currentShow = tvShow.getTvShowList().get(i).getTvShow();

            Series series = Series.builder().title(currentShow.getName()).description("").
                    airDate(init.formatStringToDate(currentShow.getStart_date())).status(Status.valueOf(currentShow.getStatus().
                    replace(" ", "_").toUpperCase())).genres((init.convertStringToEnumGenre(currentShow.getGenres()))).build();

            Set<Integer> seasonSet = new HashSet<>();

            for (int j = 0; j < currentShow.getEpisodes().length ; j++) {
                seasonSet.add(Integer.parseInt(currentShow.getEpisodes()[j].getSeason()));
            }

            for (int j = 0; j < seasonSet.size(); j++) {
                season = Season.builder().title("Season").description("").year(init.formatStringToDate(currentShow.
                        getEpisodes()[j].getAir_date())).serialNumber(j + 1).build();

                series.addSeason(season);

                    for (int k = 0; k < currentShow.getEpisodes().length; k++) {
                        if (Integer.parseInt(currentShow.getEpisodes()[k].getSeason()) == season.getSerialNumber()) {
                            episode = Episode.builder().title(currentShow.getEpisodes()[k].getName()).description("").
                                releaseDate(init.formatStringToDate(currentShow.getEpisodes()[k].getAir_date())).
                                runtime(Integer.parseInt(currentShow.getRuntime())).
                                serialNumber(Integer.parseInt(currentShow.getEpisodes()[k].getEpisode())).build();
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

    public static void main(String[] args) throws IOException, ParseException {
        EntityManagerFactory entityManager =  EMFManager.getInstance();
        EntityManager em = entityManager.createEntityManager();

        populateDb(em);
        em.clear();
        em.close();
        entityManager.close();
    }
}
