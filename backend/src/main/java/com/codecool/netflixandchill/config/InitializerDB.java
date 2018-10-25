package com.codecool.netflixandchill.config;

import com.codecool.netflixandchill.model.*;
import com.codecool.netflixandchill.util.RemoteURLReader;
import com.google.gson.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


class InitializerDB {
    private RemoteURLReader urlReader;
    private EntityManagerFactory emf;
    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat format2 = new SimpleDateFormat("MMM/dd/yyyy");

    InitializerDB(RemoteURLReader urlReader, EntityManagerFactory emf) {
        this.urlReader = urlReader;
        this.emf = emf;
    }

    private List<JSONArray> getAllShowJSON() throws IOException {
        List<JSONArray> pages = new LinkedList<>();

        for (int i = 1; i <= 10; i++) {
            String seriesPath = "https://www.episodate.com/api/most-popular?page=" + i;
            String result = urlReader.readFromUrl(seriesPath);
            JSONObject json = new JSONObject(result);

            pages.add(json.getJSONArray("tv_shows"));
        }

        return pages;

    }

    private List<Integer> getAllShowsId() throws IOException {
        List<Integer> showIdList = new ArrayList<>();

        for (JSONArray array : getAllShowJSON()) {
            showIdList.addAll(array.toList().stream()
                    .map(o -> (Integer) ((HashMap) o).get("id"))
                    .collect(Collectors.toList()));
        }
        return showIdList;
    }

    private JsonArray getAllShowWithSeasonAndEpisodeJson() throws IOException {
        JsonArray jsonArray = new JsonArray();
        JsonParser jsonParser = new JsonParser();
        for (Integer id : getAllShowsId()) {
            String seriesPath = "https://www.episodate.com/api/show-details?q=" + id.toString();
            String result = urlReader.readFromUrl(seriesPath);
            jsonArray.add((jsonParser.parse(result)));
        }
        return jsonArray;
    }

    void populateDB() throws IOException {
        EntityManager em = emf.createEntityManager();

        for (JsonElement element : getAllShowWithSeasonAndEpisodeJson()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            Episode episode;
            Season season;

            JsonObject currentSeries = (JsonObject) element.getAsJsonObject().get("tvShow");

            Series series = Series.builder()
                    .title(currentSeries.get("name").getAsString())
                    .description(currentSeries.get("description").getAsString())
                    .airDate(formatStringToDate(currentSeries.get("start_date").getAsString()))
                    .status(Status.valueOf(currentSeries.get("status").getAsString()
                    .replace(" ", "_").toUpperCase()))
                    .genres((convertJsonArrayToEnumGenre(currentSeries.get("genres").getAsJsonArray())))
                    .build();

            Set<Integer> seasonSet = new HashSet<>();

            JsonArray episodes = currentSeries.get("episodes").getAsJsonArray();

            for (int j = 0; j < episodes.size(); j++) {
                seasonSet.add(episodes.get(j).getAsJsonObject().get("season").getAsInt());
            }

            for (int j = 0; j < seasonSet.size(); j++) {
                season = Season.builder()
                        .title("Season " + (j + 1))
                        .year(formatStringToDate(episodes.get(j).getAsJsonObject().get("air_date").getAsString()))
                        .seasonNumber(j + 1)
                        .build();

                series.addSeason(season);

                for (int k = 0; k < episodes.size(); k++) {
                    if (episodes.get(k).getAsJsonObject().get("season").getAsInt() == season.getSeasonNumber()) {
                        episode = Episode.builder()
                                .title(episodes.get(k).getAsJsonObject().get("name").getAsString())
                                .releaseDate(formatStringToDate(episodes.get(k).getAsJsonObject().get("air_date").getAsString()))
                                .runtime(currentSeries.get("runtime").getAsInt())
                                .episodeNumber(episodes.get(k).getAsJsonObject().get("episode").getAsInt())
                                .build();

                        season.addEpisode(episode);
                        em.persist(episode);
                    }
                }
                em.persist(season);
            }
            em.persist(series);
            transaction.commit();
        }
        em.clear();
        em.close();
    }

    private List<Genre> convertJsonArrayToEnumGenre(JsonArray genreList) {
        List<Genre> genres = new LinkedList<>();
        genreList.forEach(genre -> genres.add(Genre.valueOf(genre.getAsString()
                .replace("-", "_")
                .toUpperCase())));

        return genres;
    }

    private Date formatStringToDate(String stringDate) {
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