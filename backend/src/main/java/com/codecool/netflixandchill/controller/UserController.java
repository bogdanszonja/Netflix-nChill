package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.dao.implementation.UserDaoDB;
import com.codecool.netflixandchill.util.RequestParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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

        for (String key : jsonObject.keySet()) {
            jsonObjectKey = key;
        }

        if (jsonObjectKey.equals("episode")) {
            JsonArray seasons = (JsonArray) jsonObject.get("season");
            for (JsonElement season : seasons) {
                JsonArray seasonsEpisode = (JsonArray) ((JsonObject) season).get("episodes");
                for (JsonElement episode : seasonsEpisode) {
                    long id = ((JsonObject) episode).get("id").getAsLong();
                    userDaoDB.addEpisodeToWatchedList(id, 1);
                }
            }
        } else if (jsonObjectKey.equals("favourite")){
            long id = jsonObject.get("season").getAsLong();
            userDaoDB.addSeriesToFavouriteList(id, 1);
        } else {
            long id = jsonObject.get("series").getAsLong();
            userDaoDB.addSeriesToWatchList(id, 1);
        }


        JsonObject answer = new JsonObject();
        answer.addProperty("success", true);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer.toString());
    }

}
