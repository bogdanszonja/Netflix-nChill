package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.util.JsonCreator;
import com.codecool.netflixandchill.util.RequestParser;
import com.codecool.netflixandchill.util.SessionManager;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SeriesController extends HttpServlet {

    private RequestParser requestParser;
    private JsonCreator jsonCreator;
    private SessionManager sessionManager;


    public SeriesController(RequestParser rp, JsonCreator jc, SessionManager sm) {
        this.requestParser = rp;
        this.jsonCreator = jc;
        this.sessionManager = sm;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject answer = new JsonObject();

        if (request.getParameter("id") != null
            && jsonCreator.getSeriesById(Long.parseLong(request.getParameter("id"))) != null) {
            answer.add("data", jsonCreator.getSeriesById(Long.parseLong(request.getParameter("id"))));
            response.getWriter().write(answer.toString());
            return;
        } else {
            JsonObject error = new JsonObject();
            error.addProperty("message", "Series not found");
            answer.add("error", error);
            response.setStatus(404);
        }

        answer.add("data", jsonCreator.getAllSeries());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

}
