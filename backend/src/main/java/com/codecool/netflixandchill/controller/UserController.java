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
        long id;

        for (String key : jsonObject.keySet()) {
            jsonObjectKey = key;
        }

//        switch (jsonObjectKey) {
//            case "episode":
//                id = jsonObject.get("episode").getAsLong();
//                userDaoDB.addEpisodeToWatchedList(id, userDaoDB.getIdFromUser());
//
//                break;
//            case "favourite":
//                id = jsonObject.get("favourite").getAsLong();
//                userDaoDB.addSeriesToFavouriteList(id, 1);
//
//                break;
//            case "watchlist":
//                id = jsonObject.get("watchlist").getAsLong();
//                userDaoDB.addSeriesToWatchList(id, 1);
//
//                break;
//            case "season":
//                id = jsonObject.get("season").getAsLong();
//                userDaoDB.addSeasonToWatchedList(id, 1);
//
//                break;
//            case "series":
//                id = jsonObject.get("series").getAsLong();
//                userDaoDB.addSeriesToWatchedList(id, 1);
//                break;
//        }


        JsonObject answer = new JsonObject();
        answer.addProperty("success", true);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer.toString());
    }

}
