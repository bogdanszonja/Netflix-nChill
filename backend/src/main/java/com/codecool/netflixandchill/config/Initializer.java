package com.codecool.netflixandchill.config;

import com.codecool.netflixandchill.json.Show;
import com.codecool.netflixandchill.model.Genre;
import com.codecool.netflixandchill.util.RemoteURLReader;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


public class Initializer {
    private RemoteURLReader urlReader;
    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat format2 = new SimpleDateFormat("MMM/dd/yyyy");

    public Initializer(RemoteURLReader urlReader) {
        this.urlReader = urlReader;
    }

    public List<JSONArray> getAllShowJSON() throws IOException {
        String netflixPath;
        List<JSONArray> pages = new LinkedList<>();

        for (int i = 0; i < 10; i++) {
            netflixPath = "https://www.episodate.com/api/most-popular?page=" + i;
            String result = urlReader.readFromUrl(netflixPath);
            JSONObject json = new JSONObject(result);
            pages.add(json.getJSONArray("tv_shows"));
        }

        return pages;

    }

    public List<Integer> getAllShowsId() throws IOException {
        List<Integer> showIdList = new ArrayList<>();

        for (JSONArray array : getAllShowJSON()) {
            showIdList.addAll(array.toList().stream()
                    .map(o -> (Integer) ((HashMap) o).get("id"))
                    .collect(Collectors.toList()));
        }
        return showIdList;
    }

    public JSONArray getAllShowWithSeasonAndEpisodeJSON() throws IOException {
        JSONArray jsonArray = new JSONArray();
        for (Integer id : getAllShowsId()) {
            String netflixPath = "https://www.episodate.com/api/show-details?q=" + id.toString();
            String result = urlReader.readFromUrl(netflixPath);
            JSONObject json = new JSONObject(result);
            jsonArray.put(json);
        }
        return jsonArray;
    }

    public void addTvShowToMyTvSHowsList() throws IOException {
        Gson gson = new Gson();
        for (Object object : getAllShowWithSeasonAndEpisodeJSON()) {
            Show show = gson.fromJson(object.toString(), Show.class);
            show.addShowToMyTvShow(show);
        }
    }

    public List<Genre> convertStringToEnumGenre(String[] genreList) {
        List<Genre> genres = new LinkedList<>();
        for (String genre : genreList) {
                genres.add(Genre.valueOf(genre.replace("-", "_").toUpperCase()));
        }
        return genres;
    }

    public Date formatStringToDate(String stringDate) {
        try {
            return format.parse(stringDate);
        } catch (ParseException e) {
            try {
                return format2.parse(stringDate);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }
}