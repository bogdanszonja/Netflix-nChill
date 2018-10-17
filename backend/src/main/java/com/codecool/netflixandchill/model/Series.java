package com.codecool.netflixandchill.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Series extends BaseModel {

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
        super(title, description);
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
