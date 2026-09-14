package com.Ordermanagement.OrderManagementSystem.Service;

import com.Ordermanagement.OrderManagementSystem.DTO.CustomerRequest;
import com.Ordermanagement.OrderManagementSystem.DTO.CustomerResponse;
import org.springframework.data.domain.Page;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest dto);
    CustomerResponse getCustomerById(Long id);
    Page<CustomerResponse> getAllCustomers(String name, int page, int size, String sortBy, String sortDir);
    CustomerResponse updateCustomer(Long id, CustomerRequest dto);
    void deleteCustomer(Long id);
}
