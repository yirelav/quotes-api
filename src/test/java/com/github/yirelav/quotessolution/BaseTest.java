package com.github.yirelav.quotessolution;

import com.github.yirelav.quotessolution.repository.AuthorRepository;
import com.github.yirelav.quotessolution.repository.QuoteRepository;
import com.github.yirelav.quotessolution.repository.RatingRepository;
import com.github.yirelav.quotessolution.service.QuoteService;
import com.github.yirelav.quotessolution.service.RatingService;
import com.github.yirelav.quotessolution.utils.EntityCreator;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public abstract class BaseTest {

    @Autowired
    protected AuthorRepository authorRepository;
    @Autowired
    protected QuoteRepository quoteRepository;
    @Autowired
    protected RatingRepository ratingRepository;

    @Autowired
    protected EntityCreator entityCreator;

    @Autowired
    protected RatingService ratingService;

    @Autowired
    protected QuoteService quoteService;

    @BeforeEach
    void reset() {
        authorRepository.deleteAll();
        quoteRepository.deleteAll();
        ratingRepository.deleteAll();
    }
}
