package com.dh.catalog.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "audiovisualExchange";
    public static final String TOPIC_NEW_SERIE = "com.dh.api-serie.newSerie";
    public static final String TOPIC_NEW_MOVIE = "com.dh.api-movie.newMovie";

    public static final String QUEUE_NEW_SERIE = "queueNewSerie";
    public static final String QUEUE_NEW_MOVIE = "queueNewMovie";

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queueNewSerie() {
        return  new Queue(QUEUE_NEW_SERIE);
    }

    @Bean
    public Queue queueNewMovie() {
        return  new Queue(QUEUE_NEW_MOVIE);
    }

    @Bean
    public Binding bindingExchangeSerie() {
        return BindingBuilder
                .bind(queueNewSerie())
                .to(appExchange())
                .with(TOPIC_NEW_SERIE);
    }

    @Bean
    public Binding bindingExchangeMovie() {
        return BindingBuilder
                .bind(queueNewMovie())
                .to(appExchange())
                .with(TOPIC_NEW_MOVIE);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
