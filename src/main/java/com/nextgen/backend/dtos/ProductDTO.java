package com.nextgen.backend.dtos;

import com.nextgen.backend.entites.ProductImage;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class ProductDTO {
    private UUID id;
    private String name;
    private String description;
    private Integer price;
    private Integer stock;
    private Set<ProductImage> images = new HashSet<>();
    private UUID category_id;
}
