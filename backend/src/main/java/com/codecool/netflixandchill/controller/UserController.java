package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.dao.implementation.UserDaoDB;
import com.codecool.netflixandchill.util.RequestParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

@WebServlet(urlPatterns = "/user")
public class UserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDaoDB userDaoDB = UserDaoDB.getInstance();

        JsonObject jsonObject = RequestParser.getInstance().getJsonObject(request);
        String jsonObjectKey = "";
        System.out.println(jsonObject);
//        long userId = jsonObject.get("userId").getAsLong();
        long id;

        for (String key : jsonObject.keySet()) {
            jsonObjectKey = key;
        }


        switch (jsonObjectKey) {
            case "episode":
                id = jsonObject.get("episode").getAsLong();
                userDaoDB.addEpisodeToWatchedList(id, 19542);

                break;
            case "favourite":
                id = jsonObject.get("favourite").getAsLong();
                userDaoDB.addSeriesToFavouriteList(id, 19542);

                break;
            case "watchlist":
                id = jsonObject.get("watchlist").getAsLong();
                userDaoDB.addSeriesToWatchList(id, 19542);

                break;
            case "season":
                id = jsonObject.get("season").getAsLong();
                userDaoDB.addSeasonEpisodeToWatchedList(id, 19542);

                break;
            case "series":
                id = jsonObject.get("series").getAsLong();
                userDaoDB.addSeriesEpisodeToWatchedList(id, 19542);
                break;
        }


        JsonObject answer = new JsonObject();
        answer.addProperty("success", true);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer.toString());
    }

}
