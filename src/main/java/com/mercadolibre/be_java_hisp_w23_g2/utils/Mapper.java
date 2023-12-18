package com.mercadolibre.be_java_hisp_w23_g2.utils;

import com.mercadolibre.be_java_hisp_w23_g2.dto.UserDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowersDTO;
import com.mercadolibre.be_java_hisp_w23_g2.entity.User;

import java.util.ArrayList;
import java.util.List;

public class Mapper {




    public static UserFollowersDTO mapToUserFollowersDTO(User user) {
        List<UserDTO> listUserDto = new ArrayList<>();

        for (User aux: user.getFollowed()
             ) {
            listUserDto.add(Mapper.mapToUserDTO(aux));
        }

        return new UserFollowersDTO(user.getId(), user.getUserName(), listUserDto);
    }

    public static UserDTO mapToUserDTO(User user){
        return new UserDTO(user.getId(), user.getUserName());
    }
}
