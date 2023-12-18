package com.mercadolibre.be_java_hisp_w23_g2.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w23_g2.entity.Post;
import com.mercadolibre.be_java_hisp_w23_g2.entity.User;
import com.mercadolibre.be_java_hisp_w23_g2.exception.NotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Repository
public class UserRepository implements IUserRepository {
    private final List<User> users;

    public UserRepository() throws IOException {
        users = loadData();
    }

    public List<User> loadData() throws IOException {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:users.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<User>> typeRef = new TypeReference<>() {
        };
        List<User> users = null;
        try {
            users = objectMapper.readValue(file, typeRef);
            for (User user : users) {
                for (int j = 0; j < user.getFollowers().size(); j++) {
                    List<User> finalUsers = users;
                    user.setFollowers(user.getFollowers().stream().map(u -> finalUsers
                            .stream().filter(u1 -> u.getId() == u1.getId()).findFirst().orElse(null)).toList());
                }
                for (int j = 0; j < user.getFollowed().size(); j++) {
                    List<User> finalUsers = users;
                    user.setFollowed(user.getFollowed().stream().map(u -> finalUsers
                            .stream().filter(u1 -> u.getId() == u1.getId()).findFirst().orElse(null)).toList());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findUserById(int id) {
        return users
                .stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public void addPost(User user, Post post) {
        user.getPosts().add(post);
    }

    @Override
    public User followUser(int userId, int userIdToFollow) {
        User user = findUserById(userId);

        user.getFollowed().add(findUserById(userIdToFollow));
        return user;
    }

    @Override
    public void unfollowUser(User currentUser, User userToUnfollow) {
        currentUser.getFollowed().remove(userToUnfollow);
        userToUnfollow.getFollowers().remove(currentUser);

    }
}
