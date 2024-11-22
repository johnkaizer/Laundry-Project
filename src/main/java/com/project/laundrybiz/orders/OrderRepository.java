package com.project.laundrybiz.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByOrderNumber(String orderNumber);
    List<Order> findByStatus(String status);

    @Query("SELECT SUM(o.totalAmount) FROM Order o")
    Double sumTotalAmount();
}
