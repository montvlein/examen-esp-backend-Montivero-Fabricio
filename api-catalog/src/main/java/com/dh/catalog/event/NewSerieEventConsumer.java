package com.dh.catalog.event;

import com.dh.catalog.config.RabbitMQConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NewSerieEventConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_SERIE)
    public void listenNewWalletEvent(Message message) {
        System.out.println("We have a notification of a new serie with id '" + message.getId() + "' nombre '" + message.getName() + "' genero '" + message.getGenre());
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
