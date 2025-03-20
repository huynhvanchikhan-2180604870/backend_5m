package com.nextgen.backend.services;

import com.nextgen.backend.dtos.CategoryDTO;
import com.nextgen.backend.dtos.ProductDTO;
import com.nextgen.backend.dtos.mappers.CategoryDtoMapper;
import com.nextgen.backend.dtos.mappers.ProductDtoMapper;
import com.nextgen.backend.entites.Product;
import com.nextgen.backend.exceptions.CategoryException;
import com.nextgen.backend.exceptions.ProductException;
import com.nextgen.backend.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImplement implements ProductServiceInterface {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryServiceInterface categoryServiceInterface;

    @Transactional
    @Override
    public ProductDTO createNewProduct(ProductDTO productDTO) throws ProductException, CategoryException {
        CategoryDTO category = categoryServiceInterface.findCategoryById(productDTO.getCategory_id());
        if (category == null) {
            throw new CategoryException("Category not found");
        }

        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setDescription(productDTO.getDescription());
        product.setCategory(CategoryDtoMapper.dtoToEntity(category));

        return ProductDtoMapper.toProductDTO(productRepository.save(product));
    }

    @Transactional
    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, UUID id) throws ProductException {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isEmpty()) {
            throw new ProductException("Product not found");
        }

        Product product = existingProduct.get();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setDescription(productDTO.getDescription());

        return ProductDtoMapper.toProductDTO(productRepository.save(product));
    }

    @Transactional
    @Override
    public void deleteProduct(UUID id) throws ProductException {
        if (!productRepository.existsById(id)) {
            throw new ProductException("Product not found");
        }
        productRepository.deleteById(id);
    }

    @Transactional
    @Override
    public ProductDTO updateStock(UUID id, int quantity) throws ProductException {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isEmpty()) {
            throw new ProductException("Product not found");
        }

        Product product = existingProduct.get();
        if (product.getStock() < quantity) {
            throw new ProductException("Insufficient stock");
        }

        product.setStock(product.getStock() - quantity);
        return ProductDtoMapper.toProductDTO(productRepository.save(product));
    }

    @Override
    public List<ProductDTO> findAllProduct() {
        return ProductDtoMapper.toProductDTOs(productRepository.findAll());
    }

    @Override
    public ProductDTO findProductById(UUID id) throws ProductException {
        Product existingProduct = productRepository.findProductById(id);
        if(existingProduct == null){
            throw new ProductException("Product not found");
        }

        return ProductDtoMapper.toProductDTO(existingProduct);
    }
}
