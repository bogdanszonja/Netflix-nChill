package com.codecool.netflixandchill.repository;

import com.codecool.netflixandchill.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    Episode findEpisodeBySeason(long id);

}
