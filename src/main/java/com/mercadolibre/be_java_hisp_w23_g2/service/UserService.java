package com.mercadolibre.be_java_hisp_w23_g2.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w23_g2.dto.*;
import com.mercadolibre.be_java_hisp_w23_g2.entity.Post;
import com.mercadolibre.be_java_hisp_w23_g2.entity.User;
import com.mercadolibre.be_java_hisp_w23_g2.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w23_g2.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w23_g2.utils.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
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
    public List<UserDTO> getFollowersUser(int userId) {
        ObjectMapper mapper = new ObjectMapper();
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new NotFoundException("User with id = " + userId + " not found");
        }
        if (user.getFollowers().isEmpty()) {
            throw new NotFoundException("User with id = " + userId + " has no followers");
        }
        return user.getFollowers().stream().map(follower -> mapper.convertValue(follower, UserDTO.class)).toList();
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
    public PostFollowedDTO getPostsByFollowedUsers(int userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new NotFoundException("User with id = " + userId + " not found");
        }
        if (user.getFollowed() == null || user.getFollowed().isEmpty() ) {
            throw new NotFoundException("User with id = " + userId + " has no followed");
        }

        LocalDate twoWeeksAgo = LocalDate.now().minusWeeks(2);
        List<Post> allPost = new ArrayList<>();

        for (User followedUser : user.getFollowed()){
            User userf = userRepository.findUserById(followedUser.getId());
            if (userf == null){
                throw new NotFoundException("User followed with id = " + followedUser.getId() + " not found");
            }
            if (userf.getPosts() == null || userf.getPosts().isEmpty()){
                throw new NotFoundException("User followed with id = " + followedUser.getId() + " has no post");
            }
            for (Post postF : userf.getPosts()){
                LocalDate postDate = postF.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (postDate.isAfter(twoWeeksAgo)){
                    allPost.add(postF);
                }
            }
        }
        return Mapper.mapPostFollowedDTO(user.getId(),allPost);
    }
}
