package com.dh.catalog.event;

import com.dh.catalog.config.RabbitMQConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NewMovieEventConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_MOVIE)
    public void listenNewWalletEvent(NewSerieEventConsumer.Message message) {
        System.out.println("Created a new movie with id '" + message.getId() + "', name '" + message.getName() + "' and genero '" + message.getGenre() + "'");
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {
        private String id;
        private String name;
        private String genre;
    }
}
