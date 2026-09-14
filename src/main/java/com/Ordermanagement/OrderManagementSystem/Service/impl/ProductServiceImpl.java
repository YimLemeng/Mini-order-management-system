package com.Ordermanagement.OrderManagementSystem.Service.impl;

import com.Ordermanagement.OrderManagementSystem.DTO.ProductRequest;
import com.Ordermanagement.OrderManagementSystem.DTO.ProductResponse;
import com.Ordermanagement.OrderManagementSystem.Entity.Category;
import com.Ordermanagement.OrderManagementSystem.Entity.Product;
import com.Ordermanagement.OrderManagementSystem.Exception.ResourceNotFoundException;
import com.Ordermanagement.OrderManagementSystem.Mapper.ProductMapper;
import com.Ordermanagement.OrderManagementSystem.Repository.CategoryRepository;
import com.Ordermanagement.OrderManagementSystem.Repository.ProductRepository;
import com.Ordermanagement.OrderManagementSystem.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest dto){
        Product product = productMapper.toEntity(dto);
        if(dto.getCategoryIds() != null && !dto.getCategoryIds().isEmpty()){
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(dto.getCategoryIds()));
            product.setCategory(categories);
        }
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        return productMapper.toDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(String name, int page, int size, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> products;
        if(name != null && !name.trim().isEmpty()){
            products = productRepository.findByNameContainingIgnoreCase(name, pageable);
        }
        else{
            products = productRepository.findAll(pageable);
        }
        return products.map(productMapper::toDTO);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest dto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        if(dto.getCategoryIds() != null){
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(dto.getCategoryIds()));
            product.setCategory(categories);
        }
        else {
            product.getCategory().clear();
        }
        Product updatedProduct = productRepository.save(product);
        return productMapper.toDTO(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        productRepository.delete(product);
    }
}
