package com.Ordermanagement.OrderManagementSystem.Service;

import com.Ordermanagement.OrderManagementSystem.DTO.ProductRequest;
import com.Ordermanagement.OrderManagementSystem.DTO.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductResponse createProduct(ProductRequest dto);
    ProductResponse getProductById(Long id);
    Page<ProductResponse> getAllProducts(String name, int page, int size, String sortBy, String sortDir);
    ProductResponse updateProduct(Long id, ProductRequest dto);
    void deleteProduct(Long id);
}
