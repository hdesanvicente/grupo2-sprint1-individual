package com.mercadolibre.be_java_hisp_w23_g2.service;

import com.mercadolibre.be_java_hisp_w23_g2.dto.PostFollowedDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.MessageDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowedDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowersCountDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowersDTO;

import java.util.List;

public interface IUserService {
    UserFollowersCountDTO getFollowersCountSeller(Integer userId);
    List<UserDTO> getAll();
    UserFollowedDTO followUser(Integer userId, Integer userIdToFollow);
    MessageDTO unfollowUser(Integer userId, Integer userIdToUnfollow);
    UserFollowersDTO getFollowersUser(Integer userId, String sortType);
    UserFollowedDTO getFollowedUser(Integer userId, String sortType);
    PostFollowedDTO getPostsByFollowedUsers(Integer userId, String sortType);
}
