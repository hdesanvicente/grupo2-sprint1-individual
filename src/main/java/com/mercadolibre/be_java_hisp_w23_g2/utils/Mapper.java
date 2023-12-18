package com.mercadolibre.be_java_hisp_w23_g2.utils;

import com.mercadolibre.be_java_hisp_w23_g2.dto.UserDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowedDTO;
import com.mercadolibre.be_java_hisp_w23_g2.entity.User;

public class Mapper {


    public static UserFollowedDTO mapUserFollowedDTO(User user) {
        return new UserFollowedDTO(user.getId(), user.getUserName(),
                user.getFollowed().stream().map(Mapper::mapUserDTO).toList());
    }

    public static UserDTO mapUserDTO(User user){
        return new UserDTO(user.getId(), user.getUserName());
    }
}
