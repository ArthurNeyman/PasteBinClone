package com.paste_bin_clone.config;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.core.annotation.Order;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(1)
public class DatabaseSetupExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        PostgresTestContainer.container.start();
        updateDataSourceProps(PostgresTestContainer.container);
    }

    private void updateDataSourceProps(PostgresTestContainer container) {
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());
        System.setProperty("spring.flyway.enabled", "true");
        System.setProperty("spring.flyway.user=", container.getUsername());
        System.setProperty("pring.flyway.password", container.getPassword());
        System.setProperty("spring.flyway.url", container.getJdbcUrl());
        System.setProperty("pring.flyway.locations", "classpath:db/migration");
    }

}
