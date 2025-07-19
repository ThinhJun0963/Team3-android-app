package com.example.prm392_group_project.models;

import java.util.List;

public class CreateOrderRequestDTO {
    private List<CartItemDTO> items;
    private String paymentMethod;

    public CreateOrderRequestDTO(List<CartItemDTO> items, String paymentMethod) {
        this.items = items;
        this.paymentMethod = paymentMethod;
    }

    public List<CartItemDTO> getItems() {
        return items;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}
