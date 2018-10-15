package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.dao.UserDao;
import com.codecool.netflixandchill.dao.implementation.UserDaoDB;
import com.codecool.netflixandchill.model.User;
import com.codecool.netflixandchill.util.SessionManager;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    private UserDao userDaoDB = UserDaoDB.getInstance();
    private SessionManager sessionManager = SessionManager.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
//        WebContext context = new WebContext(request, response, request.getServletContext());
//
//        engine.process("register.html", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = sessionManager.getHttpSession(request);

        if (session == null) {
            response.sendRedirect("/register");
            return;
        }

        String userName = request.getParameter("register_username");
        String email = request.getParameter("register_email");
        String password = request.getParameter("register_password");
        String confirmedPassword = request.getParameter("password_confirm");

        if (userDaoDB.validRegister(email, password, confirmedPassword)) {
            userDaoDB.add(User.builder().userName(userName).emailAddress(email).password(password).registrationDate(new Date()).build());
            response.sendRedirect("/login");
            return;
        }

        response.sendRedirect("/register");
    }

}

