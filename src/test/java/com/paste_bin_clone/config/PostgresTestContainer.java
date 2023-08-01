package com.paste_bin_clone.config;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresTestContainer extends PostgreSQLContainer<PostgresTestContainer> {
    private static final String IMAGE_VERSION = "postgres:13";
    private static final String DATABASE_NAME = "PasteBinCloneTestDb";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1234";

    public static PostgresTestContainer container = new PostgresTestContainer()
            .withDatabaseName(DATABASE_NAME)
            .withUsername(USERNAME)
            .withPassword(PASSWORD);

    public PostgresTestContainer() {
        super(IMAGE_VERSION);
    }


}
