package com.mercadolibre.be_java_hisp_w23_g2.repository;

import com.mercadolibre.be_java_hisp_w23_g2.entity.User;
import com.mercadolibre.be_java_hisp_w23_g2.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository implements IUserRepository {
    private final List<User> users;

    public UserRepository() {
        this.users = List.of();
    }


    @Override
    public User findUserById(int id) {
        return users
                .stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
