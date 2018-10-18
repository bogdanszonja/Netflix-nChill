package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.dao.UserDao;
import com.codecool.netflixandchill.dao.implementation.UserDaoDB;
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

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private UserDao userDaoDB = UserDaoDB.getInstance();
    private SessionManager sessionManager = SessionManager.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
//        WebContext context = new WebContext(request, response, request.getServletContext());
//
//        engine.process("login.html", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = sessionManager.getHttpSession(request);
        JsonObject jsonObject = RequestParser.getInstance().getJsonObject(request);
        JsonCreator jsonCreator = JsonCreator.getInstance();
        JsonObject user = new JsonObject();

        if (session == null) {
            response.sendRedirect("/login");
            return;
        }

        String email = jsonObject.get("loginEmail").getAsString();
        String password = jsonObject.get("loginPassword").getAsString();

        if (userDaoDB.validLogin(email, password)) {
            session.setAttribute("userId", userDaoDB.find(email).getId());
            user.addProperty("user", jsonCreator.findUserByEmail(email));

        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(user.toString());
    }
}
