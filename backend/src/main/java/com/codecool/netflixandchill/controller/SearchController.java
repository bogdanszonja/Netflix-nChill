//package com.codecool.netflixandchill.controller;
//
//import com.codecool.netflixandchill.dao.EpisodeDao;
//import com.codecool.netflixandchill.dao.UserDao;
//import com.google.gson.JsonObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class SearchController extends HttpServlet {
//    //TODO********************************************************************************
//
//    private RequestParser requestParser;
//    private JsonCreator jsonCreator;
//    private SessionManager sessionManager;
//    private EpisodeDao episodeDao;
//    private UserDao userDao;
//    private Logger logger = LoggerFactory.getLogger(LoginController.class);
//
//
//
//    public SearchController(RequestParser rp, JsonCreator jc, SessionManager sm,
//                            EpisodeDao episodeDao, UserDao userDao) {
//        this.requestParser = rp;
//        this.jsonCreator = jc;
//        this.sessionManager = sm;
//        this.episodeDao = episodeDao;
//        this.userDao = userDao;
//    }
//
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        JsonObject answer = new JsonObject();
//        String searchTerm = request.getParameter("searchTerm");
//
//        if (jsonCreator.findSeriesBySubstring(searchTerm) == null) {
//            JsonObject error = new JsonObject();
//            error.addProperty("message", "Series not found");
//            answer.add("error", error);
//            response.setStatus(404);
//        } else {
//            answer.add("data", jsonCreator.findSeriesBySubstring(searchTerm));
//        }
//
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(answer.toString());
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//    }
//
//}
