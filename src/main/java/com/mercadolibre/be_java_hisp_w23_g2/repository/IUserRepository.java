package com.mercadolibre.be_java_hisp_w23_g2.repository;

import com.mercadolibre.be_java_hisp_w23_g2.entity.User;

public interface IUserRepository {
    User findUserById(int id);
}
