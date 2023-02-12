package com.github.yirelav.quotessolution.repository;

import com.github.yirelav.quotessolution.entities.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    @Query(value = "SELECT q FROM Quote q ORDER BY random() limit 1")
    Optional<Quote> findRandom();
}
