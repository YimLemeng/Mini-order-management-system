package com.Ordermanagement.OrderManagementSystem.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private Set<CategoryResponse> category;
}
