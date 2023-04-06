package com.dh.catalog.repository;

import com.dh.catalog.client.MovieFeign;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfflineMovieRepository extends MongoRepository<MovieFeign.MovieDto, Long> {
    List<MovieFeign.MovieDto> findAllByGenre(String genre);
}
