package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RepoIntegrationTest extends DbIntegrationTest {

    @Autowired Repo repo;

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