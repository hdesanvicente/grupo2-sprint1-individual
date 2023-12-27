package com.mercadolibre.be_java_hisp_w23_g2.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.be_java_hisp_w23_g2.dto.MessageDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowedDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowersDTO;
import com.mercadolibre.be_java_hisp_w23_g2.entity.Post;
import com.mercadolibre.be_java_hisp_w23_g2.entity.User;
import com.mercadolibre.be_java_hisp_w23_g2.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w23_g2.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
    @DisplayName("T-0001: Verify that the user to follow exists.")
    void followUserTest() {
        // ARRANGE
        User userToFollow = new User();
        userToFollow.setId(1);
        userToFollow.setUserName("John Doe");

        User userFollower = new User();
        userFollower.setId(2);
        userFollower.setUserName("Alice Smith");

        when(repository.findUserById(1)).thenReturn(userToFollow);
        when(repository.findUserById(2)).thenReturn(userFollower);
        when(repository.followUser(2,1)).thenReturn(userToFollow);

        UserFollowedDTO expected = new UserFollowedDTO(1, "John Doe", List.of(new UserDTO(2, "Alice Smith")));

        // ACT
        UserFollowedDTO result = service.followUser(2, 1);

        // ASSERT
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("T-0001: Verify that the user to follow exists. Exception.")
    void followUserExceptionTest(){
        // ARRANGE
        User userFollower = new User();
        userFollower.setId(2);
        userFollower.setUserName("Alice Smith");

        when(repository.findUserById(2)).thenReturn(userFollower);

        // ACT & ASSERT
        assertThrows(NotFoundException.class, () -> {
            service.followUser(2, 1);
        });
    }

    @Test
    void getPostsByFollowedUsers() {
    }
}
