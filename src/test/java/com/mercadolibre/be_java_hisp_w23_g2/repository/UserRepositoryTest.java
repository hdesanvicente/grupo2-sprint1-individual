package com.mercadolibre.be_java_hisp_w23_g2.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    private UserRepository repository;

    @BeforeEach
    public void setUp() throws IOException {
        repository = new UserRepository();
    }

    @Test
    void findUserById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void addPost() {
    }

    @Test
    void followUser() {
    }

    @Test
    void unfollowUser() {
    }

    @Test
    void loadData() {
    }
}
