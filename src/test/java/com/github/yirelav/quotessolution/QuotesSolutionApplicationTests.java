package com.github.yirelav.quotessolution;

import com.github.yirelav.quotessolution.config.PostgresTestContainersInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class QuotesSolutionApplicationTests {

	@Test
	void contextLoads() {
	}

}
