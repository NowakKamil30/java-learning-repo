package com.example.streamingstockquoteservice.quote.service;

import com.example.streamingstockquoteservice.quote.model.Quote;
import reactor.core.publisher.Flux;

import java.time.Duration;

public interface QuoteGeneratorService {

    Flux<Quote> fetchQuoteStream(Duration period);
}
