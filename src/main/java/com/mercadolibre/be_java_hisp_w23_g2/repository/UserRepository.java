package com.mercadolibre.be_java_hisp_w23_g2.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w23_g2.entity.User;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Repository
public class UserRepository implements IUserRepository {
    private final List<User> users;

    public UserRepository() throws IOException {
        users = loadData();
    }

    public List<User> loadData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/users.json"), new TypeReference<>(){});
    }

    @Override
    public List<User> getAll() {
        return users;
    }
}
