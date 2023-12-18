package com.mercadolibre.be_java_hisp_w23_g2.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w23_g2.entity.User;
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

    public List<User> getAll() {
        return users;
    }

    @Override
    public User followUser(int userId, int userIdToFollow) {
        User user = findUserById(userId);

        user.getFollowed().add(findUserById(userIdToFollow));
        return user;

    }
}
