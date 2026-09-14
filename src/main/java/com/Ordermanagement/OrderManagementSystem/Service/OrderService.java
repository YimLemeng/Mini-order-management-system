package com.Ordermanagement.OrderManagementSystem.Service;

import com.Ordermanagement.OrderManagementSystem.DTO.OrderRequest;
import com.Ordermanagement.OrderManagementSystem.DTO.OrderResponse;
import org.springframework.data.domain.Page;

public interface OrderService {
    OrderResponse createOrder(OrderRequest dto);
    OrderResponse getOrderById(Long id);
    Page<OrderResponse> getAllOrders(String customerName, int page, int size, String sortBy, String sortDir);
    OrderResponse updateOrderStatus(Long id, String status);
}
