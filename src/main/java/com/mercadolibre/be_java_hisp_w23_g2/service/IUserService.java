package com.mercadolibre.be_java_hisp_w23_g2.service;

import com.mercadolibre.be_java_hisp_w23_g2.dto.MessageDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowedDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowersCountDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowersDTO;

import java.util.List;

public interface IUserService {
    UserFollowersCountDTO getFollowersCountSeller(int userId);
    UserFollowedDTO getFollowedUser(int userId);
    UserFollowersDTO getFollowersUser(int userId);
    List<UserDTO> getAll();
    UserFollowedDTO followUser(int userId, int userIdToFollow);
    MessageDTO unfollowUser(int userId, int userIdToUnfollow);

}
