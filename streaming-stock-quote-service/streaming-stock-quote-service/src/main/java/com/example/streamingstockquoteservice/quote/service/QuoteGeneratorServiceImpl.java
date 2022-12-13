package com.example.streamingstockquoteservice.quote.service;

import com.example.streamingstockquoteservice.quote.model.Quote;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuoteGeneratorServiceImpl implements QuoteGeneratorService {

    private final List<Quote> quotes = new ArrayList<>();

    public QuoteGeneratorServiceImpl() {
        quotes.add(new Quote("A", 10.34));
        quotes.add(new Quote("B", 100.34));
        quotes.add(new Quote("CAA", 120.00));
        quotes.add(new Quote("DDX", 1.33));
        quotes.add(new Quote("ECDS", 10.39));
        quotes.add(new Quote("GDS", 6.96));
        quotes.add(new Quote("HJHGH", 90.04));
        quotes.add(new Quote("IYB", 101.00));
        quotes.add(new Quote("JMK", 12.20));
        quotes.add(new Quote("KOO", 14.54));
        quotes.add(new Quote("LJHL", 10.00));
        quotes.add(new Quote("MOHJM", 15.15));
        quotes.add(new Quote("NiGH", 1.67));
        quotes.add(new Quote("Okh", 100.30));
        quotes.add(new Quote("POL", 105.90));
    }

    @Override
    public Flux<Quote> fetchQuoteStream(Duration period) {
        return Flux.generate(() -> 0,
                (index, sink) -> {
                    final var updatedQuote = updateQuote(quotes.get(index));
                    sink.next(updatedQuote);
                    return ++index % this.quotes.size();
                })
                .zipWith(Flux.interval(period))
                .map(Tuple2::getT1)
                .filter(quote -> quote instanceof Quote)
                .map(quote -> (Quote)quote)
                .map(quote -> {
                    quote.setInstant(Instant.now());
                    return quote;
                })
                .log("com.example.streamingstockquoteservice.quote.service.QuoteGeneratorServiceImpl");
    }

    private Quote updateQuote(final Quote quote) {
        return new Quote(quote.getTicker(), quote.getPrice().add(BigDecimal.TWO));
    }
}
