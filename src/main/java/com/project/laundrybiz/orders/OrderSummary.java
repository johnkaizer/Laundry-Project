package com.project.laundrybiz.orders;

public class OrderSummary {
    private long totalOrders;
    private double totalAmount;

    public OrderSummary(long totalOrders, double totalAmount) {
        this.totalOrders = totalOrders;
        this.totalAmount = totalAmount;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}

