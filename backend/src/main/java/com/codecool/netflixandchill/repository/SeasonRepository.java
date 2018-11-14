package com.codecool.netflixandchill.repository;

import com.codecool.netflixandchill.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface SeasonRepository extends JpaRepository<Season, Long> {
    Season findFirstBySeriesId(long id);

    Season findFirstById(long id);
}
