package com.Ordermanagement.OrderManagementSystem.Controller;

import com.Ordermanagement.OrderManagementSystem.DTO.OrderRequest;
import com.Ordermanagement.OrderManagementSystem.DTO.OrderResponse;
import com.Ordermanagement.OrderManagementSystem.Service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrders(@Valid @RequestBody OrderRequest dto){
        OrderResponse created = orderService.createOrder(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id){
        OrderResponse created = orderService.getOrderById(id);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getAllOrders(
            @RequestParam(required = false) String customerName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir){

        Page<OrderResponse> order = orderService.getAllOrders(customerName, page, size, sortBy, sortDir);
        return ResponseEntity.ok(order);
    }

    @RequestMapping(value = "/{id}/status", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long id, @RequestParam String status){
        OrderResponse updated = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(updated);
    }
}
