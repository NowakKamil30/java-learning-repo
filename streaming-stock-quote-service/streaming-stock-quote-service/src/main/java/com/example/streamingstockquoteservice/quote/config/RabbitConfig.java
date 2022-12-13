package com.example.streamingstockquoteservice.quote.config;

import com.rabbitmq.client.Connection;
import jakarta.annotation.PreDestroy;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.RabbitFlux;
import reactor.rabbitmq.Sender;
import reactor.rabbitmq.SenderOptions;

import java.io.IOException;


@Configuration
public class RabbitConfig {
    public static final String QUEUE = "quotes";

    @Autowired
    Mono<Connection> connectionMono;

    @Bean
    Mono<Connection> connectionMono(CachingConnectionFactory connectionFactory) {
        return Mono.fromCallable(() -> connectionFactory.getRabbitConnectionFactory().newConnection());
    }

    @Bean
    Sender sender(Mono<Connection> mono) {
        return RabbitFlux.createSender(new SenderOptions().connectionMono(mono));
    }

    @PreDestroy
    public void close() throws IOException {
        connectionMono.block().close();
    }
}
