package com.mercadolibre.be_java_hisp_w23_g2.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w23_g2.dto.MessageDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowedDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowersCountDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowersDTO;
import com.mercadolibre.be_java_hisp_w23_g2.entity.User;
import com.mercadolibre.be_java_hisp_w23_g2.exception.NotFollowingException;
import com.mercadolibre.be_java_hisp_w23_g2.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w23_g2.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserFollowersCountDTO getFollowersCountSeller(int userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new NotFoundException("User with id = " + userId + " not found");
        }
        return new UserFollowersCountDTO(user.getId(), user.getUserName(), user.getFollowers().size());
    }

    public List<UserDTO> getAll() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> users = userRepository.getAll();
        List<UserDTO> userDTOS = new ArrayList<>();

        for (User aux : users
        ) {
            userDTOS.add(objectMapper.convertValue(aux, UserDTO.class));
        }

        return userDTOS;
    }

    @Override
    public UserFollowersDTO getFollowersUser(int userId) {
        ObjectMapper mapper = new ObjectMapper();
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new NotFoundException("User with id = " + userId + " not found");
        }
        if (user.getFollowers() == null || user.getFollowers().isEmpty()) {
            throw new NotFoundException("User with id = " + userId + " has no followers");
        }
        return mapper.convertValue(user, UserFollowersDTO.class);
    }

    @Override
    public UserFollowedDTO getFollowedUser(int userId) {
        ObjectMapper mapper = new ObjectMapper();
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new NotFoundException("User with id = " + userId + " not found");
        }
        if (user.getFollowed() == null || user.getFollowed().isEmpty() ) {
            throw new NotFoundException("User with id = " + userId + " has no followed");
        }
        return mapper.convertValue(user, UserFollowedDTO.class);
    }

    @Override
    public MessageDTO unfollowUser(int userId, int userIdToUnfollow) {
        User currentUser = userRepository.findUserById(userId);
        validateUserExistence(currentUser, userId, "Current");

        User userToUnfollow = userRepository.findUserById(userIdToUnfollow);
        validateUserExistence(userToUnfollow, userIdToUnfollow, "To unfollow");

        validateFollowing(currentUser, userIdToUnfollow);

        userRepository.unfollowUser(currentUser, userToUnfollow);

        return new MessageDTO("Has stopped following " + userToUnfollow.getUserName());
    }

    private void validateUserExistence(User user, int userId, String userType) {
        if (user == null) {
            throw new NotFoundException(String.format("%s user with id = %d not exists.", userType, userId));
        }
    }

    private void validateFollowing(User currentUser, int userIdToUnfollow) {
        if (currentUser.getFollowed() == null ||
                currentUser.getFollowed().stream().filter(user -> user.getId() == userIdToUnfollow).findFirst().orElse(null) == null) {
            throw new NotFollowingException("The current user does not follow the user to unfollow.");
        }
    }
}
