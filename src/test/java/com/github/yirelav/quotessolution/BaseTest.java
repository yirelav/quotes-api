package com.github.yirelav.quotessolution;

import com.github.yirelav.quotessolution.config.PostgresTestContainersInitializer;
import com.github.yirelav.quotessolution.repository.AuthorRepository;
import com.github.yirelav.quotessolution.repository.QuoteRepository;
import com.github.yirelav.quotessolution.repository.RatingRepository;
import com.github.yirelav.quotessolution.utils.EntityCreator;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.logging.Level;
import java.util.logging.LogManager;

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

    @BeforeEach
    void reset() {
        authorRepository.deleteAll();
        quoteRepository.deleteAll();
        ratingRepository.deleteAll();
    }
}
