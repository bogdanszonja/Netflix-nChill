package com.codecool.netflixandchill.dao.implementation;

import com.codecool.netflixandchill.dao.SeasonDao;
import com.codecool.netflixandchill.model.Season;
import com.codecool.netflixandchill.util.EMFManager;
import com.codecool.netflixandchill.util.TransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class SeasonDaoDB implements SeasonDao {
    private TransactionManager transactionManager = TransactionManager.getInstance();
    private EntityManagerFactory emfManager = EMFManager.getInstance();
    private static SeasonDaoDB instance = null;

    public static SeasonDaoDB getInstance() {
        if (instance == null) {
            instance = new SeasonDaoDB();
        }
        return instance;
    }

    private SeasonDaoDB() {}

    @Override
    public void add(Season season) {
        EntityManager em = emfManager.createEntityManager();
        transactionManager.addToTable(em, season);
        em.close();
    }

    @Override
    public Season find(long seasonId) {
        EntityManager em = emfManager.createEntityManager();
        Season season = em.find(Season.class, seasonId);
        em.close();
        return season;
    }

    @Override
    public List<Season> getAll() {
        EntityManager em = emfManager.createEntityManager();

        List<Season> result = em.createQuery(
                "SELECT s " +
                        "FROM Season s ", Season.class)
                .getResultList();
        em.close();
        return result;
    }
}
