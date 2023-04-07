package com.dh.movie.service;


import com.dh.movie.event.NewMovieEventProducer;
import com.dh.movie.model.Movie;
import com.dh.movie.repository.MovieRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final NewMovieEventProducer eventProducer;

    public MovieService(MovieRepository movieRepository, NewMovieEventProducer newMovieEventProducer) {
        this.movieRepository = movieRepository;
        this.eventProducer = newMovieEventProducer;
    }

    public List<Movie> findByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public Long save(Movie movie) throws JsonProcessingException {
        Movie movieSaved = movieRepository.save(movie);
        NewMovieEventProducer.Message message = new NewMovieEventProducer.Message();
        message.setType(NewMovieEventProducer.Message.AudiovisualType.MOVIE);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMovie = objectMapper.writeValueAsString(movieSaved);
        message.setObj(jsonMovie);
        eventProducer.publishNewMovieEvent(message);
        return movieSaved.getId();
    }
}
