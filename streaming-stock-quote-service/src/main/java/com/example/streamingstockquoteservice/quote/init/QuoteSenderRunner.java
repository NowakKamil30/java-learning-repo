package com.example.streamingstockquoteservice.quote.init;

import com.example.streamingstockquoteservice.quote.config.RabbitConfig;
import com.example.streamingstockquoteservice.quote.service.QuoteGeneratorService;
import com.example.streamingstockquoteservice.quote.service.QuoteMessageSender;
import com.rabbitmq.client.Delivery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.rabbitmq.Receiver;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuoteSenderRunner implements CommandLineRunner {

    private final QuoteMessageSender quoteMessageSender;
    private final QuoteGeneratorService quoteGeneratorService;

    private final Receiver receiver;


    @Override
    public void run(String... args) throws Exception {
        final var latch = new CountDownLatch(25);
        quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(50))
                .take(44)
                .log("Got quote")
                .flatMap(quoteMessageSender::sendQuoteMessage)
                .subscribe(
                        result -> log.info("Send Message to Rabbit"),
                        throwable -> log.error("Got error ", throwable),
                        () -> log.info("done")
                );

        latch.await(1, TimeUnit.SECONDS);

        final var receivedCount = new AtomicInteger();

        receiver.consumeAutoAck(RabbitConfig.QUEUE)
                .log("Msg Receiver")
                .subscribe(msg -> log.info("Received Message # {} - {}", receivedCount.incrementAndGet(),
                new String(msg.getBody())));
    }
}
