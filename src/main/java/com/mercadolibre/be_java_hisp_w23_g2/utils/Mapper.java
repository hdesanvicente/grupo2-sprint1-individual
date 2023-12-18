package com.mercadolibre.be_java_hisp_w23_g2.utils;

import com.mercadolibre.be_java_hisp_w23_g2.dto.UserDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowedDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowersCountDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowersDTO;
import com.mercadolibre.be_java_hisp_w23_g2.entity.User;

public class Mapper {
    public static UserDTO mapUserDTO(User user) {
        return new UserDTO(user.getId(), user.getUserName());
    }

    public static UserDTO mapUserDTO(User user){
        return new UserDTO(user.getId(), user.getUserName());

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
