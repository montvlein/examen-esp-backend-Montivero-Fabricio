package com.dh.catalog.event;

import com.dh.catalog.client.MovieFeign;
import com.dh.catalog.config.RabbitMQConfig;
import com.dh.catalog.model.RabbitMessage;
import com.dh.catalog.service.CatalogServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NewMovieEventConsumer {

    @Autowired
    private CatalogServices services;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_MOVIE)
    public void listenNewWalletEvent(RabbitMessage message) throws JsonProcessingException {
        log.info("A new " + message.getType() + " has been created with id '" + message.getId() + "' on genero '" + message.getGenre() + "'");
        ObjectMapper objectMapper = new ObjectMapper();
        log.info(message.getObj());
        MovieFeign.MovieDto movie = objectMapper.readValue(message.getObj(), MovieFeign.MovieDto.class);
        services.save(movie);
    }

}
