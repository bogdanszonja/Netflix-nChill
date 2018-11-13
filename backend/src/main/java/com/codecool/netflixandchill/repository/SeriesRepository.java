package com.codecool.netflixandchill.repository;

import com.codecool.netflixandchill.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface SeriesRepository extends JpaRepository<Series, Long> {

    List<Series> findByTitleContaining(String substring);

    List<Series> findTop3ByOrderByAirDateDesc();

}
