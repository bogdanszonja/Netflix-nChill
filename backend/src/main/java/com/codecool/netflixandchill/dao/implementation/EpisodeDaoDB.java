package com.codecool.netflixandchill.dao.implementation;

import com.codecool.netflixandchill.dao.EpisodeDao;
import com.codecool.netflixandchill.dao.FakeEpisode;
import com.codecool.netflixandchill.model.Episode;
import com.codecool.netflixandchill.util.EMFManager;
import com.codecool.netflixandchill.util.TransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class EpisodeDaoDB implements EpisodeDao {
    private TransactionManager transactionManager = TransactionManager.getInstance();
    private EntityManagerFactory emfManager = EMFManager.getInstance();
    private static EpisodeDaoDB instance = null;

    public static EpisodeDaoDB getInstance() {
        if (instance == null) {
            instance = new EpisodeDaoDB();
        }
        return instance;
    }

    private EpisodeDaoDB() {}

    @Override
    public void add(Episode episode) {
        EntityManager em = emfManager.createEntityManager();
        transactionManager.addToTable(em, episode);
        em.close();
    }

    @Override
    public Episode find(long episodeId) {
        EntityManager em = emfManager.createEntityManager();
        Episode episode = em.find(Episode.class, episodeId);
        em.close();
        return episode;
    }

    @Override
    public List<Episode> getAll() {
        EntityManager em = emfManager.createEntityManager();
        List<Episode> result = em.createQuery(
                "SELECT e " +
                        "FROM Episode e ", Episode.class)
                .getResultList();
        em.close();
        return result;
    }

    @Override
    public List<Episode> findBySubstring(String substring) {
        EntityManager em = emfManager.createEntityManager();
        List<Episode> result = em.createQuery(
                "SELECT e " +
                        "FROM Episode e WHERE e.title LIKE '%' || :param || '%'", Episode.class)
                .setParameter("param", substring)
                .getResultList();
        em.close();

        List<FakeEpisode> fakeEpisodes = new ArrayList<>();

        for (Episode episode : result) {
            fakeEpisodes.add(new FakeEpisode(episode));
        }

        return null;
    }
}
