package com.github.yirelav.quotessolution.repository;


import com.github.yirelav.quotessolution.domain.entities.RatingHistoryRecord;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;
import java.util.stream.Stream;

public interface RatingRepository extends JpaRepository<RatingHistoryRecord, Long> {
    Stream<RatingHistoryRecord> findAllByQuoteId(long quoteId);

    Optional<RatingHistoryRecord> findFirstByQuoteIdOrderByUpdatedDesc(long quoteId);

    void deleteAllByQuoteId(Long quoteId);
}
