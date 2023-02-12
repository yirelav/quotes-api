package com.github.yirelav.quotessolution;

import com.github.yirelav.quotessolution.repository.AuthorRepository;
import com.github.yirelav.quotessolution.repository.QuoteRepository;
import com.github.yirelav.quotessolution.repository.RatingRepository;
import com.github.yirelav.quotessolution.service.AuthorService;
import com.github.yirelav.quotessolution.service.QuoteService;
import com.github.yirelav.quotessolution.service.RatingService;
import com.github.yirelav.quotessolution.utils.EntityCreator;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public abstract class BaseTest {

    @SpyBean
    protected AuthorRepository authorRepository;
    @SpyBean
    protected QuoteRepository quoteRepository;
    @SpyBean
    protected RatingRepository ratingRepository;

    @Autowired
    protected EntityCreator entityCreator;

    @SpyBean
    protected RatingService ratingService;

    @SpyBean
    protected QuoteService quoteService;

    @SpyBean
    protected AuthorService authorService;

    @BeforeEach
    void reset() {
        authorRepository.deleteAll();
        quoteRepository.deleteAll();
        ratingRepository.deleteAll();
    }
}
