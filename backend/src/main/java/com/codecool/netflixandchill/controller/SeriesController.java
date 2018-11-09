package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.model.Series;
import com.codecool.netflixandchill.service.SeriesService;
import com.codecool.netflixandchill.util.JsonCreator;
import com.codecool.netflixandchill.util.RequestParser;
import com.codecool.netflixandchill.util.SessionManager;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/series")
public class SeriesController {

//    private RequestParser requestParser;
//    private JsonCreator jsonCreator;
//    private SessionManager sessionManager;

    @Autowired
    private SeriesService seriesService;

//    public SeriesController(RequestParser rp, JsonCreator jc, SessionManager sm) {
//        this.requestParser = rp;
//        this.jsonCreator = jc;
//        this.sessionManager = sm;
//    }

    @GetMapping
    public List<Series> getAllSeries() {
        return this.seriesService.getAllSeries();
    }

    @GetMapping("/{id}")
    public Series getSingleSeries(@PathVariable Long id) {
        return this.seriesService.getSingleSeriesById(id);
    }

    @GetMapping("/search")
    public List<Series> getSeriesBySubstring(@RequestParam("searchTerm") String substring) {
        return this.seriesService.getSeriesBySubstring(substring);
    }

    @GetMapping("/trending")
    public List<Series> getTrendingSeries() {
        return this.seriesService.getTrendingSeries();
    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        JsonObject answer = new JsonObject();
//
//        if (request.getParameter("id") != null) {
//            if (jsonCreator.getSeriesById(Long.parseLong(request.getParameter("id"))) != null) {
//                answer.addSeries("data", jsonCreator.getSeriesById(Integer.parseInt(request.getParameter("id"))));
//                response.getWriter().write(answer.toString());
//                return;
//            } else {
//                JsonObject error = new JsonObject();
//                error.addProperty("message", "Series not found");
//                answer.addSeries("error", error);
//                response.setStatus(404);
//            }
//        } else {
//            JsonObject error = new JsonObject();
//            error.addProperty("message", "Series not found");
//            answer.addSeries("error", error);
//            response.setStatus(404);
//        }
//
//        answer.addSeries("data", jsonCreator.getAllSeries());
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(answer.toString());
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//    }

}
