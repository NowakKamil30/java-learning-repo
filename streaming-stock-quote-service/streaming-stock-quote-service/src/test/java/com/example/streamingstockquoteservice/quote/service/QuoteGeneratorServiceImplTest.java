package com.example.streamingstockquoteservice.quote.service;

import com.example.streamingstockquoteservice.quote.model.Quote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

class QuoteGeneratorServiceImplTest {

    QuoteGeneratorService service;

    @BeforeEach
    void setUp() {
        service = new QuoteGeneratorServiceImpl();
    }

    @Test
    void fetchQuoteStream() throws InterruptedException {
        final var quotesFlux = service.fetchQuoteStream(Duration.ofMillis(100l));

        final Consumer<Quote> consumer = System.out::println;

        final Consumer<Throwable> throwableConsumer = e -> System.out.println(e.getMessage());

        final var countDownLatch = new CountDownLatch(1);

        final Runnable done = () -> countDownLatch.countDown();

        quotesFlux.take(30)
                .subscribe(consumer, throwableConsumer, done);

        countDownLatch.await();
    }

}