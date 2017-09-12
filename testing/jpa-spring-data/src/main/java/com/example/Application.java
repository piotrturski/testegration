package com.example;

import lombok.Builder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {SpringApplication.run(Application.class, args);}
}

@Entity @Builder
class SampleEntity {
    @GeneratedValue @Id int id;
    String name;
    int age;
}

@Service
class ExpensiveService {

    public ExpensiveService(Repo repo) {
        throw new RuntimeException("too expensive for our tests");
    }
}

interface Repo extends Repository<SampleEntity, Integer> {

    Long countByNameAndAge(String name, int age);
}
