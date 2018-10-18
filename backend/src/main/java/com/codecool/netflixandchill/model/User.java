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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(unique = true, nullable = false)
    private String emailAddress;

    @Column(nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "users_watchlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "episode_id"))
    @ToString.Exclude
    private Collection<Series> watchlist = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "users_favourites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "episode_id"))
    @ToString.Exclude
    private Collection<Series> favourites = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "episode_id"))
    @ToString.Exclude
    private Collection<Episode> watchedEpisodes = new ArrayList<>();

    @Temporal(TemporalType.DATE)
    private Date registrationDate;

    @Builder
    public User(String userName, String emailAddress, String password, Date registrationDate) {
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.registrationDate = registrationDate;
    }

    public void addWatchedEpisodes(Episode episode) {
        watchedEpisodes.add(episode);
        episode.addUser(this);
    }

    public void addSeriesToFavouriteList(Series series) {
        favourites.add(series);
    }

    public void addSeriesToWatchList(Series series) {
        watchlist.add(series);
    }

}