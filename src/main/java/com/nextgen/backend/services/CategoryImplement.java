package com.nextgen.backend.services;

import com.nextgen.backend.dtos.CategoryDTO;
import com.nextgen.backend.dtos.mappers.CategoryDtoMapper;
import com.nextgen.backend.entites.Category;
import com.nextgen.backend.exceptions.CategoryException;
import com.nextgen.backend.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryImplement implements CategoryServiceInterface {
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) throws CategoryException {
        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        Category saveCategory = categoryRepository.save(category);
        CategoryDTO convert = CategoryDtoMapper.entityToDTO(saveCategory);
        return convert;
    }

    @Transactional
    @Override
    public CategoryDTO updateCategory(UUID id, CategoryDTO categoryDTO) throws CategoryException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new CategoryException("Category id not found");
        }

        Category currenExist = optionalCategory.get();

        if (categoryDTO.getName() != null && !categoryDTO.getName().equals(currenExist.getName())) {
            currenExist.setName(categoryDTO.getName());
        }

        if (categoryDTO.getDescription() != null && !categoryDTO.getDescription().equals(currenExist.getDescription())) {
            currenExist.setDescription(categoryDTO.getDescription());
        }

        Category savedCategory = categoryRepository.save(currenExist);
        return CategoryDtoMapper.entityToDTO(savedCategory);
    }


    @Override
    public void deleteCategory(UUID id) throws CategoryException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new CategoryException("Category id not found");
        }
        Category currenExist = optionalCategory.get();
        categoryRepository.delete(currenExist);
    }

    @Override
    public List<CategoryDTO> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> dtos = CategoryDtoMapper.entityToDTOs(categories);
        return dtos;
    }

    @Override
    public CategoryDTO findCategoryById(UUID id) throws CategoryException {
        Category category = categoryRepository.findCategoryById(id);
        if(category == null){
            throw new CategoryException("Category id not found");
        }
        CategoryDTO convert = CategoryDtoMapper.entityToDTO(category);
        return convert;
    }
}
