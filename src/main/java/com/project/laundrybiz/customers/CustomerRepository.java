package com.project.laundrybiz.customers;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Custom query to find customer by phone number
    List<Customer> findByPhoneContaining(String phone);
    // Custom query to search customers by name (case-insensitive)
    List<Customer> findByNameContainingIgnoreCase(String name);
}
