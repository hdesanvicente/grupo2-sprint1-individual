package com.mercadolibre.be_java_hisp_w23_g2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class User {
    private int id;
    private String userName;
    private List<Post> posts;
    private List<User> followers;
    private List<User> followed;
}
