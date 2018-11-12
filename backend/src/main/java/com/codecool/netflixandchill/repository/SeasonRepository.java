package com.codecool.netflixandchill.repository;

import com.codecool.netflixandchill.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<Season, Long> {
}
