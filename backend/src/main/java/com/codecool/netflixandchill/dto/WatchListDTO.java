package com.codecool.netflixandchill.dto;

import com.codecool.netflixandchill.model.Episode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WatchListDTO {
    List<Episode> watchedEpisodes;
    int wastedTime;



}
