package com.acidonper.myapp.entities.repositories;

import com.acidonper.myapp.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class UserRepositoryTest {


    @Autowired
    private UserRepository repository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    void findByDni() {
        repository.save(new User("000X","test", "02"));
        repository.save(new User("001X","test_ok", "01"));
        List<User> persistedUsers = repository.findByDni("001X");
        assertThat(persistedUsers.toString()).isEqualTo("[User[dni='001X', firstName='test_ok', lastName='01']]");
    }
}