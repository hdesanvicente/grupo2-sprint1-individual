package com.mercadolibre.be_java_hisp_w23_g2.service;

import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowersCountDTO;
import com.mercadolibre.be_java_hisp_w23_g2.exception.NotFollowingException;
import com.mercadolibre.be_java_hisp_w23_g2.repository.UserRepository;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowedDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowersDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.PostDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.PostFollowedDTO;

import com.mercadolibre.be_java_hisp_w23_g2.exception.NotFoundException;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mercadolibre.be_java_hisp_w23_g2.entity.Post;
import com.mercadolibre.be_java_hisp_w23_g2.entity.Product;
import com.mercadolibre.be_java_hisp_w23_g2.entity.User;
import com.mercadolibre.be_java_hisp_w23_g2.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
  @DisplayName("Test method to obtain posts with the last two weeks old")
  void getPostsWithLastTwoWeeksOld() {
    //Arrange
    User user = new User();
    User follower1 = new User();
    User follower2 = new User();
    user.setId(1);
    follower1.setId(2);
    follower2.setId(3);

    Post post21 = new Post(1, 2, LocalDate.now().minusWeeks(1), new Product(), null, 0.0);
    Post post22 = new Post(2, 2, LocalDate.now().minusDays(5), new Product(), null, 0.0);
    Post post23 = new Post(3, 2, LocalDate.now().minusDays(14), new Product(), null, 0.0);
    Post post24 = new Post(4, 2, LocalDate.now().minusDays(15), new Product(), null, 0.0);

    Post post31 = new Post(1, 3, LocalDate.now().minusWeeks(1), new Product(), null, 0.0);
    Post post32 = new Post(2, 3, LocalDate.now().minusDays(15), new Product(), null, 0.0);
    Post post33 = new Post(3, 3, LocalDate.now().minusDays(14), new Product(), null, 0.0);
    
    follower1.setPosts(List.of(post21, post22, post23, post24));
    follower2.setPosts(List.of(post31, post32, post33));

    user.setFollowed(List.of(follower1, follower2));

    ObjectMapper mapper = new ObjectMapper();
    List<PostDTO> postDTOS = Stream.of(post21, post22, post23, post31, post33)
        .map(post -> mapper.convertValue(post, PostDTO.class)).toList();

    PostFollowedDTO followedDTO = new PostFollowedDTO(user.getId(), postDTOS);

    when(repository.findUserById(1)).thenReturn(user);
    when(repository.findUserById(2)).thenReturn(follower1);
    when(repository.findUserById(3)).thenReturn(follower2);
    //Act
    PostFollowedDTO result = service.getPostsByFollowedUsers(user.getId(), null);
    //Assert
    Assertions.assertEquals(followedDTO, result);
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
}
