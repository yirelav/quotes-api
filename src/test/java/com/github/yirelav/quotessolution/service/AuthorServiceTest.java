package com.github.yirelav.quotessolution.service;

import com.github.yirelav.quotessolution.config.TestConstants;
import com.github.yirelav.quotessolution.domain.entities.Author;
import com.github.yirelav.quotessolution.repository.AuthorRepository;
import com.github.yirelav.quotessolution.web.dto.CreateAuthorRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.github.yirelav.quotessolution.config.TestConstants.AUTHOR_MAIL;
import static com.github.yirelav.quotessolution.config.TestConstants.AUTHOR_NAME;
import static com.github.yirelav.quotessolution.config.TestConstants.AUTHOR_PASSWORD;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {


    private AuthorService authorService;

    private AuthorRepository repository;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(AuthorRepository.class);
        authorService = new AuthorService(repository);
    }

    @Test
    void create() {

        CreateAuthorRequest createAuthorRequest = new CreateAuthorRequest(
                AUTHOR_NAME,
                AUTHOR_MAIL,
                AUTHOR_PASSWORD
        );

        Mockito.when(repository.save(ArgumentMatchers.any())).thenAnswer(i -> i.getArgument(0));

        Author author = authorService.create(createAuthorRequest);

        assertEquals(AUTHOR_NAME, author.getName());
        assertNotEquals(TestConstants.AUTHOR_PASSWORD, author.getPassword());

        assertTrue(BCrypt.checkpw(TestConstants.AUTHOR_PASSWORD, author.getPassword()));
    }

}