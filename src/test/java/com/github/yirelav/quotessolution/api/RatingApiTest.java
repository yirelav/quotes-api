package com.github.yirelav.quotessolution.api;

import com.github.yirelav.quotessolution.BaseApiTest;
import com.github.yirelav.quotessolution.config.TestConstants;
import com.github.yirelav.quotessolution.domain.entities.Author;
import com.github.yirelav.quotessolution.domain.entities.RatingHistoryRecord;
import com.github.yirelav.quotessolution.domain.enums.Vote;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@EnableAutoConfiguration
class RatingApiTest extends BaseApiTest {

    @Test
    void givenTwoUpvoteReq_shouldAcceptedWith202() throws Exception {
        List<Author> authors = entityCreator.createNAuthors(2);
        Long quoteId = prepareTestQuote();

        String urlTemplate = "/quotes/" + quoteId + "/up";

        mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate).contentType("application/json")
                        .param("author", authors.get(0).getName()))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
        mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate).contentType("application/json")
                        .param("author", authors.get(1).getName()))
                .andExpect(MockMvcResultMatchers.status().isAccepted());

        List<RatingHistoryRecord> ratingHistoryRecords = ratingService.getQuoteRatings(quoteId);
        assertEquals(2, ratingHistoryRecords.size());
        assertEquals(1, ratingHistoryRecords.get(0).getDifference());
        assertEquals(1, ratingHistoryRecords.get(0).getTotal());
        assertEquals(1, ratingHistoryRecords.get(1).getDifference());
        assertEquals(2, ratingHistoryRecords.get(1).getTotal());

        assertEquals(2, quoteService.findById(quoteId).get().getCurrentRating());
        Mockito.verify(ratingService, Mockito.times(2)).changeRating(any(), any(), any());

    }

    @Test
    void givenVariousVoteReq_shouldAcceptedWith202() throws Exception {
        List<Author> authors = entityCreator.createNAuthors(3);

        Long quoteId = prepareTestQuote();

        String upTemplate = "/quotes/" + quoteId + "/up";
        String downTemplate = "/quotes/" + quoteId + "/down";

        mockMvc.perform(MockMvcRequestBuilders.post(upTemplate).contentType("application/json")
                        .param("author", authors.get(0).getName()))
                .andExpect(MockMvcResultMatchers.status().isAccepted());

        mockMvc.perform(MockMvcRequestBuilders.post(downTemplate).contentType("application/json")
                        .param("author", authors.get(1).getName()))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
        mockMvc.perform(MockMvcRequestBuilders.post(downTemplate).contentType("application/json")
                        .param("author", authors.get(2).getName()))
                .andExpect(MockMvcResultMatchers.status().isAccepted());

        List<RatingHistoryRecord> ratingHistoryRecords = ratingService.getQuoteRatings(quoteId);
        assertEquals(3, ratingHistoryRecords.size());
        assertEquals(1, ratingHistoryRecords.get(0).getDifference());
        assertEquals(1, ratingHistoryRecords.get(0).getTotal());
        assertEquals(-1, ratingHistoryRecords.get(1).getDifference());
        assertEquals(0, ratingHistoryRecords.get(1).getTotal());
        assertEquals(-1, ratingHistoryRecords.get(2).getDifference());
        assertEquals(-1, ratingHistoryRecords.get(2).getTotal());

        assertEquals(-1, quoteService.findById(quoteId).get().getCurrentRating());
        Mockito.verify(ratingService, Mockito.times(3)).changeRating(any(), any(), any());

    }

    @Test
    void givenAllRatingReq_shouldReturnListWith200() throws Exception {
        List<Author> authors = entityCreator.createNAuthors(3);

        Long quoteId = prepareTestQuote();

        quoteService.changeRating(quoteId, authors.get(0).getName(), Vote.UP);
        quoteService.changeRating(quoteId, authors.get(1).getName(), Vote.UP);
        quoteService.changeRating(quoteId, authors.get(2).getName(), Vote.DOWN);


        String urlTemplate = "/quotes/" + quoteId + "/ratings";
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value(authors.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].author").value(authors.get(2).getName()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void givenTwoVotesByOneAuthor_shouldReturn400() throws Exception {
        Long quoteId = prepareTestQuote();

        String upTemplate = "/quotes/" + quoteId + "/up";
        String downTemplate = "/quotes/" + quoteId + "/down";

        mockMvc.perform(MockMvcRequestBuilders.post(upTemplate).contentType("application/json")
                        .param("author", TestConstants.AUTHOR_NAME))
                .andExpect(MockMvcResultMatchers.status().isAccepted());

        mockMvc.perform(MockMvcRequestBuilders.post(downTemplate).contentType("application/json")
                        .param("author", TestConstants.AUTHOR_NAME))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(ratingService, Mockito.times(2)).changeRating(any(), any(), any());
    }

    private Long prepareTestQuote() {
        Author authorEntity = entityCreator.createTestAuthorEntity();
        return entityCreator.createNQuotes(1, authorEntity).get(0).getId();
    }

}
