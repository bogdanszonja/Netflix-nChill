package com.codecool.netflixandchill.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Date releaseDate;

    @Column(nullable = false)
    private int runtime;

    @Column(nullable = false)
    private int serialNumber;

    @ManyToOne
    private Season season;

    @ManyToMany(mappedBy = "watchedEpisodes")
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
