package com.codecool.netflixandchill.dao.implementation;

import com.codecool.netflixandchill.dao.UserDao;
import com.codecool.netflixandchill.model.Episode;
import com.codecool.netflixandchill.model.Season;
import com.codecool.netflixandchill.model.Series;
import com.codecool.netflixandchill.model.User;
import com.codecool.netflixandchill.util.TransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Collection;
import java.util.List;

public class UserDaoDB implements UserDao {

    private TransactionManager transactionManager;
    private EntityManagerFactory emfManager;


    public UserDaoDB(TransactionManager transactionManager, EntityManagerFactory emfManager) {
        this.transactionManager = transactionManager;
        this.emfManager = emfManager;
    }


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
        return user;
    }

    public long getIdFromUser(User user) {
        return user.getId();
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
        user.addSeriesToWatchList(series);
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
        user.addSeriesToFavouriteList(series);
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
    public boolean checkIfUserNameAlreadyExists(String userName) {
        EntityManager em = emfManager.createEntityManager();

        List<User> result = em.createQuery(
                "SELECT u " +
                        "FROM User u " +
                        "WHERE u.userName = :userName", User.class)
                .setParameter("userName", userName)
                .getResultList();
        em.close();
        return (result.size() != 0) ? true: false;
    }

    @Override
    public boolean checkIfEmailAlreadyExists(String email) {
        User user = find(email);

        return (user != null);
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

    @Override
    public void addSeasonEpisodeToWatchedList(long seasonId, long userId) {
        EntityManager em = emfManager.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        User user = em.find(User.class, userId);
        Season season = em.find(Season.class, seasonId);
        for (Episode episode : season.getEpisodes()) {
            user.addWatchedEpisodes(episode);
            em.persist(user);
        }
        transaction.commit();

        em.close();
    }

    @Override
    public void addSeriesEpisodeToWatchedList(long seriesId, long userId) {
        EntityManager em = emfManager.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        User user = em.find(User.class, userId);
        Series series = em.find(Series.class, seriesId);
        for (Season season : series.getSeasons()) {
            for (Episode episode : season.getEpisodes()) {
                user.addWatchedEpisodes(episode);
                em.persist(user);
            }
        }
        transaction.commit();

        em.close();
    }
}
