package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.dao.UserDao;
import com.codecool.netflixandchill.dao.implementation.UserDaoDB;
import com.codecool.netflixandchill.util.SessionManager;
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

        if (session == null) {
            response.sendRedirect("/login");
            return;
        }

        String email = request.getParameter("login_email");
        String password = request.getParameter("login_password");

        if (userDaoDB.validLogin(email, password)) {
            session.setAttribute("userId", userDaoDB.find(email).getId());
            response.sendRedirect("/user-page");
            return;
        }

        response.sendRedirect("/login");
    }
}
