package com.github.yirelav.quotessolution.api;

import com.github.yirelav.quotessolution.BaseApiTest;
import com.github.yirelav.quotessolution.config.PostgresTestContainersInitializer;
import com.github.yirelav.quotessolution.domain.entities.Author;
import com.github.yirelav.quotessolution.domain.entities.Quote;
import com.github.yirelav.quotessolution.domain.enums.Vote;
import com.github.yirelav.quotessolution.utils.TestUtils;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.*;

@EnableAutoConfiguration
@ContextConfiguration(
        initializers = {
                PostgresTestContainersInitializer.class
        }
)
class QuotesApiTest extends BaseApiTest {

    @Test
    void givenCreateQuoteReq_shouldCreatedWith201() throws Exception {
        assertEquals(0, quoteRepository.count());
        entityCreator.createTestAuthorEntity();
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
        Author authorEntity = entityCreator.createTestAuthorEntity();
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
        Author authorEntity = entityCreator.createTestAuthorEntity();
        entityCreator.createNQuotes(1, authorEntity);
        assertEquals(1, quoteRepository.count());
        long id = quoteRepository.findAll().get(0).getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/quotes/" + id)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        assertEquals(0, quoteRepository.count());
    }

    @RepeatedTest(10)
    void givenRandomQuoteReq_shouldReturn200() throws Exception {
        Author authorEntity = entityCreator.createTestAuthorEntity();
        entityCreator.createNQuotes(2, authorEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/quotes/random")
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void givenTopWorse10Req_shouldReturnQuotesListWith200() throws Exception {
        Author quotesAuthor = entityCreator.createTestAuthorEntity();
        List<Quote> quotes = entityCreator.createNQuotes(11, quotesAuthor);

        List<Author> voters = entityCreator.createNAuthors(10);

        Quote bestQuote = quotes.get(4);
        quoteService.changeRating(bestQuote.getId(), voters.get(0).getName(), Vote.UP);
        quoteService.changeRating(bestQuote.getId(), voters.get(1).getName(), Vote.UP);
        quoteService.changeRating(bestQuote.getId(), voters.get(2).getName(), Vote.UP);
        quoteService.changeRating(bestQuote.getId(), voters.get(3).getName(), Vote.UP);

        Quote second = quotes.get(8);
        quoteService.changeRating(second.getId(), voters.get(0).getName(), Vote.UP);
        quoteService.changeRating(second.getId(), voters.get(1).getName(), Vote.UP);

        Quote last = quotes.get(2);
        quoteService.changeRating(last.getId(), voters.get(0).getName(), Vote.DOWN);

        Quote worse = quotes.get(1);
        quoteService.changeRating(worse.getId(), voters.get(0).getName(), Vote.DOWN);
        quoteService.changeRating(worse.getId(), voters.get(1).getName(), Vote.DOWN);

        mockMvc.perform(MockMvcRequestBuilders.get("/quotes/top10")
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(bestQuote.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(second.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[9].id").value(last.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(10)))
                .andDo(MockMvcResultHandlers.print());

                mockMvc.perform(MockMvcRequestBuilders.get("/quotes/worse10")
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[9].id").value(second.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(last.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(worse.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(10)))
                .andDo(MockMvcResultHandlers.print());


    }


}
