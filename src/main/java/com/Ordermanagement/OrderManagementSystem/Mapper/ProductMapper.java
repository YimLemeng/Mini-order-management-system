package com.Ordermanagement.OrderManagementSystem.Mapper;

import com.Ordermanagement.OrderManagementSystem.DTO.CategoryResponse;
import com.Ordermanagement.OrderManagementSystem.DTO.ProductRequest;
import com.Ordermanagement.OrderManagementSystem.DTO.ProductResponse;
import com.Ordermanagement.OrderManagementSystem.Entity.Category;
import com.Ordermanagement.OrderManagementSystem.Entity.Product;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductMapper {
    public ProductResponse toDTO(Product product){
        if (product == null) {
            return null;
        }
        ProductResponse dto = new ProductResponse();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());

        if(product.getCategory() != null)
            dto.setCategory(product.getCategory().stream()
                    .map(this::toCategoryDTO)
                    .collect(Collectors.toSet()));
        return dto;
    }

    public Product toEntity(ProductRequest dto){
        if (dto == null) {
            return null;
        }
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        return product;
    }

    public CategoryResponse toCategoryDTO(Category category){
        if (category == null) {
            return null;
        }
        CategoryResponse dto = new CategoryResponse();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }
}

