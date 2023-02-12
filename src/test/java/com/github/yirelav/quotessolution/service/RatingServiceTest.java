package com.github.yirelav.quotessolution.service;

import com.github.yirelav.quotessolution.BaseTest;
import com.github.yirelav.quotessolution.domain.entities.Author;
import com.github.yirelav.quotessolution.domain.entities.Quote;
import com.github.yirelav.quotessolution.domain.enums.Vote;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class RatingServiceTest extends BaseTest {

    @Autowired
    private QuoteService quoteService;

    @Test
    void changeRating() {
        Author authorEntity = entityCreator.createTestAuthorEntity();
        List<Quote> quotes = entityCreator.createNQuotes(1, authorEntity);

        List<Author> authors = entityCreator.createNAuthors(4);
        Quote quote = quotes.get(0);
        Long quoteId = quote.getId();
        quoteService.changeRating(quoteId, authors.get(0).getName(), Vote.UP);
        quoteService.changeRating(quoteId, authors.get(1).getName(), Vote.UP);
        quoteService.changeRating(quoteId, authors.get(2).getName(), Vote.UP);
        assertEquals(3, quoteRepository.findById(quoteId).get().getCurrentRating());

        quoteService.changeRating(quoteId, authors.get(3).getName(), Vote.DOWN);
        assertEquals(2, quoteRepository.findById(quoteId).get().getCurrentRating());
        Mockito.verify(ratingService, Mockito.times(4)).changeRating(any(), any(), any());

    }

    @Test
    void whenRemoveQuote_thenRemoveQuoteRatings() {
        Author authorEntity = entityCreator.createTestAuthorEntity();
        List<Quote> quotes = entityCreator.createNQuotes(1, authorEntity);
        List<Author> authors = entityCreator.createNAuthors(4);

        Quote quote = quotes.get(0);
        Long quoteId = quote.getId();
        quoteService.changeRating(quoteId, authors.get(0).getName(), Vote.UP);
        quoteService.changeRating(quoteId, authors.get(1).getName(), Vote.UP);
        quoteService.remove(quoteId);


        assertTrue(
                quoteRepository.findById(quoteId).isEmpty()
        );
        assertTrue(
                ratingRepository.findFirstByQuoteIdOrderByUpdatedDesc(quoteId).isEmpty()
        );

        assertEquals(0, quoteRepository.count());
        assertEquals(0, ratingRepository.count());

        Mockito.verify(ratingService, Mockito.times(2)).changeRating(any(), any(), any());

    }
}