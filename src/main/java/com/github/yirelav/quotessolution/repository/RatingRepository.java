package com.github.yirelav.quotessolution.repository;


import com.github.yirelav.quotessolution.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Stream<Rating> findAllByQuote(long quoteId);
}
