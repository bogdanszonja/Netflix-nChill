package com.codecool.netflixandchill.repository;

import com.codecool.netflixandchill.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface SeriesRepository extends JpaRepository<Series, Long> {

    @Transactional
    List<Series> findByTitleContaining(String substring);

    @Transactional
    List<Series> findTop3ByOrderByAirDateDesc();

}
