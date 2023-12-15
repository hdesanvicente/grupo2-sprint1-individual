package com.mercadolibre.be_java_hisp_w23_g2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private String type;
    private String brand;
    private String color;
    private String notes;
 }
