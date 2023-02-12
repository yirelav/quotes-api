package com.github.yirelav.quotessolution.api;

import com.github.yirelav.quotessolution.BaseApiTest;
import com.github.yirelav.quotessolution.domain.entities.Author;
import com.github.yirelav.quotessolution.utils.TestUtils;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@EnableAutoConfiguration
class RatingApiTest extends BaseApiTest {

    @Test
    void givenCreateQuoteReq_shouldCreatedWith201() throws Exception {
        Author authorEntity = entityCreator.createAuthorEntity();
        entityCreator.createNQuotes(1, authorEntity);

    }


}
