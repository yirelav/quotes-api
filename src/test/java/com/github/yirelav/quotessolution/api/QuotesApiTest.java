package com.github.yirelav.quotessolution.api;

import com.github.yirelav.quotessolution.BaseApiTest;
import com.github.yirelav.quotessolution.entities.Author;
import com.github.yirelav.quotessolution.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@EnableAutoConfiguration
class QuotesApiTest extends BaseApiTest {

    @Test
    void givenCreateQuoteReq_shouldCreatedWith201() throws Exception {
        assertEquals(0, quoteRepository.count());
        entityCreator.createAuthorEntity();
        String createQuoteReq = TestUtils.loadFile("json/create_quote.json");
        mockMvc.perform(MockMvcRequestBuilders.post("/quotes")
                        .contentType("application/json")
                        .content(createQuoteReq))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());

        assertEquals(1, quoteRepository.count());
    }

    @Test
    void givenNonExistentAuthor_shouldReturn400() throws Exception {
        String createQuoteReq = TestUtils.loadFile("json/create_quote_wrong_author.json");
        mockMvc.perform(MockMvcRequestBuilders.post("/quotes")
                        .contentType("application/json")
                        .content(createQuoteReq))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
        assertEquals(0, quoteRepository.count());
    }

    @Test
    void givenQuoteUpdateReq_shouldUpdateWith200() throws Exception {
        Author authorEntity = entityCreator.createAuthorEntity();
        entityCreator.createNQuotes(1, authorEntity);
        assertEquals(1, quoteRepository.count());

        long id = quoteRepository.findAll().get(0).getId();
        String createQuoteReq = TestUtils.loadFile("json/update_quote.json");
        mockMvc.perform(MockMvcRequestBuilders.put("/quotes/" + id)
                        .contentType("application/json")
                        .content(createQuoteReq))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(createQuoteReq))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(authorEntity.getName()))
                .andDo(MockMvcResultHandlers.print());

        assertEquals(1, quoteRepository.count());
    }


    @Test
    void givenQuoteRemoveReq_shouldRemoveWith200() throws Exception {
        Author authorEntity = entityCreator.createAuthorEntity();
        entityCreator.createNQuotes(1, authorEntity);
        assertEquals(1, quoteRepository.count());

        mockMvc.perform(MockMvcRequestBuilders.delete("/quotes/1")
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        assertEquals(0, quoteRepository.count());
    }



}
