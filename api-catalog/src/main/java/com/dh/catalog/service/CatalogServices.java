package com.dh.catalog.service;

import com.dh.catalog.client.MovieFeign;
import com.dh.catalog.client.SerieFeign;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogServices {

    private final SerieFeign serieFeign;
    private final MovieFeign movieFeign;

    public CatalogServices(SerieFeign serieFeign, MovieFeign movieFeign) {
        this.serieFeign = serieFeign;
        this.movieFeign = movieFeign;
    }

    @CircuitBreaker(name="getMovieByGenr", fallbackMethod = "getMovieByGenreFallbackValue")
    @Retry(name = "getMovieByGenr")
    public List<MovieFeign.MovieDto> getMovieByGenre(String genr) {
        return movieFeign.getByGenre(genr);
    }

    @CircuitBreaker(name="getSerieByGenr", fallbackMethod = "getSerieByGenreFallbackValue")
    @Retry(name = "getSerieByGenr")
    public List<SerieFeign.SerieDto> getSerieByGenre(String genre) {
        return serieFeign.getByGenre(genre);
    }

    private List<MovieFeign.MovieDto> getMovieByGenreFallbackValue(String genre, Throwable t) throws Exception {
        List<MovieFeign.MovieDto> list = new ArrayList<>();
        return list;
    }
    private List<SerieFeign.SerieDto> getSerieByGenreFallbackValue(String genre, Throwable t) throws Exception {
        List<SerieFeign.SerieDto> list = new ArrayList<>();
        return list;
    }
}
