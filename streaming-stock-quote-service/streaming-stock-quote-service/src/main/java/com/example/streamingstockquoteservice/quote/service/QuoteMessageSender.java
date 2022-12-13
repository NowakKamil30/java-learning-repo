package com.example.streamingstockquoteservice.quote.service;

import com.example.streamingstockquoteservice.quote.config.RabbitConfig;
import com.example.streamingstockquoteservice.quote.model.Quote;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

@Component
@RequiredArgsConstructor
public class QuoteMessageSender {
    private final ObjectMapper objectMapper;
    private final Sender sender;

    public Mono<Void> sendQuoteMessage(final Quote quote) throws JsonProcessingException {
        byte[] jsonBytes = objectMapper.writeValueAsBytes(quote);

        return sender.send(Mono.just(new OutboundMessage("", RabbitConfig.QUEUE, jsonBytes)));
    }
}
