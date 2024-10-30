package com.project.laundrybiz.orders;

import com.africastalking.sms.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.africastalking.AfricasTalking;
import com.africastalking.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Initialize Africa's Talking SMS Service
    private SmsService smsService;

    public OrderService() {
        // Replace with your Africa's Talking credentials
        String username = "shine";
        String apiKey = "atsk_8b75a9fd057a13049127d36414882d1a49cf888fa0534875e9a8f88782dec1256712c04c";
        AfricasTalking.initialize(username, apiKey);
        smsService = AfricasTalking.getService(AfricasTalking.SERVICE_SMS);
    }

    // Create or update an order
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

    // Delete order by ID (if needed)
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

    // Update order status to "Done" and send an SMS notification
    public Order markOrderAsDone(Long id) throws Exception {
        Order order = getOrderById(id);
        if (order != null) {
            order.setStatus("Done");  // Update the status to Done
            orderRepository.save(order);  // Save the updated order
            sendCompletionSMS(order.getCustomerPhoneNumber(), order.getOrderNumber());  // Send SMS notification
            return order;
        } else {
            throw new Exception("Order not found");
        }
    }

    // Send an SMS notification to the customer
    private void sendCompletionSMS(String phoneNumber, String orderNumber) {
        try {
            String message = "Your order " + orderNumber + " is now ready for pickup. Thank you for choosing our service!";
            System.out.println("Sending SMS to: " + phoneNumber);

            // Send the message and capture the response
            List<Recipient> response = smsService.send(message, new String[]{phoneNumber}, true);

            // Log the response from Africa's Talking
            for (Recipient recipient : response) {
                System.out.println("Recipient: " + recipient.number);
                System.out.println("Status: " + recipient.status);
                System.out.println("MessageId: " + recipient.messageId);
                System.out.println("Cost: " + recipient.cost);
            }

            System.out.println("SMS sent successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to send SMS to: " + phoneNumber);
        }
    }

}
