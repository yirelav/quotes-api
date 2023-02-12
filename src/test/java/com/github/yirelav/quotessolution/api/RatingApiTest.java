package com.github.yirelav.quotessolution.api;

import com.github.yirelav.quotessolution.BaseApiTest;
import com.github.yirelav.quotessolution.domain.entities.Author;
import com.github.yirelav.quotessolution.domain.entities.RatingHistoryRecord;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    }

    private Long prepareTestQuote() {
        Author authorEntity = entityCreator.createTestAuthorEntity();
        return entityCreator.createNQuotes(1, authorEntity).get(0).getId();
    }

}
