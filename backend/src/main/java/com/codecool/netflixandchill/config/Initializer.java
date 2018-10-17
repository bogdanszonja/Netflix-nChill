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
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class Initializer {
    private RemoteURLReader urlReader;

    public Initializer(RemoteURLReader urlReader) {
        this.urlReader = urlReader;
    }

    public JSONArray getAllShowJSON() throws IOException {
        String netflixPath = "https://www.episodate.com/api/most-popular?page=:page";

        String result = urlReader.readFromUrl(netflixPath);
        JSONObject json = new JSONObject(result);

        return json.getJSONArray("tv_shows");

    }

    public List<Integer> getAllShowsId() throws IOException {
        return getAllShowJSON().toList().stream()
                .map(o -> (Integer) ((HashMap) o).get("id"))
                .collect(Collectors.toList());
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

    public Date formatStringToDate(String stringDate) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(stringDate);
    }
}