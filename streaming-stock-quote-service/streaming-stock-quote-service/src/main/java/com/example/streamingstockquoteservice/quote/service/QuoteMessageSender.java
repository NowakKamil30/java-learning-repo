package com.example.streamingstockquoteservice.quote.service;

import com.example.streamingstockquoteservice.quote.config.RabbitConfig;
import com.example.streamingstockquoteservice.quote.model.Quote;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.OutboundMessageResult;
import reactor.rabbitmq.QueueSpecification;
import reactor.rabbitmq.Sender;

@Component
@Slf4j
@RequiredArgsConstructor
public class QuoteMessageSender {
    private final ObjectMapper objectMapper;
    private final Sender sender;

    public Mono<Void> sendQuoteMessage(final Quote quote) {
        byte[] jsonBytes = new byte[0];
        try {
            jsonBytes = objectMapper.writeValueAsBytes(quote);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Flux<OutboundMessageResult> confirmations = sender.sendWithPublishConfirms(
                Flux.just(new OutboundMessage("", RabbitConfig.QUEUE, jsonBytes)));

        sender.declareQueue(QueueSpecification.queue(RabbitConfig.QUEUE))
                .thenMany(confirmations)
                .doOnError(e -> log.error("Send failed", e))
                .subscribe(r -> {
                    if (r.isAck()) {
                        log.info("Message sent successfully {}", new String(r.getOutboundMessage().getBody()));
                    }
                });

        return Mono.empty();
    }
}
