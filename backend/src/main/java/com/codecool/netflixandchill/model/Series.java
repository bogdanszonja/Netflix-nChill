package com.codecool.netflixandchill.model;

import com.sun.deploy.security.ValidationState;
import lombok.*;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Temporal(TemporalType.DATE)
    private Date airDate;

    @OneToMany(mappedBy = "series", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @Column(nullable = false)
    @ToString.Exclude
    private List<Season> seasons = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Genre.class)
    @Column(name = "genre", nullable = false)
    @ToString.Exclude
    private List<Genre> genres = new ArrayList<>();

    @Builder
    public Series(String title, String description, Status status, Date airDate, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.airDate = airDate;
        this.genres = genres;
    }

    public void addSeason(Season season) {
        seasons.add(season);
        season.setSeries(this);
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }
}
