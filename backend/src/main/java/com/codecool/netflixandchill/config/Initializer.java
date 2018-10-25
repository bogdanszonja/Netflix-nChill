package com.codecool.netflixandchill.config;

import com.codecool.netflixandchill.controller.*;
import com.codecool.netflixandchill.dao.EpisodeDao;
import com.codecool.netflixandchill.dao.SeasonDao;
import com.codecool.netflixandchill.dao.SeriesDao;
import com.codecool.netflixandchill.dao.UserDao;
import com.codecool.netflixandchill.dao.implementation.EpisodeDaoDB;
import com.codecool.netflixandchill.dao.implementation.SeasonDaoDB;
import com.codecool.netflixandchill.dao.implementation.SeriesDaoDB;
import com.codecool.netflixandchill.dao.implementation.UserDaoDB;
import com.codecool.netflixandchill.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;

@WebListener
public class Initializer implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(Initializer.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EntityManagerFactory emf = EMFManager.getInstance();
        RemoteURLReader remoteURLReader = new RemoteURLReader();

        InitializerDB initializerDB = new InitializerDB(remoteURLReader, emf);

        try {
            initializerDB.populateDB();
        } catch (IOException e) {
            logger.info("Error in initializerDB!");
            e.printStackTrace();
        }

        SessionManager sessionManager = new SessionManager();
        RequestParser requestParser = new RequestParser();
        TransactionManager transactionManager = new TransactionManager();

        EpisodeDao episodeDao = new EpisodeDaoDB(transactionManager, emf);
        SeasonDao seasonDao = new SeasonDaoDB(transactionManager, emf);
        SeriesDao seriesDao = new SeriesDaoDB(transactionManager, emf);
        UserDao userDao = new UserDaoDB(transactionManager, emf);

        JsonCreator jsonCreator = new JsonCreator(episodeDao, seasonDao, seriesDao, userDao);

        ServletContext context = sce.getServletContext();
        context.addServlet("Join",
                new JoinController(requestParser, jsonCreator, sessionManager, userDao))
                .addMapping("/join");
        context.addServlet("Login",
                new LoginController(requestParser, jsonCreator, sessionManager, userDao))
                .addMapping("/login");
        context.addServlet("Search",
                new SearchController(requestParser, jsonCreator, sessionManager, episodeDao, userDao))
                .addMapping("/search");
        context.addServlet("Series",
                new SeriesController(requestParser, jsonCreator, sessionManager))
                .addMapping("/series");
        context.addServlet("User",
                new UserController(requestParser, jsonCreator, sessionManager, userDao))
                .addMapping("/user");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.warn("Modification happened, server restarting...");
    }
}
