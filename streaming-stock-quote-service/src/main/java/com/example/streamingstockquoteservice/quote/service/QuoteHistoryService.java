package com.example.streamingstockquoteservice.quote.service;

import com.example.streamingstockquoteservice.quote.domain.QuoteHistory;
import com.example.streamingstockquoteservice.quote.model.Quote;
import reactor.core.publisher.Mono;

public interface QuoteHistoryService {

    Mono<QuoteHistory> saveQuoteMongo(Quote quote);
}
