package com.project.laundrybiz.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Create or update a customer
    @PostMapping
    public ResponseEntity<Customer> addOrUpdateCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.saveCustomer(customer));
    }

    // Get all customers
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    // Get a single customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a customer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    // Search customers by phone number
    @GetMapping("/search/phone")
    public ResponseEntity<List<Customer>> searchCustomersByPhone(@RequestParam String phone) {
        return ResponseEntity.ok(customerService.findByPhone(phone));
    }

    // Search customers by name
    @GetMapping("/search/name")
    public ResponseEntity<List<Customer>> searchCustomersByName(@RequestParam String name) {
        return ResponseEntity.ok(customerService.findByName(name));
    }
}

