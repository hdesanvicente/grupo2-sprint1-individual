package com.mercadolibre.be_java_hisp_w23_g2.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class User {
    private int id;
    private String userName;
    private List<Post> posts;
    private List<User> followers;
    private List<User> followed;
}
