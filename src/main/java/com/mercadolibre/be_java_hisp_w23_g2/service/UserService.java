package com.mercadolibre.be_java_hisp_w23_g2.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w23_g2.dto.MessageDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowedDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowersCountDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowersDTO;
import com.mercadolibre.be_java_hisp_w23_g2.entity.User;

import com.mercadolibre.be_java_hisp_w23_g2.exception.BadRequestException;

import com.mercadolibre.be_java_hisp_w23_g2.exception.NotFollowingException;
import com.mercadolibre.be_java_hisp_w23_g2.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w23_g2.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w23_g2.utils.Mapper;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserFollowersCountDTO getFollowersCountSeller(int userId) {
        User user = userRepository.findUserById(userId);
        validateUserExistence(user, userId, "Current");

        return Mapper.mapUserFollowersCountDTO(user);
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
        User user = userRepository.findUserById(userId);
        validateUserExistence(user, userId, "Current");

        if (user.getFollowers() == null || user.getFollowers().isEmpty()) {
            throw new NotFoundException("User with id = " + userId + " has no followers");
        }
        return Mapper.mapUserFollowersDTO(user);

    }

    @Override
    public UserFollowedDTO getFollowedUser(int userId) {
        User user = userRepository.findUserById(userId);
        validateUserExistence(user, userId, "Current");

        if (user.getFollowed() == null || user.getFollowed().isEmpty() ) {
            throw new NotFoundException("User with id = " + userId + " has no followed");
        }
        return Mapper.mapUserFollowedDTO(user);
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

    @Override
    public UserFollowedDTO followUser(int userId, int userIdToFollow) {
        if (userId == userIdToFollow){
            throw new BadRequestException("A user cannot follow himself");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        User user = userRepository.findUserById(userId);
        User user2 = userRepository.findUserById(userIdToFollow);


        if (user == null){
            throw new BadRequestException("The user "+userId+" doesn't exist");
        }

        if (user2 == null){
            throw new BadRequestException("The user "+userIdToFollow+" doesn't exist");
        }

        if (user.getFollowed().contains(userRepository.findUserById(userIdToFollow))) {
            throw new BadRequestException("The user " + userId + " allready follow " + userIdToFollow);
        }

        return Mapper.mapUserFollowedDTO(userRepository.followUser(userId,userIdToFollow));
    }
}
