package com.example;

import com.google.common.collect.ImmutableMap;
import net.piotrturski.testegration.postgres.Postgres;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * this is the superclass for all postgres related spring tests
 */
public class DbIntegrationTest {

    @BeforeClass public static void beforeSpringContext() {prepareDatabaseForTest();}

    @Before public void beforeEachTest() { prepareDatabaseForTest(); }

    private static void prepareDatabaseForTest() {
        Postgres.docker("my-project", "9.6.1")
                .postConfigure(runtime ->
                        ImmutableMap.of(
                            "spring.datasource.url", runtime.getConfig().getJdbcUrl(),
                            "spring.datasource.username", runtime.getConfig().user,
                            "spring.datasource.password", runtime.getConfig().passwd)
                        .forEach(System::setProperty))
                .run();
    }
}
