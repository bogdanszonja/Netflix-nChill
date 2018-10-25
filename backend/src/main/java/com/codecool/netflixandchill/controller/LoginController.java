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
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginController extends HttpServlet {

    private RequestParser requestParser;
    private JsonCreator jsonCreator;
    private SessionManager sessionManager;
    private UserDao userDao;
    private Logger logger = LoggerFactory.getLogger(LoginController.class);


    public LoginController(RequestParser rp, JsonCreator jc, SessionManager sm,
                           UserDao userDao) {
        this.requestParser = rp;
        this.jsonCreator = jc;
        this.sessionManager = sm;
        this.userDao = userDao;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Get request");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Post request");
        HttpSession session = sessionManager.getHttpSession(request);
        JsonObject jsonObject = requestParser.getJsonObject(request);
        logger.info("Data from request: " + jsonObject);
        JsonObject invalidLogin = new JsonObject();
        invalidLogin.addProperty("success", false);
        String userJson = invalidLogin.toString();

//        if (session.getAttribute("userId") == null) {
//            return;
//        }

        String email = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();

        JsonObject answer = new JsonObject();
        if (userDao.validLogin(email, password)) {
            logger.info("Valid login");
            session.setAttribute("userId", userDao.find(email).getId());
            logger.info("UserId: " + session.getAttribute("userId"));
            answer.add("data", jsonCreator.findUserByEmail(email));
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer.toString());
    }
}
