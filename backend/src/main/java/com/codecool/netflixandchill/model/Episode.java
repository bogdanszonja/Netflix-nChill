package com.codecool.netflixandchill.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Episode extends BaseModel {

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @ToString.Exclude
    private Date releaseDate;

    @Column(nullable = false)
    private int runtime;

    @Column(nullable = false)
    private int serialNumber;

    @ManyToOne
    private Season season;

    @ManyToMany(mappedBy = "watchedEpisodes")
    @ToString.Exclude
    private Collection<User> users = new ArrayList<>();

    @Builder
    public Episode(String title, String description, Date releaseDate, int runtime, int serialNumber) {
        super(title, description);
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.serialNumber = serialNumber;
    }

    public void addUser(User user) {
        users.add(user);
    }
}
