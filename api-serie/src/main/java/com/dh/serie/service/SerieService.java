package com.dh.serie.service;

import com.dh.serie.event.NewSerieEventProducer;
import com.dh.serie.model.Serie;
import com.dh.serie.repository.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {

    private final SerieRepository repository;
    private final NewSerieEventProducer eventProducer;


    public SerieService(SerieRepository repository, NewSerieEventProducer newSerieEventProducer) {
        this.repository = repository;
        this.eventProducer = newSerieEventProducer;
    }

    public List<Serie> getAll() {
        return repository.findAll();
    }

    public List<Serie> getSeriesBygGenre(String genre) {
        return repository.findAllByGenre(genre);
    }

    public String create(Serie serie) {
        Serie serieSaved = repository.save(serie);
        NewSerieEventProducer.Message message = new NewSerieEventProducer.Message();
        message.setId(serie.getId());
        message.setName(serie.getName());
        message.setGenre(serie.getGenre());
        eventProducer.publishNewSerieEvent(message);
        return serieSaved.getId();
    }
}
