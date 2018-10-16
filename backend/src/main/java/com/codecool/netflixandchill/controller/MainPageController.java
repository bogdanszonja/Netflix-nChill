package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.dao.EpisodeDao;
import com.codecool.netflixandchill.dao.UserDao;
import com.codecool.netflixandchill.dao.implementation.EpisodeDaoDB;
import com.codecool.netflixandchill.dao.implementation.UserDaoDB;
import com.codecool.netflixandchill.model.*;
import com.codecool.netflixandchill.util.EMFManager;
import com.codecool.netflixandchill.util.JsonUtil;
import com.codecool.netflixandchill.util.SessionManager;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

@WebServlet(urlPatterns = {"/"})
public class MainPageController extends HttpServlet {

    private static boolean firstVisit = true;
    private EpisodeDao episodeDao = EpisodeDaoDB.getInstance();
    private UserDao userDao = UserDaoDB.getInstance();
    private SessionManager sessionManager = SessionManager.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
//        WebContext context = new WebContext(request, response, request.getServletContext());
//
//        if (firstVisit) {
//            this.populateDb();
//            firstVisit = false;
//        }
//
//        context.setVariable("episodes", episodeDao.getAll());
//
//        engine.process("index.html", context, response.getWriter());
        JsonUtil.getInstance().getAllShows();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
//        WebContext context = new WebContext(request, response, request.getServletContext());
//
//        HttpSession session = sessionManager.getHttpSessionRedirect(request);
//
//        if (session == null) {
//            response.sendRedirect("/");
//            return;
//        }
//
//        userDao.addEpisode(episodeDao.find(Long.parseLong(request.getParameter("episode"))).getId(),
//                (long) session.getAttribute("userId"));
//
//        context.setVariable("episodes", episodeDao.getAll());
//
//        engine.process("index.html", context, response.getWriter());
    }

}
