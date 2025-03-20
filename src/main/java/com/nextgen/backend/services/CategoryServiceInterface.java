package com.nextgen.backend.services;

import com.nextgen.backend.dtos.CategoryDTO;
import com.nextgen.backend.exceptions.CategoryException;

import java.util.List;
import java.util.UUID;

public interface CategoryServiceInterface {
    public CategoryDTO addCategory(CategoryDTO categoryDTO) throws CategoryException;
    public CategoryDTO updateCategory(UUID id, CategoryDTO categoryDTO) throws CategoryException;
    public void deleteCategory(UUID id) throws CategoryException;
    public List<CategoryDTO> findAllCategories();
    public CategoryDTO findCategoryById(UUID id) throws CategoryException;;
}
