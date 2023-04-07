package com.dh.catalog.service;

import com.dh.catalog.client.MovieFeign;
import com.dh.catalog.client.SerieFeign;
import com.dh.catalog.exceptions.MovieServerNotResponse;
import com.dh.catalog.exceptions.SerieServerNotResponse;
import com.dh.catalog.model.GenreResponse;
import com.dh.catalog.repository.OfflineMovieRepository;
import com.dh.catalog.repository.OfflineSerieRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
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
    public List<MovieFeign.MovieDto> getMovieByGenre(String genr) throws MovieServerNotResponse {
        try {
            return movieFeign.getByGenre(genr);
        } catch (Exception e) {
            throw new MovieServerNotResponse("Movies services unavailable");
        }
    }

    @CircuitBreaker(name="getSerieByGenr", fallbackMethod = "getSerieByGenreFallbackValue")
    @Retry(name = "getSerieByGenr")
    public List<SerieFeign.SerieDto> getSerieByGenre(String genre) throws SerieServerNotResponse {
        try {
            return serieFeign.getByGenre(genre);
        } catch (Exception e) {
            throw new SerieServerNotResponse("Series services unavailable");
        }
    }

    private List<MovieFeign.MovieDto> getMovieByGenreFallbackValue(String genre, Throwable t) {
        return movieRepository.findAllByGenre(genre);
    }
    private List<SerieFeign.SerieDto> getSerieByGenreFallbackValue(String genre, Throwable t) {
        return serieRepository.findAllByGenre(genre);
    }

    public GenreResponse getByGenreOnline(String genre) {
        GenreResponse listByGen = new GenreResponse();
        listByGen.setGenre(genre);

        try {
            listByGen.setMovies(getMovieByGenre(genre));
        } catch (MovieServerNotResponse movieServerException) {
            log.error(movieServerException.getMessage());
            log.info("resolving Movie Server Not Response with offline repository");
            listByGen.setMovies(movieRepository.findAllByGenre(genre));
        }

        try {
            listByGen.setSeries(getSerieByGenre(genre));
        } catch (SerieServerNotResponse serieServerException) {
            log.error(serieServerException.getMessage());
            log.info("resolving Serie Server Not Response with offline repository");
            listByGen.setSeries(serieRepository.findAllByGenre(genre));
        }

        return listByGen;
    }

    public void save(MovieFeign.MovieDto movie) {
        movieRepository.save(movie);
    }

    public void save(SerieFeign.SerieDto serie) {
        serieRepository.save(serie);
    }

    public GenreResponse getByGenreOffline(String genre) {
        GenreResponse listByGen = new GenreResponse();
        listByGen.setGenre(genre);
        listByGen.setMovies(movieRepository.findAllByGenre(genre));
        listByGen.setSeries(serieRepository.findAllByGenre(genre));
        return listByGen;
    }
}
