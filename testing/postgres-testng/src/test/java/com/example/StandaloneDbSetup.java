package com.example;

import net.piotrturski.testegration.core.Definition;
import net.piotrturski.testegration.postgres.Postgres;
import net.piotrturski.testegration.postgres.PostgresConf;
import net.piotrturski.testegration.system.ConfigLoader;
import org.flywaydb.core.Flyway;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;

/**
 * this is the utility class for all postgres related tests without any DI framework
 */
public class StandaloneDbSetup {

    static DataSource prepareDataSourceForTest() {
        return Definition.<PostgresConf>of()
                // you may load config
                .configure(ConfigLoader.fromYaml(PostgresConf.class, "my-project"))
                // label the environment differently than DI-based environment
                .label("non-DI-project")
//                .label("my-project")
                // use postgres
                .withC(Postgres.dockerPartial("my-project", "9.6.1"))
                // it will be convenient to keep one open datasource
                .postConfigure(runtime -> {
                    PostgresConf cfg = runtime.getConfig();
                    SingleConnectionDataSource dataSource =
                            new SingleConnectionDataSource(cfg.getJdbcUrl(), cfg.user, cfg.passwd, true);
                    runtime.putShared("ds", dataSource);
                })
                // run flyway migrations manually
                .buildSchema(runtime -> {
                    DataSource ds = runtime.getShared("ds");
                    Flyway flyway = new Flyway();
                    flyway.setDataSource(ds);
                    flyway.migrate();
                })
                // tell how to release resources when they are not needed anymore
                .closeConnector(runtime -> {
                    SingleConnectionDataSource ds = runtime.getShared("ds", SingleConnectionDataSource.class);
                    ds.destroy();
                })
                .run()
                .getShared("ds"); //when environment is prepared for the next test, get datasource
    }
}
