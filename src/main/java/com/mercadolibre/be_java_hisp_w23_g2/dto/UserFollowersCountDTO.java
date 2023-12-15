package com.mercadolibre.be_java_hisp_w23_g2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserFollowersCountDTO {
    @JsonProperty("user_id")
    private int id;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("followers_count")
    private int followersCount;
}
