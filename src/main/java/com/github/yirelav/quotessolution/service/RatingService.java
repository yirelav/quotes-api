package com.github.yirelav.quotessolution.service;

import com.github.yirelav.quotessolution.domain.entities.Author;
import com.github.yirelav.quotessolution.domain.entities.Quote;
import com.github.yirelav.quotessolution.domain.entities.RatingHistoryRecord;
import com.github.yirelav.quotessolution.domain.enums.Vote;
import com.github.yirelav.quotessolution.repository.RatingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class RatingService {

    private final RatingRepository repository;

    public List<RatingHistoryRecord> getQuoteRatings(Long quoteId) {
        return repository.findAllByQuoteIdOrderByUpdated(quoteId);
    }

    @Retryable
    @Transactional(propagation = Propagation.MANDATORY)
    public void changeRating(Quote quote, Author author, Vote direction) {
        int difference = direction == Vote.UP ? 1 : -1;
        int total = findLastHistoryRecord(quote.getId())
                .map(r -> r.getTotal() + difference)
                .orElse(difference);

        quote.setCurrentRating(total);

        RatingHistoryRecord historyRecord = RatingHistoryRecord.builder()
                .quote(quote)
                .author(author)
                .difference(difference)
                .total(total)
                .date(Instant.now())
                .build();

        repository.save(historyRecord);
    }

    private Optional<RatingHistoryRecord> findLastHistoryRecord(long quoteId) {
        return repository.findFirstByQuoteIdOrderByUpdatedDesc(quoteId);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void removeRatings(Long quoteId) {
        repository.deleteAllByQuoteId(quoteId);
    }
}
