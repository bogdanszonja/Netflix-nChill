package com.codecool.netflixandchill.model;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @ToString.Exclude
    private Date releaseDate;

    @Column(nullable = false)
    private int runtime;

    @Column(nullable = false)
    private int episodeNumber;

    @ManyToOne
    private Season season;

    @ManyToMany(mappedBy = "watchedEpisodes")
    @LazyCollection(LazyCollectionOption.TRUE)
    @ToString.Exclude
    private Collection<User> users = new ArrayList<>();

    @Builder
    public Episode(String title, Date releaseDate, int runtime, int episodeNumber) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.episodeNumber = episodeNumber;
    }

    public void addUser(User user) {
        users.add(user);
    }
}
