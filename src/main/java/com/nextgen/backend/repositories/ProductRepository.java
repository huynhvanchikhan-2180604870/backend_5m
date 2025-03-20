package com.nextgen.backend.repositories;

import com.nextgen.backend.entites.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("select t from Product t where t.id = ?1")
    public Product findProductById(UUID id);
}
