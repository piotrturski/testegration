package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.NEVER;
import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {SpringApplication.run(Repo.class, args);}
}

@Entity
class SampleEntity {
    @Id int id;
}

@Service
class BusinessService {

    @Autowired Repo repo;

    /** business method that commits two transactions */
    @Transactional(NEVER)
    public long addTwoAndCount() {
        repo.addAndCount();
        return repo.addAndCount();
    }

}

@Repository
@Transactional(REQUIRES_NEW)
class Repo {

    @Autowired EntityManager entityManager;

    public long addAndCount() {
        entityManager.persist(new SampleEntity());
        return entityManager.createQuery("select count(1) from SampleEntity", Long.class)
                .getSingleResult();
    }
}
