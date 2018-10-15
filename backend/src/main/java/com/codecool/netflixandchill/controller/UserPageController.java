package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.dao.UserDao;
import com.codecool.netflixandchill.dao.implementation.UserDaoDB;
import com.codecool.netflixandchill.model.Episode;
import com.codecool.netflixandchill.util.SessionManager;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/user-page")
public class UserPageController extends HttpServlet {

    private SessionManager sessionManager = SessionManager.getInstance();
    private UserDao userDaoDB = UserDaoDB.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        HttpSession session = sessionManager.getHttpSessionRedirect(request);
//        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
//        WebContext context = new WebContext(request, response, request.getServletContext());
//
//        if (session == null) {
//            response.sendRedirect("/");
//            return;
//        }
//
//        long userId = (long) session.getAttribute("userId");
//        List<Episode> episodes = userDaoDB.getWatchedEpisodesById(userId);
//        context.setVariable("episodes", episodes);
//
//        engine.process("user_page.html", context, response.getWriter());
    }
}
