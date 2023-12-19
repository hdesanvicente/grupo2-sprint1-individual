package com.mercadolibre.be_java_hisp_w23_g2.service;

import com.mercadolibre.be_java_hisp_w23_g2.dto.*;
import com.mercadolibre.be_java_hisp_w23_g2.entity.Post;
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

import java.time.LocalDate;
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
        List<User> users = userRepository.getAll();

        return users.stream().map(Mapper::mapUserDTO).toList();
    }

    @Override
    public UserFollowersDTO getFollowersUser(int userId, String sortType) {
        User user = userRepository.findUserById(userId);
        validateUserExistence(user, userId, "Current");

        if (user.getFollowers() == null || user.getFollowers().isEmpty()) {
            throw new NotFoundException("User with id = " + userId + " has no followers");
        }
        if(sortType != null){
            user.setFollowers(userSortHandler(new ArrayList<>(user.getFollowers()), sortType));
        }
        return Mapper.mapUserFollowersDTO(user);
    }

    @Override
    public UserFollowedDTO getFollowedUser(int userId, String sortType) {
        User user = userRepository.findUserById(userId);
        validateUserExistence(user, userId, "Current");

        checkIfUserHasFollowed(user);

        if(sortType != null){
            user.setFollowed(userSortHandler(new ArrayList<>(user.getFollowed()), sortType));
        }

        return Mapper.mapUserFollowedDTO(user);
    }

    @Override
    public MessageDTO unfollowUser(int userId, int userIdToUnfollow) {
        validateThatItIsNotTheSameUser(userId, userIdToUnfollow);

        User currentUser = userRepository.findUserById(userId);
        validateUserExistence(currentUser, userId, "Current");

        User userToUnfollow = userRepository.findUserById(userIdToUnfollow);
        validateUserExistence(userToUnfollow, userIdToUnfollow, "To unfollow");

        validateFollowing(currentUser, userIdToUnfollow);

        userRepository.unfollowUser(currentUser, userToUnfollow);

        return new MessageDTO("Has stopped following " + userToUnfollow.getUserName());
    }

    @Override
    public UserFollowedDTO followUser(int userId, int userIdToFollow) {
        validateThatItIsNotTheSameUser(userId, userIdToFollow);

        User user = userRepository.findUserById(userId);
        validateUserExistence(user, userId, "Current");

        User user2 = userRepository.findUserById(userIdToFollow);
        validateUserExistence(user2, userIdToFollow, "To Follow");

        if (user.getFollowed().contains(userRepository.findUserById(userIdToFollow))) {
            throw new BadRequestException("The user " + userId + " allready follow " + userIdToFollow);
        }

        return Mapper.mapUserFollowedDTO(userRepository.followUser(userId,userIdToFollow));
    }

    @Override
    public PostFollowedDTO getPostsByFollowedUsers(int userId, String sortType) {
        User user = userRepository.findUserById(userId);
        validateUserExistence(user, userId, "Current");

        checkIfUserHasFollowed(user);

        LocalDate twoWeeksAgo = LocalDate.now().minusWeeks(2);
        List<Post> allPost = new ArrayList<>();

        for (User followedUser : user.getFollowed()) {
            User userf = userRepository.findUserById(followedUser.getId());
            validateUserExistence(userf, followedUser.getId(), "Followed");

            if (userf.getPosts() == null || userf.getPosts().isEmpty()) {
                throw new NotFoundException("User followed with id = " + followedUser.getId() + " has no post");
            }
            for (Post postF : userf.getPosts()) {
                if (postF.getDate().isAfter(twoWeeksAgo)) {
                    allPost.add(postF);
                }
            }
        }
        if(sortType != null){
            postSortHandler(allPost, sortType);
        }

        return Mapper.mapPostFollowedDTO(user.getId(), allPost);
    }

    public void postSortHandler(List<Post> posts, String sortType){
        String[] attributes = sortType.split("_");
        if(attributes.length < 2){
            return;
        }
        if("date".equals(attributes[0])){
            if("asc".equals(attributes[1])){
                posts.sort(Comparator.comparing(Post::getDate));
            }else{
                posts.sort(Comparator.comparing(Post::getDate).reversed());
            }
        }
    }

    public List<User> userSortHandler(List<User> user, String sortType){
        String[] attributes = sortType.split("_");
        if(attributes.length < 2){
            return user;
        }
        if("name".equals(attributes[0])){
            if("asc".equals(attributes[1])){
                user.sort(Comparator.comparing(User::getUserName));
            }else{
                user.sort(Comparator.comparing(User::getUserName).reversed());
            }
        }
        return user;

    }
  
  private void checkIfUserHasFollowed(User user) {
        if (user.getFollowed() == null || user.getFollowed().isEmpty()) {
            throw new NotFoundException("User with id = " + user.getId() + " has no followed");
        }
    }

    private void validateThatItIsNotTheSameUser(int userId, int userId2){
        if (userId == userId2){
            throw new BadRequestException("A user cannot follow/unfollow himself");
        }
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
