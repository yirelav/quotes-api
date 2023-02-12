package com.github.yirelav.quotessolution.config;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainerProvider;

public class PostgresTestContainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final JdbcDatabaseContainer POSTGRES_CONTAINER;

    static {
        POSTGRES_CONTAINER = (new PostgreSQLContainerProvider()).newInstance("11.6");
        POSTGRES_CONTAINER.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        TestPropertyValues
                .of("spring.datasource.url=" + POSTGRES_CONTAINER.getJdbcUrl())
                .and("spring.datasource.username=" + POSTGRES_CONTAINER.getUsername())
                .and("spring.datasource.password=" + POSTGRES_CONTAINER.getPassword())
                .applyTo(configurableApplicationContext.getEnvironment());
    }

}
