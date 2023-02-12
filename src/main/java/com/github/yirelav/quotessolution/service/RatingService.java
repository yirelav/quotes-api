package com.github.yirelav.quotessolution.service;

import com.github.yirelav.quotessolution.domain.entities.Quote;
import com.github.yirelav.quotessolution.domain.entities.RatingHistoryRecord;
import com.github.yirelav.quotessolution.domain.enums.Vote;
import com.github.yirelav.quotessolution.repository.RatingRepository;
import jakarta.persistence.LockModeType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
@AllArgsConstructor
public class RatingService {

    private final RatingRepository repository;

    public Stream<RatingHistoryRecord> getQuoteRatings(Long quoteId) {
        return repository.findAllByQuoteId(quoteId);
    }

    @Retryable
    public void changeRating(Quote quote, Vote direction) {

        int difference = direction == Vote.UP ? 1 : -1;
        int total = findLastHistoryRecord(quote.getId())
                .map(r -> r.getTotal() + difference)
                .orElse(difference);

        RatingHistoryRecord historyRecord = RatingHistoryRecord.builder()
                .quote(quote)
                .author(quote.getAuthor())
                .difference(difference)
                .total(total)
                .date(Instant.now())
                .build();

        quote.setCurrentRating(total);

        System.out.println("before historyRecord");
        repository.save(historyRecord);
        System.out.println("after historyRecord");

    }

    private Optional<RatingHistoryRecord> findLastHistoryRecord(long quoteId) {
        return repository.findFirstByQuoteIdOrderByUpdatedDesc(quoteId);
    }


    public void removeRatings(Long quoteId) {
        repository.deleteAllByQuoteId(quoteId);
    }
}
