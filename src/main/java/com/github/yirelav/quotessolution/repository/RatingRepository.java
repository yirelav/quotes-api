package com.github.yirelav.quotessolution.repository;


import com.github.yirelav.quotessolution.domain.entities.RatingHistoryRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<RatingHistoryRecord, Long> {
    List<RatingHistoryRecord> findAllByQuoteIdOrderByUpdated(long quoteId);

    Optional<RatingHistoryRecord> findFirstByQuoteIdOrderByUpdatedDesc(long quoteId);

    void deleteAllByQuoteId(Long quoteId);
}
