package com.paste_bin_clone.config;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

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
