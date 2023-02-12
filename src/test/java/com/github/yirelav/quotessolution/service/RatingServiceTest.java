package com.github.yirelav.quotessolution.service;

import com.github.yirelav.quotessolution.BaseTest;
import com.github.yirelav.quotessolution.domain.entities.Author;
import com.github.yirelav.quotessolution.domain.entities.Quote;
import com.github.yirelav.quotessolution.domain.enums.Vote;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest
class RatingServiceTest extends BaseTest {

    @Autowired
    private QuoteService quoteService;


    @Test
    void changeRating() {
        Author authorEntity = entityCreator.createAuthorEntity();
        List<Quote> quotes = entityCreator.createNQuotes(1, authorEntity);

        Quote quote = quotes.get(0);
        Long quoteId = quote.getId();
        quoteService.changeRating(quoteId, Vote.UP);
        quoteService.changeRating(quoteId, Vote.UP);
        quoteService.changeRating(quoteId, Vote.UP);
        assertEquals(3, quoteRepository.findById(quoteId).get().getCurrentRating());

        quoteService.changeRating(quoteId, Vote.DOWN);
        assertEquals(2, quoteRepository.findById(quoteId).get().getCurrentRating());


    }

    @Test
    void removeRating() {
        Author authorEntity = entityCreator.createAuthorEntity();
        List<Quote> quotes = entityCreator.createNQuotes(1, authorEntity);

        Quote quote = quotes.get(0);
        Long quoteId = quote.getId();
        quoteService.changeRating(quoteId, Vote.UP);
        quoteService.changeRating(quoteId, Vote.UP);
        quoteService.remove(quoteId);
        quoteService.changeRating(quoteId, Vote.UP);
        quoteService.changeRating(quoteId, Vote.DOWN);


        assertTrue(
                quoteRepository.findById(quoteId).isEmpty()
        );
        assertTrue(
                ratingRepository.findFirstByQuoteIdOrderByUpdatedDesc(quoteId).isEmpty()
        );

        assertEquals(0, quoteRepository.count());
        assertEquals(0, ratingRepository.count());

    }
}