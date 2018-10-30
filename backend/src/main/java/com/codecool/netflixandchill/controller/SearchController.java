package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.dao.EpisodeDao;
import com.codecool.netflixandchill.dao.UserDao;
import com.codecool.netflixandchill.util.JsonCreator;
import com.codecool.netflixandchill.util.RequestParser;
import com.codecool.netflixandchill.util.SessionManager;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SearchController extends HttpServlet {

    private RequestParser requestParser;
    private JsonCreator jsonCreator;
    private SessionManager sessionManager;
    private EpisodeDao episodeDao;
    private UserDao userDao;


    public SearchController(RequestParser rp, JsonCreator jc, SessionManager sm,
                            EpisodeDao episodeDao, UserDao userDao) {
        this.requestParser = rp;
        this.jsonCreator = jc;
        this.sessionManager = sm;
        this.episodeDao = episodeDao;
        this.userDao = userDao;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject answer = new JsonObject();
        String searchTerm = request.getParameter("searchTerm");

        if (jsonCreator.findSeriesBySubstring(searchTerm).size() == 0) {
            answer.add("data", jsonCreator.findSeriesBySubstring(searchTerm));
        } else {
            JsonObject error = new JsonObject();
            error.addProperty("message", "Series not found");
            answer.add("error", error);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

}
