package com.Ordermanagement.OrderManagementSystem.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerProfileResponse {
    private Long id;
    private String address;
    private LocalDate dateOfBirth;
    private String bio;
}
