package com.project.laundrybiz.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Create or update an order
    @PostMapping
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.saveOrder(order));
    }

    // Fetch all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // Fetch order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete order by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build(); // Return 204 status code (No Content)
    }

    // Fetch order by order number
    @GetMapping("/search/by-order-number")
    public ResponseEntity<Order> getOrderByOrderNumber(@RequestParam String orderNumber) {
        Order order = orderService.getOrderByOrderNumber(orderNumber);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Fetch orders by status
    @GetMapping("/search/by-status")
    public ResponseEntity<List<Order>> getOrdersByStatus(@RequestParam String status) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }

    // Update an existing order
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        Order existingOrder = orderService.getOrderById(id);
        if (existingOrder != null) {
            existingOrder.setCustomerPhoneNumber(orderDetails.getCustomerPhoneNumber());
            existingOrder.setServiceType(orderDetails.getServiceType());
            existingOrder.setStatus(orderDetails.getStatus());
            existingOrder.setLaundryWeight(orderDetails.getLaundryWeight());
            existingOrder.setTotalAmount(orderDetails.getTotalAmount());
            existingOrder.setOrderDate(orderDetails.getOrderDate());
            return ResponseEntity.ok(orderService.saveOrder(existingOrder));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
