package com.dh.catalog.repository;

import com.dh.catalog.client.SerieFeign;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfflineSerieRepository extends MongoRepository<SerieFeign.SerieDto, String> {

    List<SerieFeign.SerieDto> findAllByGenre(String genre);
}
