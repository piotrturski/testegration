package com.example;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@SpringBootApplication
@Repository
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class Repo {
    final JdbcTemplate jdbcTemplate;

    /** fails at first batch containing null */
    public void save_ints_in_batches_by_two(List<Integer> ints) {
        jdbcTemplate.batchUpdate("insert into some_table values (?)", ints, 2,
                (ps, value) -> ps.setInt(1, value));
    }

    /** executes stored function */
    public int count() {
        return jdbcTemplate.queryForObject("select my_count()", Integer.class);
    }

    public static void main(String[] args) {SpringApplication.run(Repo.class, args);}
}
