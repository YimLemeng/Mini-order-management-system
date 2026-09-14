package com.Ordermanagement.OrderManagementSystem.Mapper;

import com.Ordermanagement.OrderManagementSystem.DTO.OrderItemResponse;
import com.Ordermanagement.OrderManagementSystem.DTO.OrderResponse;
import com.Ordermanagement.OrderManagementSystem.Entity.Order;
import com.Ordermanagement.OrderManagementSystem.Entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public OrderResponse toDTO(Order order) {
        if(order == null) {
            return null;
        }
        OrderResponse dto = new OrderResponse();
        dto.setId(order.getId());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setCustomerName(order.getCustomer().getName());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus().name());
        dto.setTotalPrice(order.getTotalPrice());
        if(order.getOrderItems() != null) {
            dto.setOrderItems(order.getOrderItems()
                    .stream()
                    .map(this::toOrderItemDTO)
                    .collect(Collectors.toList())
            );
        }
        return dto;
    }

    public OrderItemResponse toOrderItemDTO(OrderItem item) {
        if(item == null) {
            return null;
        }
        OrderItemResponse dto = new OrderItemResponse();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        return dto;
    }
}
