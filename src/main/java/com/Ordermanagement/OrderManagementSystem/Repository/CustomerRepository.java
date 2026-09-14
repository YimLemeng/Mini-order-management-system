package com.Ordermanagement.OrderManagementSystem.Repository;

import com.Ordermanagement.OrderManagementSystem.Entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByEmail(String email);
    Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
