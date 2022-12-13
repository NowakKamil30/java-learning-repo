package com.example.streamingstockquoteservice.quote.service;

import com.example.streamingstockquoteservice.quote.domain.QuoteHistory;
import com.example.streamingstockquoteservice.quote.model.Quote;
import com.example.streamingstockquoteservice.quote.repositories.QuoteHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class QuoteHistoryServiceImpl implements QuoteHistoryService {

    private final QuoteHistoryRepository repository;

    @Override
    public Mono<QuoteHistory> saveQuoteMongo(Quote quote) {
        return repository.save(QuoteHistory.builder()
                .ticker(quote.getTicker())
                .price(quote.getPrice())
                .instant(quote.getInstant())
                .build());
    }
}
