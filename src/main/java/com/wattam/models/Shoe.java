package com.wattam.models;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Shoes")
public class Shoe {

    @Id
    private String id;
    private String name;
    private String style;
    private String colour;
    private String material;
    private BigDecimal price;

}
