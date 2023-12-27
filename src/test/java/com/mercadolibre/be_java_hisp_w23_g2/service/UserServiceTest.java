package com.mercadolibre.be_java_hisp_w23_g2.service;

import com.mercadolibre.be_java_hisp_w23_g2.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository repository;

    @InjectMocks
    UserService service;

    @Test
    void getFollowersCountSeller() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getFollowersUser() {
    }

    @Test
    void getFollowedUser() {
    }

    @Test
    void unfollowUser() {
    }

    @Test
    void followUser() {
    }

    @Test
    void getPostsByFollowedUsers() {
    }
}
