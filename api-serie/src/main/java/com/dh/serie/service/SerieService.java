package com.dh.serie.service;

import com.dh.serie.event.NewSerieEventProducer;
import com.dh.serie.model.Serie;
import com.dh.serie.repository.SerieRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public String create(Serie serie) throws JsonProcessingException {
        Serie serieSaved = repository.save(serie);
        NewSerieEventProducer.Message message = new NewSerieEventProducer.Message();
        message.setType(NewSerieEventProducer.Message.AudiovisualType.SERIE);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonSerie = objectMapper.writeValueAsString(serieSaved);
        message.setObj(jsonSerie);
        eventProducer.publishNewSerieEvent(message);
        return serieSaved.getId();
    }
}
