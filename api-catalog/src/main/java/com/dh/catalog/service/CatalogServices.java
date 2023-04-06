package com.dh.catalog.service;

import com.dh.catalog.client.MovieFeign;
import com.dh.catalog.client.SerieFeign;
import com.dh.catalog.model.GenreResponse;
import com.dh.catalog.repository.OfflineMovieRepository;
import com.dh.catalog.repository.OfflineSerieRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogServices {

    private final SerieFeign serieFeign;
    private final MovieFeign movieFeign;
    private final OfflineMovieRepository movieRepository;
    private final OfflineSerieRepository serieRepository;

    public CatalogServices(SerieFeign serieFeign, MovieFeign movieFeign, OfflineSerieRepository offlineSerieRepository, OfflineMovieRepository offlineMovieRepository) {
        this.serieFeign = serieFeign;
        this.movieFeign = movieFeign;
        this.movieRepository = offlineMovieRepository;
        this.serieRepository = offlineSerieRepository;
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
        return movieRepository.findAllByGenre(genre);
    }
    private List<SerieFeign.SerieDto> getSerieByGenreFallbackValue(String genre, Throwable t) throws Exception {
        return serieRepository.findAllByGenre(genre);
    }

    public GenreResponse getByGenreOnline(String genre) throws Exception {
        GenreResponse listByGen = new GenreResponse();
        listByGen.setGenre(genre);
        listByGen.setMovies(getMovieByGenre(genre));
        listByGen.setSeries(getSerieByGenre(genre));
        return listByGen;
    }

    public void save(MovieFeign.MovieDto movie) {
        movieRepository.save(movie);
    }

    public void save(SerieFeign.SerieDto serie) {
        serieRepository.save(serie);
    }

    public GenreResponse getByGenreOffline(String genre) throws Exception {
        GenreResponse listByGen = new GenreResponse();
        listByGen.setGenre(genre);
        listByGen.setMovies(movieRepository.findAllByGenre(genre));
        listByGen.setSeries(serieRepository.findAllByGenre(genre));
        return listByGen;
    }
}
