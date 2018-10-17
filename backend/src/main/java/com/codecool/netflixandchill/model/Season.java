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
public class Season extends BaseModel {

    @OneToMany(mappedBy = "season", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @Column(nullable = false)
    @ToString.Exclude
    private List<Episode> episodes = new ArrayList<>();

    @Temporal(TemporalType.DATE)
    private Date year;

    @Column(nullable = false)
    private int serialNumber;

    @ManyToOne
    private Series series;

    @Builder
    public Season(String title, String description, Date year, int serialNumber) {
        super(title, description);
        this.year = year;
        this.serialNumber = serialNumber;
    }

    public void addEpisode(Episode episode) {
        episodes.add(episode);
        episode.setSeason(this);
    }
}
