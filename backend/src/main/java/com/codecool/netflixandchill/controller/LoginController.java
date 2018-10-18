package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.dao.UserDao;
import com.codecool.netflixandchill.dao.implementation.UserDaoDB;
import com.codecool.netflixandchill.util.JsonCreator;
import com.codecool.netflixandchill.util.RequestParser;
import com.codecool.netflixandchill.util.SessionManager;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private UserDao userDaoDB = UserDaoDB.getInstance();
    private SessionManager sessionManager = SessionManager.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        HttpSession session = sessionManager.getHttpSession(request);
        JsonObject jsonObject = RequestParser.getInstance().getJsonObject(request);
        JsonCreator jsonCreator = JsonCreator.getInstance();
        JsonObject invalidLogin = new JsonObject();
        invalidLogin.addProperty("success", false);
        String userJson = invalidLogin.toString();

//        if (session == null) {
//            response.sendRedirect("/login");
//            return;
//        }

        String email = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();

        if (userDaoDB.validLogin(email, password)) {
//            session.setAttribute("userId", userDaoDB.find(email).getId());
            userJson = jsonCreator.findUserByEmail(email);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(userJson);
    }
}
