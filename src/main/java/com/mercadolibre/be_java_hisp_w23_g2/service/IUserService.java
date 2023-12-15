package com.mercadolibre.be_java_hisp_w23_g2.service;

import com.mercadolibre.be_java_hisp_w23_g2.dto.UserFollowersCountDTO;

public interface IUserService {
    UserFollowersCountDTO getFollowersCountSeller(int userId);
}
