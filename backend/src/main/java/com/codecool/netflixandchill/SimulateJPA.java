package com.codecool.netflixandchill;

import com.codecool.netflixandchill.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SimulateJPA {

    public static void populateDb(EntityManager em) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate = Calendar.getInstance().getTime();
        Date airDate = Calendar.getInstance().getTime();
        Date year = Calendar.getInstance().getTime();
        try {
            releaseDate = sdf.parse("1997-07-21");
            airDate = sdf.parse("1993-12-01");
            year = sdf.parse("1993-12-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Series series = Series.builder().title("Szonja").description("Anita").airDate(new Date())
                .status(Status.RUNNING).genres(Collections.singletonList(Genre.HORROR)).build();
        Season season = Season.builder().title("Oli").description("Zoli").year(new Date()).serialNumber(1).build();
        Season season2 = Season.builder().title("Oli2").description("Zoli").year(new Date()).serialNumber(1).build();
        Season season3 = Season.builder().title("Oli3").description("Zoli").year(new Date()).serialNumber(1).build();
        Season season4 = Season.builder().title("Oli4").description("Zoli").year(new Date()).serialNumber(1).build();
        series.addSeason(season);
        season.setSeries(series);
        series.addSeason(season2);
        season2.setSeries(series);
        series.addSeason(season3);
        season3.setSeries(series);
        series.addSeason(season4);
        season4.setSeries(series);

        Episode episode = Episode.builder().title("A").description("1").releaseDate(new Date()).runtime(45).serialNumber(0).build();
        Episode episode2 = Episode.builder().title("B").description("2").releaseDate(new Date()).runtime(45).serialNumber(0).build();
        Episode episode3 = Episode.builder().title("C").description("3").releaseDate(new Date()).runtime(45).serialNumber(0).build();
        Episode episode4 = Episode.builder().title("D").description("4").releaseDate(new Date()).runtime(45).serialNumber(0).build();
        Episode episode5 = Episode.builder().title("E").description("5").releaseDate(new Date()).runtime(45).serialNumber(0).build();
        Episode episode6 = Episode.builder().title("F").description("6").releaseDate(new Date()).runtime(45).serialNumber(0).build();
        episode.setSeason(season);
        season.addEpisode(episode);
        episode2.setSeason(season2);
        season2.addEpisode(episode2);
        episode3.setSeason(season2);
        season2.addEpisode(episode3);
        episode4.setSeason(season3);
        season3.addEpisode(episode4);
        episode5.setSeason(season3);
        season3.addEpisode(episode5);
        episode6.setSeason(season3);
        season3.addEpisode(episode6);


        Series series2 = Series.builder().title("Szonja2").description("Anita").airDate(new Date())
                .status(Status.RUNNING).genres(Arrays.asList(Genre.HORROR, Genre.PORNO)).build();
        Season season21 = Season.builder().title("Oli21").description("Zoli").year(new Date()).serialNumber(1).build();
        Season season22 = Season.builder().title("Oli22").description("Zoli").year(new Date()).serialNumber(1).build();
        Season season23 = Season.builder().title("Oli23").description("Zoli").year(new Date()).serialNumber(1).build();
        Season season24 = Season.builder().title("Oli24").description("Zoli").year(new Date()).serialNumber(1).build();
        series.addSeason(season21);
        season21.setSeries(series2);
        series.addSeason(season22);
        season22.setSeries(series2);
        series.addSeason(season23);
        season23.setSeries(series2);
        series.addSeason(season24);
        season24.setSeries(series2);

        Episode episode21 = Episode.builder().title("A2").description("1").releaseDate(new Date()).runtime(45).serialNumber(0).build();
        Episode episode22 = Episode.builder().title("B2").description("2").releaseDate(new Date()).runtime(45).serialNumber(0).build();
        Episode episode23 = Episode.builder().title("C2").description("3").releaseDate(new Date()).runtime(45).serialNumber(0).build();
        Episode episode24 = Episode.builder().title("D2").description("4").releaseDate(new Date()).runtime(45).serialNumber(0).build();
        Episode episode25 = Episode.builder().title("E2").description("5").releaseDate(new Date()).runtime(45).serialNumber(0).build();
        Episode episode26 = Episode.builder().title("F2").description("6").releaseDate(new Date()).runtime(45).serialNumber(0).build();
        episode21.setSeason(season21);
        season21.addEpisode(episode21);
        episode22.setSeason(season22);
        season22.addEpisode(episode22);
        episode23.setSeason(season22);
        season22.addEpisode(episode23);
        episode24.setSeason(season23);
        season23.addEpisode(episode24);
        episode25.setSeason(season23);
        season23.addEpisode(episode25);
        episode26.setSeason(season23);
        season23.addEpisode(episode26);

        User user = User.builder().emailAddress("oli").password("pina").registrationDate(new Date()).userName("olcsi").build();
        user.addWatchedEpisodes(episode);
        user.addWatchedEpisodes(episode2);
        user.addWatchedEpisodes(episode3);
        user.addWatchedEpisodes(episode4);
        user.addWatchedEpisodes(episode5);
        user.addWatchedEpisodes(episode6);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(series);
        em.persist(series2);
        em.persist(season);
        em.persist(season2);
        em.persist(season3);
        em.persist(season4);
        em.persist(season21);
        em.persist(season22);
        em.persist(season23);
        em.persist(season24);
        em.persist(user);
        em.persist(episode21);
        em.persist(episode22);
        em.persist(episode23);
        em.persist(episode24);
        em.persist(episode25);
        em.persist(episode26);
        transaction.commit();

    }

    public static void main(String[] args) {
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<>();
        for (String envName : env.keySet()) {
            if (envName.contains("DB_USER_NAME")) {
                configOverrides.put("javax.persistence.jdbc.user", env.get(envName));
            }
            if (envName.contains("DB_PASSWORD")) {
                configOverrides.put("javax.persistence.jdbc.password", env.get(envName));
            }
        }

        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("netflixandchill", configOverrides);
        EntityManager em = emf.createEntityManager();

        populateDb(em);
        em.clear(); //clear hibernate cache - force next statements to read data from db

        em.close();
        emf.close();

    }
}
