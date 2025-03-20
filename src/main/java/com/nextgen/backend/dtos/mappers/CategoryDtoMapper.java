package com.nextgen.backend.dtos.mappers;

import com.nextgen.backend.dtos.CategoryDTO;
import com.nextgen.backend.entites.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDtoMapper {
    public static CategoryDTO entityToDTO(Category category){
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }

    public static List<CategoryDTO> entityToDTOs(List<Category> categories){
        List<CategoryDTO> dtos = new ArrayList<>();
        for(Category i : categories){
            CategoryDTO dto = entityToDTO(i);
            dtos.add(dto);
        }
        return dtos;
    }

    public static Category dtoToEntity(CategoryDTO dto){
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return  category;
    }

    public static List<Category> dtoToEntitys(List<CategoryDTO> dtos){
        List<Category> categories = new ArrayList<>();
        for(CategoryDTO i : dtos){
            Category category = dtoToEntity(i);
            categories.add(category);
        }
        return categories;
    }
}
