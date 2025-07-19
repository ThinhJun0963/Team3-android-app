package com.example.prm392_group_project.models;

public class CartItemDTO {
    private Long productId;
    private String name;
    private int quantity;
    private double price;
    private String imageUrl;

    public CartItemDTO(Long productId, String name, int quantity, double price, String imageUrl) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.imageUrl = imageUrl;

    }

    public Long getProductId() { return productId; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPrice() { return price; }
    public double getTotalPrice() { return price * quantity; }
    public String getImageUrl() {
        return imageUrl;
    }
}
