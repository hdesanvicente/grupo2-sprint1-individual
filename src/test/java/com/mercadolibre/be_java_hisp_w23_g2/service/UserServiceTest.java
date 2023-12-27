package com.mercadolibre.be_java_hisp_w23_g2.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowersCountDTO;
import com.mercadolibre.be_java_hisp_w23_g2.entity.User;
import com.mercadolibre.be_java_hisp_w23_g2.exception.NotFollowingException;
import com.mercadolibre.be_java_hisp_w23_g2.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository repository;

    @InjectMocks
    UserService service;

    @DisplayName("T-0007 - US 02 - Followers count seller OK")
    @Test
    void getFollowersCountSellerTest() {
        //Arrange
        User user = new User();
        user.setId(1);
        user.setUserName("John Doe");

        User follower1 = new User();
        User follower2 = new User();

        follower1.setId(2);
        follower1.setUserName("Alice Smith");
        follower2.setId(3);
        follower2.setUserName("Bob Jones");

        user.setFollowers(List.of(follower1, follower2));
        when(repository.findUserById(user.getId())).thenReturn(user);

        // Act
        UserFollowersCountDTO expectedResult = service.getFollowersCountSeller(user.getId());

        // Assert
        Assertions.assertEquals(user.getFollowers().size(), expectedResult.getFollowersCount());
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
