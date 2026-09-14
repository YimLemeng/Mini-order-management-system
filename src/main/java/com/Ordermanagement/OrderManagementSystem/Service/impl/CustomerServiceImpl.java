package com.Ordermanagement.OrderManagementSystem.Service.impl;

import com.Ordermanagement.OrderManagementSystem.DTO.CustomerRequest;
import com.Ordermanagement.OrderManagementSystem.DTO.CustomerResponse;
import com.Ordermanagement.OrderManagementSystem.Entity.Customer;
import com.Ordermanagement.OrderManagementSystem.Entity.CustomerProfile;
import com.Ordermanagement.OrderManagementSystem.Exception.ResourceAlreadyExistsException;
import com.Ordermanagement.OrderManagementSystem.Exception.ResourceNotFoundException;
import com.Ordermanagement.OrderManagementSystem.Mapper.CustomerMapper;
import com.Ordermanagement.OrderManagementSystem.Repository.CustomerRepository;
import com.Ordermanagement.OrderManagementSystem.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional
    public CustomerResponse createCustomer(CustomerRequest dto) {
        if(customerRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Customer with email " + dto.getEmail() + " already exists");
        }
        Customer customer = customerMapper.toEntity(dto);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toDTO(savedCustomer);
    }

    @Override
    @Transactional (readOnly = true)
    public CustomerResponse getCustomerById(Long id){
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return customerMapper.toDTO(customer);
    }

    @Override
    @Transactional (readOnly = true)
    public Page<CustomerResponse> getAllCustomers(String name, int page, int size, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Customer> customers;
        if(name != null && !name.trim().isEmpty()) {
            customers = customerRepository.findByNameContainingIgnoreCase(name, pageable);
        }
        else {
            customers = customerRepository.findAll(pageable);
        }
        return customers.map(customerMapper::toDTO);
    }

    @Override
    @Transactional
    public CustomerResponse updateCustomer(Long id, CustomerRequest dto) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        if(!customer.getEmail().equals(dto.getEmail()) &&
                customerRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new ResourceAlreadyExistsException("Customer with email " + dto.getEmail() + " already exists");
        }
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        if(dto.getProfile() != null){
            if(customer.getProfile() == null){
                CustomerProfile profile = customerMapper.toProfileEntity(dto.getProfile());
                customer.setProfile(profile);
                profile.setCustomer(customer);
            }
            else {
                CustomerProfile profile = customer.getProfile();
                profile.setAddress(dto.getProfile().getAddress());
                profile.setDateOfBirth(dto.getProfile().getDateOfBirth());
                profile.setBio(dto.getProfile().getBio());
            }
        }
        else{
            customer.setProfile(null);
        }
        Customer updatedCustomer = customerRepository.save(customer);
        return customerMapper.toDTO(updatedCustomer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id){
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        customerRepository.delete(customer);
    }
}
