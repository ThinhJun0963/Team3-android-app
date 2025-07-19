package com.example.prm392_group_project.models;

public class OrderDetailResponseDTO {
    private ProductDTO product;
    private int quantity;
    private double price;

    // Getters and Setters
    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
