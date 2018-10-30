package com.codecool.netflixandchill.dao;

import com.codecool.netflixandchill.dao.implementation.UserDaoDB;
import com.codecool.netflixandchill.util.TransactionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDaoTest {

    private UserDao userDao;
    private TransactionManager transactionManager;
    private EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        transactionManager = mock(TransactionManager.class);
        emf = mock(EntityManagerFactory.class);
        userDao = new UserDaoDB(transactionManager, emf);
    }

    @Test
    void testFindThrowsIllegalArgumentExceptionWithInvalidId() {
        when(emf.createEntityManager()).thenReturn(mock(EntityManager.class));
        assertThrows(IllegalArgumentException.class, ()-> userDao.find(-1));
    }

    @Test
    void testFindThrowsIllegalArgumentExceptionWithInvalidEmail() {
        when(emf.createEntityManager()).thenReturn(mock(EntityManager.class));
        assertThrows(IllegalArgumentException.class, ()-> userDao.find(""));
    }

    @Test
    void testAddEpisodeToWatchedListThrowsIllegalArgumentExceptionWithInvalidEpisodeId() {
        when(emf.createEntityManager()).thenReturn(mock(EntityManager.class));
        assertThrows(IllegalArgumentException.class, ()-> userDao.addEpisodeToWatchedList(-1, 1));
    }

    @Test
    void testAddEpisodeToWatchedListThrowsIllegalArgumentExceptionWithInvalidUserId() {
        when(emf.createEntityManager()).thenReturn(mock(EntityManager.class));
        assertThrows(IllegalArgumentException.class, ()-> userDao.addEpisodeToWatchedList(1, -1));
    }

    @Test
    void testAddSeriesToWatchListThrowsIllegalArgumentExceptionWithInvalidSeriesId() {
        when(emf.createEntityManager()).thenReturn(mock(EntityManager.class));
        assertThrows(IllegalArgumentException.class, ()-> userDao.addSeriesToWatchList(-1, 1));
    }

    @Test
    void testAddSeriesToWatchListThrowsIllegalArgumentExceptionWithInvalidUserId() {
        when(emf.createEntityManager()).thenReturn(mock(EntityManager.class));
        assertThrows(IllegalArgumentException.class, ()-> userDao.addSeriesToWatchList(1, -1));
    }

    @Test
    void testAddSeriesToFavouriteListThrowsIllegalArgumentExceptionWithInvalidSeriesId() {
        when(emf.createEntityManager()).thenReturn(mock(EntityManager.class));
        assertThrows(IllegalArgumentException.class, ()-> userDao.addSeriesToFavouriteList(-1, 1));
    }

    @Test
    void testAddSeriesToFavouriteListThrowsIllegalArgumentExceptionWithInvalidUserId() {
        when(emf.createEntityManager()).thenReturn(mock(EntityManager.class));
        assertThrows(IllegalArgumentException.class, ()-> userDao.addSeriesToFavouriteList(1, -1));
    }

    @Test
    void testValidRegisterThrowsIllegalArgumentExceptionWithWrongConfirmation() {
        assertThrows(IllegalArgumentException.class, ()-> userDao.validRegister("a", "12345", "54321"));
    }

    @Test
    void testValidLoginThrowsIllegalArgumentExceptionWithInvalidPassword() {
        assertThrows(IllegalArgumentException.class, ()-> userDao.validLogin( "a", ""));
    }

    @Test
    void testGetWatchedEpisodesByIdThrowsIllegalArgumentExceptionWithInvalidId() {
        when(emf.createEntityManager()).thenReturn(mock(EntityManager.class));
        assertThrows(IllegalArgumentException.class, ()-> userDao.getWatchedEpisodesById(-1));
    }

    @Test
    void testAddSeasonEpisodeToWatchedListThrowsIllegalArgumentExceptionWithInvalidEpisodeId() {
        when(emf.createEntityManager()).thenReturn(mock(EntityManager.class));
        assertThrows(IllegalArgumentException.class, ()-> userDao.addSeasonEpisodeToWatchedList(-1, 1));
    }

    @Test
    void testAddSeasonEpisodeToWatchedListThrowsIllegalArgumentExceptionWithInvalidUserId() {
        when(emf.createEntityManager()).thenReturn(mock(EntityManager.class));
        assertThrows(IllegalArgumentException.class, ()-> userDao.addSeasonEpisodeToWatchedList(1, -1));
    }

    @Test
    void testAddSeriesEpisodeToWatchedListThrowsIllegalArgumentExceptionWithInvalidSeriesId() {
        when(emf.createEntityManager()).thenReturn(mock(EntityManager.class));
        assertThrows(IllegalArgumentException.class, ()-> userDao.addSeriesEpisodeToWatchedList(-1, -1));
    }

    @Test
    void testAddSeriesEpisodeToWatchedListThrowsIllegalArgumentExceptionWithInvalidUserId() {
        when(emf.createEntityManager()).thenReturn(mock(EntityManager.class));
        assertThrows(IllegalArgumentException.class, ()-> userDao.addSeriesEpisodeToWatchedList(1, -1));
    }
}