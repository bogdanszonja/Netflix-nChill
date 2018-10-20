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

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EpisodeDao episodeDao = new EpisodeDaoDB();
        SeasonDao seasonDao = new SeasonDaoDB();
        SeriesDao seriesDao = new SeriesDaoDB();
        UserDao userDao = new UserDaoDB();

        ServletContext context = sce.getServletContext();
        context.addServlet("Series", new SeriesController(seriesDao)).addMapping("/series");
        context.addServlet("Search", new SearchController(seriesDao)).addMapping("/search");
        context.addServlet("Join", new JoinController(seriesDao)).addMapping("/join");
        context.addServlet("Login", new LoginController(seriesDao)).addMapping("/login");
        context.addServlet("User", new UserController(seriesDao, userDao)).addMapping("/user");
    }
}
