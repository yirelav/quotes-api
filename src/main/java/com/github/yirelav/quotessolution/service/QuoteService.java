package com.github.yirelav.quotessolution.service;

import com.github.yirelav.quotessolution.domain.entities.Author;
import com.github.yirelav.quotessolution.domain.entities.Quote;
import com.github.yirelav.quotessolution.domain.enums.Vote;
import com.github.yirelav.quotessolution.repository.QuoteRepository;
import com.github.yirelav.quotessolution.web.dto.CreateQuoteRequest;
import com.github.yirelav.quotessolution.web.exception.AuthorNotFoundException;
import com.github.yirelav.quotessolution.web.exception.QuoteNotFoundException;
import jakarta.persistence.LockModeType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class QuoteService {

    private final QuoteRepository repository;
    private final AuthorService authorService;
    private final RatingService ratingService;

    public Optional<Quote> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public Optional<Quote> findRandom() {
        return repository.findRandom();
    }

    @Modifying
    @Retryable
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Optional<Quote> remove(Long id) {
        Optional<Quote> quoteOpt = repository.findById(id);
        ratingService.removeRatings(id);
        quoteOpt.ifPresent(repository::delete);
        return quoteOpt;
    }

    @Modifying
    public Quote create(CreateQuoteRequest request) {

        Optional<Author> authorOpt = authorService.findByName(request.author());

        if (authorOpt.isPresent()) {
            return repository.save(
                    Quote.builder()
                            .content(request.content())
                            .author(authorOpt.get())
                            .build());
        } else {
            throw new AuthorNotFoundException(request.author());
        }
    }

    @Modifying
    public Optional<Quote> update(Long id, String content) {
        Optional<Quote> updatedQuote = repository.findById(id);
        updatedQuote.ifPresent(quote -> quote.setContent(content));
        return updatedQuote;
    }

    @Modifying
    public void changeRating(long quoteId, String authorName, Vote direction) {
        Optional<Quote> quote = repository.findById(quoteId);
        if (quote.isEmpty()) {
            throw new QuoteNotFoundException(quoteId);
        }
        Optional<Author> authorOpt = authorService.findByName(authorName);
        if (authorOpt.isEmpty()) {
            throw new AuthorNotFoundException(authorName);
        }

        ratingService.changeRating(quote.get(), authorOpt.get(), direction);
    }

    public Page<Quote> findPageAndSort(Pageable currentRating) {
        return repository.findAll(currentRating);
    }
}
