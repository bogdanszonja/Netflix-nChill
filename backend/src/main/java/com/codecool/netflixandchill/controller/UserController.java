package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.dao.UserDao;
import com.codecool.netflixandchill.util.JsonCreator;
import com.codecool.netflixandchill.util.RequestParser;
import com.codecool.netflixandchill.util.SessionManager;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserController extends HttpServlet {

    private RequestParser requestParser;
    private JsonCreator jsonCreator;
    private SessionManager sessionManager;
    private UserDao userDao;
    private Logger logger = LoggerFactory.getLogger(UserController.class);


    public UserController(RequestParser rp, JsonCreator jc, SessionManager sm,
                          UserDao userDao) {
        this.requestParser = rp;
        this.jsonCreator = jc;
        this.sessionManager = sm;
        this.userDao = userDao;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Get request");
        JsonObject answer = new JsonObject();
        answer.add("data", jsonCreator.findUserById(Long.parseLong(request.getParameter("userId"))));
        logger.info("Answer to send back: " + answer);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Post request");
//        HttpSession session = sessionManager.getHttpSession(request);
        JsonObject jsonObject = requestParser.getJsonObject(request);
        logger.info("Data from request: " + jsonObject);
        String jsonObjectKey = "";
        long userId = jsonObject.get("userId").getAsLong();
        long id;

        for (String key : jsonObject.keySet()) {
            jsonObjectKey = key;
        }
        logger.info("JsonObjectKey: " + jsonObjectKey);

//        System.out.println(request.getSession().getAttribute("userId"));
//        if (session.getAttribute("userId") == null) {
//            System.out.println("pina");
//            return;
//        }

//        int userId = (Integer) session.getAttribute("userId");

        switch (jsonObjectKey) {
            case "episode":
                logger.info("Switch-case: episode");
                id = jsonObject.get("episode").getAsLong();
                userDao.addEpisodeToWatchedList(id, userId);

                break;
            case "favourite":
                logger.info("Switch-case: favourite");
                id = jsonObject.get("favourite").getAsLong();
                userDao.addSeriesToFavouriteList(id, userId);

                break;
            case "watchlist":
                logger.info("Switch-case: watchlist");
                id = jsonObject.get("watchlist").getAsLong();
                userDao.addSeriesToWatchList(id, userId);

                break;
            case "season":
                logger.info("Switch-case: season");
                id = jsonObject.get("season").getAsLong();
                userDao.addSeasonEpisodeToWatchedList(id, userId);

                break;
            case "series":
                logger.info("Switch-case: series");
                id = jsonObject.get("series").getAsLong();
                userDao.addSeriesEpisodeToWatchedList(id, userId);
                break;
        }


        JsonObject answer = new JsonObject();
        answer.add("data", jsonCreator.findUserById(userId));
        logger.info("Answer to send back: " + answer);
//        answer.addProperty("success", true);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer.toString());
    }

}
