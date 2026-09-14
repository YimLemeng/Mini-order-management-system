package com.Ordermanagement.OrderManagementSystem.DTO;

import lombok.Data;

@Data
public class CustomerResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private CustomerProfileResponse profile;
}
