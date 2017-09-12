package com.example;

import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NgTest {

    Repo repo;

    @BeforeMethod
    public void prepareDb() {
        DataSource dataSource = StandaloneDbSetup.prepareDataSourceForTest();
        repo = new Repo(new JdbcTemplate(dataSource));
    }

    @Test
    public void should_insert_all_values() {

        repo.save_ints_in_batches_by_two(Arrays.asList(1,2,3,4,5));

        int count = repo.count();
        assertThat(count).isEqualTo(5);
    }

    @Test
    public void should_fail_on_batch_containing_null() {

        assertThatThrownBy(() ->
                repo.save_ints_in_batches_by_two(Arrays.asList(1,2,3,null,5))
        ).isNotNull();

        int count = repo.count();
        assertThat(count).as("only 1st batch of size 2 should have succeeded")
                .isEqualTo(2);
    }
}
