package com.example.prm392_group_project.models;

public class OrderItem {
    private Long productId;
    private String productName;
    private int quantity;
    private double price;

    // Getters
    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
