package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.dao.EpisodeDao;
import com.codecool.netflixandchill.dao.FakeEpisode;
import com.codecool.netflixandchill.dao.UserDao;
import com.codecool.netflixandchill.dao.implementation.EpisodeDaoDB;
import com.codecool.netflixandchill.dao.implementation.SeriesDaoDB;
import com.codecool.netflixandchill.dao.implementation.UserDaoDB;
import com.codecool.netflixandchill.model.Series;
import com.codecool.netflixandchill.util.JsonCreator;
import com.codecool.netflixandchill.util.RequestParser;
import com.codecool.netflixandchill.util.SessionManager;
import com.google.gson.JsonObject;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/search"})
public class SearchPageController extends HttpServlet {
    private SessionManager sessionManager = SessionManager.getInstance();
    private EpisodeDao episodeDao = EpisodeDaoDB.getInstance();
    private UserDao userDao = UserDaoDB.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
//        WebContext context = new WebContext(request, response, request.getServletContext());
//
//        HttpSession session = sessionManager.getHttpSession(request);
//
//        if (session != null) context.setVariable("userId", session.getAttribute("userId"));
//
//        String searchWord = request.getParameter("search");
//
//        List<FakeEpisode> searchedEpisodes = episodeDao.findBySubstring(searchWord);
//        context.setVariable("episodes", searchedEpisodes);
//
//        engine.process("search.html", context, response.getWriter());



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String searchTerm = request.getParameter("searchTerm");
        String result = JsonCreator.getInstance().findSeriesBySubstring(searchTerm);

        if(result == null) {
            result = "NoSeriesFound";
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);
    }

}
