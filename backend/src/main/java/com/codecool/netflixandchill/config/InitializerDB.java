package com.codecool.netflixandchill.config;

import com.codecool.netflixandchill.json.Show;
import com.codecool.netflixandchill.json.TvShow;
import com.codecool.netflixandchill.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class InitializerDB {

    private EntityManagerFactory emf;
    private Initializer init;
    private Show tvShow;


    public InitializerDB(EntityManagerFactory emf, Initializer init, Show tvShow) {
        this.emf = emf;
        this.init = init;
        this.tvShow = tvShow;
    }


    public void populateDb() throws IOException {

        EntityManager em = emf.createEntityManager();

        init.addTvShowToMyTvSHowsList();

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
                season = Season.builder().title("Season " + (j + 1)).year(init.formatStringToDate(currentShow.
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
        em.clear();
        em.close();
    }

}
