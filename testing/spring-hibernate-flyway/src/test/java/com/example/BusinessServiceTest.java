package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BusinessServiceTest extends DbTest {

    @Autowired BusinessService businessService;

    @Test
    public void should_commit_two_insertions() {
        long count = businessService.addTwoAndCount();

        assertThat(count).isEqualTo(2);
    }

    @Test
    public void should_commit_two_insertions_again() {
        long count = businessService.addTwoAndCount();

        assertThat(count).isEqualTo(2); // still 2, we don't care what was committed before
    }
}