package com.nextgen.backend.dtos.mappers;

import com.nextgen.backend.dtos.ProductDTO;
import com.nextgen.backend.entites.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDtoMapper {
    public static ProductDTO toProductDTO(Product product){
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setImages(product.getImages());
        dto.setDescription(product.getDescription());
        dto.setStock(product.getStock());
        dto.setCategory_id(product.getCategory().getId());
        return  dto;
    }

    public static List<ProductDTO> toProductDTOs(List<Product> products){
        List<ProductDTO> dtos = new ArrayList<>();
        products.forEach(product -> {
            ProductDTO dto = toProductDTO(product);
            dtos.add(dto);
        });
        return  dtos;
    }
}
