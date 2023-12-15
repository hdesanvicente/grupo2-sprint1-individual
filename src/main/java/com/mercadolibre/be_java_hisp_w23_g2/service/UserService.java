package com.mercadolibre.be_java_hisp_w23_g2.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowersCountDTO;
import com.mercadolibre.be_java_hisp_w23_g2.entity.User;
import com.mercadolibre.be_java_hisp_w23_g2.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w23_g2.repository.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserFollowersCountDTO getFollowersCountSeller(int userId) {
        ObjectMapper mapper = new ObjectMapper();
        User user = userRepository.findUserById(userId);
        if(user==null){
            throw new NotFoundException("User with id = " + userId + " not found");
        }
        return mapper.convertValue(user,UserFollowersCountDTO.class);
    }
}
