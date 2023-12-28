package com.mercadolibre.be_java_hisp_w23_g2.utils;

import com.mercadolibre.be_java_hisp_w23_g2.dto.PostDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.PostFollowedDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.ProductDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowedDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowersCountDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowersDTO;
import com.mercadolibre.be_java_hisp_w23_g2.entity.Post;
import com.mercadolibre.be_java_hisp_w23_g2.entity.Product;
import com.mercadolibre.be_java_hisp_w23_g2.entity.User;
import java.util.List;

/**
 * Utility class for mapping entities to DTOs and vice versa.
 */
public class Mapper {

  private Mapper() {

  }

  public static PostFollowedDTO mapPostFollowedDTO(int id, List<Post> posts) {
    return new PostFollowedDTO(id, posts.stream().map(Mapper::mapPostToPostDTO).toList());
  }

  public static PostDTO mapPostToPostDTO(Post post) {
    return new PostDTO(post.getUserId(), post.getId(), post.getDate(),
        mapProductToProductDTO(post.getProduct()), post.getCategory(), post.getPrice());
  }

  public static Post mapPostDTOToPost(PostDTO postDTO) {
    return new Post(postDTO.getId(), postDTO.getUserId(), postDTO.getDate(),
        mapProductDTOToProduct(postDTO.getProduct()), postDTO.getCategory(), postDTO.getPrice());
  }

  public static ProductDTO mapProductToProductDTO(Product product) {
    return new ProductDTO(product.getId(), product.getName(), product.getType(), product.getBrand(),
        product.getColor(), product.getNotes());
  }

  public static Product mapProductDTOToProduct(ProductDTO productDTO) {
    return new Product(productDTO.getId(), productDTO.getName(), productDTO.getType(),
        productDTO.getBrand(), productDTO.getColor(), productDTO.getNotes());
  }

  public static UserDTO mapUserDTO(User user) {
    return new UserDTO(user.getId(), user.getUserName());
  }

  public static UserFollowersDTO mapUserFollowersDTO(User user) {
    return new UserFollowersDTO(user.getId(), user.getUserName(),
        user.getFollowers().stream().map(Mapper::mapUserDTO).toList());
  }

  public static UserFollowersCountDTO mapUserFollowersCountDTO(User user) {
    return new UserFollowersCountDTO(user.getId(), user.getUserName(), user.getFollowers().size());
  }

  public static UserFollowedDTO mapUserFollowedDTO(User user) {
    return new UserFollowedDTO(user.getId(), user.getUserName(),
        user.getFollowed().stream().map(Mapper::mapUserDTO).toList());
  }

}
