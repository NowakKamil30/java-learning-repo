package com.example.streamingstockquoteservice.quote.init;

import com.example.streamingstockquoteservice.quote.service.QuoteGeneratorService;
import com.example.streamingstockquoteservice.quote.service.QuoteHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuoteRunner implements CommandLineRunner {

    private final QuoteHistoryService quoteHistoryService;
    private final QuoteGeneratorService quoteGeneratorService;

    @Override
    public void run(String... args) throws Exception {
        quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(10l))
                .take(60)
                .log("Got: ")
                .flatMap(quoteHistoryService::saveQuoteMongo)
                .subscribe(
                        savedQuote -> log.info("Saved Quote: " + savedQuote),
                        throwable -> log.error("Some Error"),
                        () -> log.info("done"));
    }
}
