package com.github.yirelav.quotessolution.api;

import com.github.yirelav.quotessolution.BaseApiTest;
import com.github.yirelav.quotessolution.config.TestConstants;
import com.github.yirelav.quotessolution.domain.entities.Author;
import com.github.yirelav.quotessolution.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@EnableAutoConfiguration
class AuthorsApiTest extends BaseApiTest {

    @Test
    void givenCreateAuthorReq_shouldCreatedWith201() throws Exception {
        assertEquals(0, authorRepository.count());
        String createQuoteReq = TestUtils.loadFile("json/create_author.json");
        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                        .contentType("application/json")
                        .content(createQuoteReq))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());

        List<Author> authors = authorRepository.findAll();
        assertEquals(1, authors.size());
        assertEquals(TestConstants.AUTHOR_NAME, authors.get(0).getName());

        Mockito.verify(authorService, Mockito.times(1)).create(any());
    }

    @Test
    void givenAlreadyExistsAuthorReq_shouldReturn400() throws Exception {
        entityCreator.createTestAuthorEntity();
        String createQuoteReq = TestUtils.loadFile("json/create_author.json");
        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                        .contentType("application/json")
                        .content(createQuoteReq))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

}
