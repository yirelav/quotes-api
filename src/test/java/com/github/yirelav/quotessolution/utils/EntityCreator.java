package com.github.yirelav.quotessolution.utils;

import com.github.yirelav.quotessolution.domain.Author;
import com.github.yirelav.quotessolution.domain.Quote;
import com.github.yirelav.quotessolution.repository.AuthorRepository;
import com.github.yirelav.quotessolution.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.github.yirelav.quotessolution.config.TestConstants.AUTHOR_MAIL;
import static com.github.yirelav.quotessolution.config.TestConstants.AUTHOR_NAME;
import static com.github.yirelav.quotessolution.config.TestConstants.AUTHOR_PASSWORD;
import static com.github.yirelav.quotessolution.config.TestConstants.QUOTE_CONTENT;

@Component
public class EntityCreator {

    @Autowired
    QuoteRepository quoteRepository;

    @Autowired
    AuthorRepository authorRepository;

    public void createNQuotes(int n, Author author) {
        List<Quote> articles = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            articles.add(getTestQuoteEntity(author));
        }
        quoteRepository.saveAll(articles);
    }

    public static Quote getTestQuoteEntity(Author author) {
        return Quote.builder()
                .author(author)
                .content(QUOTE_CONTENT)
                .build();
    }

    public Author createAuthorEntity() {
        return authorRepository.save(
                getTestAuthorEntity()
        );
    }

    public static Author getTestAuthorEntity() {
        return Author.builder()
                .name(AUTHOR_NAME)
                .email(AUTHOR_MAIL)
                .password(AUTHOR_PASSWORD)
                .build();
    }

}
