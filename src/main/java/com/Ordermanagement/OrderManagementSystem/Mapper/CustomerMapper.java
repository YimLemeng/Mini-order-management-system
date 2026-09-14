package com.Ordermanagement.OrderManagementSystem.Mapper;

import com.Ordermanagement.OrderManagementSystem.DTO.CustomerProfileRequest;
import com.Ordermanagement.OrderManagementSystem.DTO.CustomerProfileResponse;
import com.Ordermanagement.OrderManagementSystem.DTO.CustomerRequest;
import com.Ordermanagement.OrderManagementSystem.DTO.CustomerResponse;
import com.Ordermanagement.OrderManagementSystem.Entity.Customer;
import com.Ordermanagement.OrderManagementSystem.Entity.CustomerProfile;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerResponse toDTO(Customer customer) {
        if (customer == null) {
            return null;
        }
        CustomerResponse dto = new CustomerResponse();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setProfile(toProfileDTO(customer.getProfile()));
        return dto;
    }

    public Customer toEntity(CustomerRequest dto){
        if (dto == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        if(dto.getProfile() != null){
            CustomerProfile profile = toProfileEntity(dto.getProfile());
            customer.setProfile(profile);
            profile.setCustomer(customer);
        }
        return customer;
    }

    public CustomerProfileResponse toProfileDTO(CustomerProfile profile){
        if (profile == null) {
            return null;
        }
        CustomerProfileResponse dto = new CustomerProfileResponse();
        dto.setId(profile.getId());
        dto.setAddress(profile.getAddress());
        dto.setDateOfBirth(profile.getDateOfBirth());
        dto.setBio(profile.getBio());
        return dto;
    }

    public CustomerProfile toProfileEntity(CustomerProfileRequest dto){
        if (dto == null) {
            return null;
        }
        CustomerProfile profile = new CustomerProfile();
        profile.setAddress(dto.getAddress());
        profile.setDateOfBirth(dto.getDateOfBirth());
        profile.setBio(dto.getBio());
        return profile;
    }
}
