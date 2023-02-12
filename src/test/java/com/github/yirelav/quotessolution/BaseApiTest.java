package com.github.yirelav.quotessolution;

import com.github.yirelav.quotessolution.config.PostgresTestContainersInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.logging.Level;
import java.util.logging.LogManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(
        initializers = {
                PostgresTestContainersInitializer.class
        }
)
public abstract class BaseApiTest extends BaseTest {
    static {
        // Postgres JDBC driver uses JUL;
        // disable it to avoid annoying, irrelevant, stderr logs during connection testing
        LogManager.getLogManager().getLogger("").setLevel(Level.OFF);
    }

    @Autowired
    protected MockMvc mockMvc;

}
