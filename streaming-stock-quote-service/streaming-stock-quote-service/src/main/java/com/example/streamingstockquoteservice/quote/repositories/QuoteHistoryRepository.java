package com.example.streamingstockquoteservice.quote.repositories;

import com.example.streamingstockquoteservice.quote.domain.QuoteHistory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface QuoteHistoryRepository extends ReactiveMongoRepository<QuoteHistory, String> {
}
