package com.Ordermanagement.OrderManagementSystem.Service.impl;

import com.Ordermanagement.OrderManagementSystem.DTO.OrderItemRequest;
import com.Ordermanagement.OrderManagementSystem.DTO.OrderRequest;
import com.Ordermanagement.OrderManagementSystem.DTO.OrderResponse;
import com.Ordermanagement.OrderManagementSystem.Entity.*;
import com.Ordermanagement.OrderManagementSystem.Exception.InsufficientStockException;
import com.Ordermanagement.OrderManagementSystem.Exception.ResourceNotFoundException;
import com.Ordermanagement.OrderManagementSystem.Mapper.OrderMapper;
import com.Ordermanagement.OrderManagementSystem.Repository.CustomerRepository;
import com.Ordermanagement.OrderManagementSystem.Repository.OrderRepository;
import com.Ordermanagement.OrderManagementSystem.Repository.ProductRepository;
import com.Ordermanagement.OrderManagementSystem.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest dto){
        Customer customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(()
                -> new ResourceNotFoundException("Customer not found with id " + dto.getCustomerId()));
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        BigDecimal totalPrice = BigDecimal.ZERO;

        for(OrderItemRequest itemDTO : dto.getItems()){
            Product product = productRepository.findById(itemDTO.getProductId()).orElseThrow(()
                    -> new  ResourceNotFoundException("Product not found with id " + itemDTO.getProductId()));
            if(product.getStock() < itemDTO.getQuantity()){
                throw new InsufficientStockException("Insufficient stock for product " + product.getName()
                + ". Requested " + itemDTO.getQuantity() + " Available " + product.getStock());
            }
            product.setStock(product.getStock() - itemDTO.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPrice(product.getPrice());
            order.addOrderItem(orderItem);

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
            totalPrice = totalPrice.add(itemTotal);
        }
        order.setTotalPrice(totalPrice);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDTO(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
        return orderMapper.toDTO(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> getAllOrders(String customerName, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Order> orders;
        if(customerName != null && !customerName.trim().isEmpty()){
            orders = orderRepository.searchByCustomerName(customerName, pageable);
        }
        else {
            orders = orderRepository.findAll(pageable);
        }
        return orders.map(orderMapper::toDTO);
    }

    @Override
    @Transactional
    public OrderResponse updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
        OrderStatus oldStatus = order.getStatus();
        OrderStatus newStatus;
        try{
            newStatus = OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException ex){
            throw new IllegalArgumentException("Invalid Order Status " + status);
        }
        if(oldStatus == newStatus){
            return orderMapper.toDTO(order);
        }
        if(newStatus == OrderStatus.CANCELLED && oldStatus != OrderStatus.CANCELLED){
            for(OrderItem item : order.getOrderItems()){
                Product product = item.getProduct();
                product.setStock(product.getStock() + item.getQuantity());
                productRepository.save(product);
            }
        }
        else if(oldStatus == OrderStatus.CANCELLED && newStatus != OrderStatus.CANCELLED){
            for(OrderItem item : order.getOrderItems()){
                Product product = item.getProduct();
                if (product.getStock() < item.getQuantity()){
                    throw new InsufficientStockException("Cannot restore order status. Insufficient stock for product " + product.getName());
                }
                product.setStock(product.getStock() - item.getQuantity());
                productRepository.save(product);
            }
        }
        order.setStatus(newStatus);
        Order updateOrder = orderRepository.save(order);
        return orderMapper.toDTO(updateOrder);
    }
}
