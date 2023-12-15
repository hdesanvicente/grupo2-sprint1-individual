package com.mercadolibre.be_java_hisp_w23_g2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private int id;
    private int userId;
    private Date date;
    private Product product;
    private String category;
    private double price;
}
