package com.codecool.netflixandchill.dao.implementation;

import com.codecool.netflixandchill.dao.EpisodeDao;
import com.codecool.netflixandchill.model.Episode;
import com.codecool.netflixandchill.util.TransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class EpisodeDaoDB implements EpisodeDao {

    private TransactionManager transactionManager;
    private EntityManagerFactory emfManager;


    public EpisodeDaoDB(TransactionManager transactionManager, EntityManagerFactory emfManager) {
        this.transactionManager = transactionManager;
        this.emfManager = emfManager;
    }


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
                        "FROM Episode e WHERE UPPER(e.title) LIKE UPPER('%' || :param || '%')", Episode.class)
                .setParameter("param", substring)
                .getResultList();
        em.close();

        return result;
    }
}
