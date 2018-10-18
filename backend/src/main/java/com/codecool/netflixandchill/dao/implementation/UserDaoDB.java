package com.codecool.netflixandchill.dao.implementation;

import com.codecool.netflixandchill.dao.UserDao;
import com.codecool.netflixandchill.model.Episode;
import com.codecool.netflixandchill.model.Series;
import com.codecool.netflixandchill.model.User;
import com.codecool.netflixandchill.util.EMFManager;
import com.codecool.netflixandchill.util.TransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Collection;
import java.util.List;

public class UserDaoDB implements UserDao {

    private TransactionManager transactionManager = TransactionManager.getInstance();
    private EntityManagerFactory emfManager = EMFManager.getInstance();
    private static UserDaoDB instance = null;

    public static UserDaoDB getInstance() {
        if (instance == null) {
            instance = new UserDaoDB();
        }
        return instance;
    }

    private UserDaoDB() {}

    @Override
    public void add(User user) {
        EntityManager em = emfManager.createEntityManager();
        transactionManager.addToTable(em, user);
        em.close();
    }

    @Override
    public User find(long userId) {
        EntityManager em = emfManager.createEntityManager();
        User user = em.find(User.class, userId);
        em.close();
        Collection<Episode> watchedEpisodes = user.getWatchedEpisodes();
        System.out.println(watchedEpisodes.size());
        return user;
    }

    @Override
    public User find(String email) {
        EntityManager em = emfManager.createEntityManager();

        List<User> result = em.createQuery(
                "SELECT u " +
                    "FROM User u " +
                    "WHERE u.emailAddress = :email", User.class)
                .setParameter("email", email)
                .getResultList();
        Collection<Episode> watchedEpisodes = result.get(0).getWatchedEpisodes();
        System.out.println(watchedEpisodes.size());
        em.close();
        return (result.size() != 0) ? result.get(0): null;
    }

    @Override
    public void remove(long userId) {
        EntityManager em = emfManager.createEntityManager();
        em.remove(userId);
        em.close();
    }

    @Override
    public List<User> getAll() {
        EntityManager em = emfManager.createEntityManager();

        List<User> result = em.createQuery(
                "SELECT u " +
                    "FROM Series u ", User.class)
                .getResultList();
        em.close();
        return result;
    }

    @Override
    public void addEpisodeToWatchedList(long episodeId, long userId) {
        EntityManager em = emfManager.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        User user = em.find(User.class, userId);
        Episode episode = em.find(Episode.class, episodeId);
        user.getWatchedEpisodes().add(episode);
        em.persist(user);
        transaction.commit();

        em.close();
    }

    @Override
    public void addSeriesToWatchList(long episodeId, long userId) {
        EntityManager em = emfManager.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        User user = em.find(User.class, userId);
        Series series = em.find(Series.class, episodeId);
        user.getWatchlist().add(series);
        em.persist(user);
        transaction.commit();

        em.close();
    }

    @Override
    public void addSeriesToFavouriteList(long episodeId, long userId) {
        EntityManager em = emfManager.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        User user = em.find(User.class, userId);
        Series series = em.find(Series.class, episodeId);
        user.getFavourites().add(series);
        em.persist(user);
        transaction.commit();

        em.close();
    }

    @Override
    public boolean validRegister(String email, String password, String confirmedPassword) {
        User user = find(email);

        return (user == null) && (password.equals(confirmedPassword));
    }

    @Override
    public boolean validLogin(String email, String password) {
        User user = find(email);

        return (user != null) && (user.getPassword().equals(password));
    }

    @Override
    public List<Episode> getWatchedEpisodesById(long userId) {
        EntityManager em = emfManager.createEntityManager();

        List<Episode> result = em.createQuery(
                "SELECT e " +
                    "FROM Episode e INNER JOIN e.users u " +
                    "WHERE u.id = :userId", Episode.class)
                .setParameter("userId", userId)
                .getResultList();
        em.close();
        return result;
    }
}
