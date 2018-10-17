package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.util.JsonCreator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/series"})
public class SeriesController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("id") != null) {
            response.getWriter().write(
                    JsonCreator.getInstance().getSeriesById(Long.parseLong(request.getParameter("id"))));
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JsonCreator.getInstance().getAllShows());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

}
