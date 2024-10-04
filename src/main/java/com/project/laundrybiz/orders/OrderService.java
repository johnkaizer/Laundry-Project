package com.project.laundrybiz.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Create or update order
    public Order saveOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        // Generate order number based on the ID after saving
        savedOrder.generateOrderNumber();
        return orderRepository.save(savedOrder); // Save again to persist the generated order number
    }

    // Fetch all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Fetch order by ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    // Delete order by ID
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    // Fetch order by order number
    public Order getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    // Fetch orders by status
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
}
