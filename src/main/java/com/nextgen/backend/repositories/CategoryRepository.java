package com.nextgen.backend.repositories;

import com.nextgen.backend.entites.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    @Query("select c from Category c where c.id = ?1")
    public Category findCategoryById(UUID id);
}
