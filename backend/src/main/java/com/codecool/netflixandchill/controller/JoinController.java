package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.dao.UserDao;
import com.codecool.netflixandchill.model.User;
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
import java.util.*;

public class JoinController extends HttpServlet {

    private RequestParser requestParser;
    private JsonCreator jsonCreator;
    private SessionManager sessionManager;
    private UserDao userDao;
    private Logger logger = LoggerFactory.getLogger(JoinController.class);


    public JoinController(RequestParser rp, JsonCreator jc, SessionManager sm,
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
        JsonObject jsonObject = requestParser.getJsonObject(request);
        logger.info("Data from request: " + jsonObject);
//        HttpSession session = sessionManager.getHttpSession(request);
//
//        if (session == null) {
//            response.sendRedirect("/register");
//            return;
//        }

        String userName = jsonObject.get("username").getAsString();
        String email = jsonObject.get("email").getAsString();
        String password = jsonObject.get("password").getAsString();
        String confirmedPassword = jsonObject.get("confirmPassword").getAsString();

        JsonObject answer = new JsonObject();

        if (userDao.validRegister(email, password, confirmedPassword)) {
            logger.info("Valid registration");
            userDao.add(User.builder().userName(userName).emailAddress(email).password(password).registrationDate(new Date()).build());
            answer.addProperty("success", true);
        } else if (userDao.checkIfEmailAlreadyExists(email)){
            answer.addProperty("error", "email address already exists");
            response.setStatus(400);
        } else if (password != confirmedPassword){
            answer.addProperty("error", "password and confirmed password are not the same");
            response.setStatus(400);
        } else if(userDao.checkIfUserNameAlreadyExists(userName)) {
            answer.addProperty("error", "username already exists");
            response.setStatus(400);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer.toString());
    }

}

