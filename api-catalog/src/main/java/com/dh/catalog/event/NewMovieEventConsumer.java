package com.dh.catalog.event;

import com.dh.catalog.config.RabbitMQConfig;
import com.dh.catalog.model.RabbitMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NewMovieEventConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_MOVIE)
    public void listenNewWalletEvent(RabbitMessage message) {
        log.info("A new " + message.getType() + " has been created with id '" + message.getId() + "' on genero '" + message.getGenre() + "'");
    }

}
