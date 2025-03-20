package com.nextgen.backend.services;

import com.nextgen.backend.dtos.ProductDTO;
import com.nextgen.backend.exceptions.CategoryException;
import com.nextgen.backend.exceptions.ProductException;

import java.util.List;
import java.util.UUID;

public interface ProductServiceInterface {
    public ProductDTO createNewProduct(ProductDTO productDTO) throws ProductException, CategoryException;
    public ProductDTO updateProduct(ProductDTO productDTO, UUID id) throws ProductException, CategoryException;
    public void deleteProduct(UUID id) throws ProductException, CategoryException;
    public ProductDTO updateStock(UUID id, int quantity) throws ProductException, CategoryException;
    public List<ProductDTO> findAllProduct();
    public ProductDTO findProductById(UUID id) throws ProductException;
}
