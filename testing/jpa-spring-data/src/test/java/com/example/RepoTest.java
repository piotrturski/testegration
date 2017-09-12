package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class RepoTest extends DbTest {

    @Autowired Repo repo;
    @Autowired TestEntityManager testEntityManager;

    @Test
    public void should_count_by_age_and_name() {

        testEntityManager.persistAndFlush(SampleEntity.builder().age(4).name("john").build());
        testEntityManager.persistAndFlush(SampleEntity.builder().age(5).name("john").build());
        testEntityManager.persistAndFlush(SampleEntity.builder().age(5).name("john").build());

        long count = repo.countByNameAndAge("john", 5);

        assertThat(count).isEqualTo(2);
    }

}