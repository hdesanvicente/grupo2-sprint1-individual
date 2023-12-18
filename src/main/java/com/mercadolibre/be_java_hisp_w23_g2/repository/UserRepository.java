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
    public <T> T followUser(int userId, int userIdToFollow) {
        User user = findUserById(userId);
        String text;

        if (userId == userIdToFollow){
            text = "Un usuario no se puede seguir a si mismo";
            return (T) text;
        }

        if (findUserById(userId) == null){
            text = "No existe el usuario con id "+userId;
            return (T) text;
        }

        if (findUserById(userIdToFollow) == null){
            text = "No existe el usuario con id "+userIdToFollow;
            return (T) text;
        }

        if (user.getFollowed().contains(findUserById(userIdToFollow))) {
            text = "El usuario "+userId+" ya sigue a "+userIdToFollow;
            return (T) text;}

        user.getFollowed().add(findUserById(userIdToFollow));
        return (T) user;

    }
}
