package com.github.yirelav.quotessolution.repository;

import com.github.yirelav.quotessolution.domain.entities.Quote;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    @Query(value = "SELECT q FROM Quote q ORDER BY random() limit 1")
    Optional<Quote> findRandom();

    @Override
    @Modifying
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Quote> findById(Long id);
}
