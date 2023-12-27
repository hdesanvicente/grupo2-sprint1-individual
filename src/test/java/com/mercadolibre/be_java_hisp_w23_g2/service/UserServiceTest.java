package com.mercadolibre.be_java_hisp_w23_g2.service;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w23_g2.dto.PostDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.PostFollowedDTO;
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
import org.mockito.junit.jupiter.MockitoExtension;

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
}
