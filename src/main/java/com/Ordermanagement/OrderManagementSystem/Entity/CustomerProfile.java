package com.Ordermanagement.OrderManagementSystem.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customer_profiles")
public class CustomerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    @Column(name = "dob")
    private LocalDate dateOfBirth;

    private String bio;

    @OneToOne(mappedBy = "profile")
    private Customer customer;
}
