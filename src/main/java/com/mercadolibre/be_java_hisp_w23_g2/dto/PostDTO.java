package com.mercadolibre.be_java_hisp_w23_g2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class PostDTO {
    @JsonProperty("post_id")
    private int id;
    @JsonProperty("user_id")
    private int userId;
    private Date date;
    private ProductDTO product;
    private String category;
    private double price;
}
