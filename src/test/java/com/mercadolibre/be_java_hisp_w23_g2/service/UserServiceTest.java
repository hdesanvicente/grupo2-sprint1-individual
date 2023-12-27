package com.mercadolibre.be_java_hisp_w23_g2.service;

import com.mercadolibre.be_java_hisp_w23_g2.dto.UserDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowedDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowersDTO;
import com.mercadolibre.be_java_hisp_w23_g2.entity.User;
import com.mercadolibre.be_java_hisp_w23_g2.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w23_g2.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
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
    @DisplayName("T-0003 Validates that alphabetical order exists on getFollowersUser (asc)")
    void getFollowersUserAscTest() {
        //ARRANGE
        User user_test = new User(1,"melilackington",null,List.of(new User(54,"zendaya99",null,null,null),
                new User(96,"tomHoland87",null,null,null)),null);

        UserFollowersDTO expected = new UserFollowersDTO(1,"melilackington",List.of(new UserDTO(96,"tomHoland87"),
                new UserDTO(54,"zendaya99")));

        when(repository.findUserById(1)).thenReturn(user_test);

        //ACT

        UserFollowersDTO obtain = service.getFollowersUser(1,"name_asc");

        //ASSERT
        assertEquals(expected,obtain);
    }

    @Test
    @DisplayName("T-0003 Validates that alphabetical order exists on getFollowersUser (desc)")
    void getFollowersUserDescTest() {
        //ARRANGE
        User user_test = new User(1,"melilackington",null,List.of(new User(54,"zendaya99",null,null,null),
                new User(96,"tomHoland87",null,null,null)),null);

        UserFollowersDTO expected = new UserFollowersDTO(1,"melilackington",List.of(new UserDTO(54,"zendaya99"),
                new UserDTO(96,"tomHoland87")));

        when(repository.findUserById(1)).thenReturn(user_test);

        //ACT

        UserFollowersDTO obtain = service.getFollowersUser(1,"name_desc");

        //ASSERT
        assertEquals(expected,obtain);
    }

    @Test
    @DisplayName("T-0003 Validates that alphabetical order exists on getFollowersUser Exception")
    void getFollowersUserExceptionTest() {
        //ARRANGE
        List<User> empty_followers = new ArrayList<>();
        User user_test = new User(1,"melilackington",null,empty_followers,null);

        when(repository.findUserById(1)).thenReturn(user_test);

        //ACT && ASSERT
        assertThrows(NotFoundException.class,()->service.getFollowersUser(1,"name_desc"));
    }

    @Test
    @DisplayName("T-0003 Validates that alphabetical order exists on getFollowedUser (asc)")
    void getFollowedUserAscTest() {
        //ARRANGE
        User user_test = new User(1,"melilackington",null,null,List.of(new User(54,"zendaya99",null,null,null),
                new User(96,"tomHoland87",null,null,null)));

        UserFollowedDTO expected = new UserFollowedDTO(1,"melilackington",List.of(new UserDTO(96,"tomHoland87"),
                new UserDTO(54,"zendaya99")));

        when(repository.findUserById(1)).thenReturn(user_test);

        //ACT

        UserFollowedDTO obtain = service.getFollowedUser(1,"name_asc");

        //ASSERT
        assertEquals(expected,obtain);
    }

    @Test
    @DisplayName("T-0003 Validates that alphabetical order exists on getFollowedUser (desc)")
    void getFollowedUserDescTest() {
        //ARRANGE
        User user_test = new User(1,"melilackington",null,null,List.of(new User(54,"zendaya99",null,null,null),
                new User(96,"tomHoland87",null,null,null)));

        UserFollowedDTO expected = new UserFollowedDTO(1,"melilackington",List.of(new UserDTO(54,"zendaya99"),
                new UserDTO(96,"tomHoland87")));

        when(repository.findUserById(1)).thenReturn(user_test);

        //ACT

        UserFollowedDTO obtain = service.getFollowedUser(1,"name_desc");

        //ASSERT
        assertEquals(expected,obtain);
    }

    @Test
    @DisplayName("T-0003 Validates that alphabetical order exists on getFollowedUser Exception")
    void getFollowedUserExceptionTest() {
        //ARRANGE
        List<User> empty_followed = new ArrayList<>();
        User user_test = new User(1,"melilackington",null,null,empty_followed);

        when(repository.findUserById(1)).thenReturn(user_test);

        //ACT && ASSERT
        assertThrows(NotFoundException.class,()->service.getFollowedUser(1,"name_desc"));
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
