package com.project.laundrybiz.orders;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerPhoneNumber;

    private String serviceType;

    private String status;  // E.g., "In Progress", "Completed"

    private double laundryWeight;

    private double totalAmount;

    private LocalDateTime orderDate;

    private String orderNumber;

    // Method to generate the order number
    @PrePersist
    public void generateOrderNumber() {
        this.orderNumber = "TIK" + id;
    }
}
