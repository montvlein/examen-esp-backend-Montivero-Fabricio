package com.dh.movie.service;


import com.dh.movie.event.NewMovieEventProducer;
import com.dh.movie.model.Movie;
import com.dh.movie.repository.MovieRepository;
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

    public Long save(Movie movie) {
        Movie movieSaved = movieRepository.save(movie);
        NewMovieEventProducer.Message message = new NewMovieEventProducer.Message();
        message.setId(String.valueOf(movieSaved.getId()));
        message.setType(NewMovieEventProducer.Message.AudiovisualType.MOVIE);
        message.setGenre(movieSaved.getGenre());
        message.setObj(movieSaved);
        eventProducer.publishNewMovieEvent(message);
        return movieSaved.getId();
    }
}
