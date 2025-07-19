package com.example.prm392_group_project.models;

import java.util.List;

public class CreateOrderRequestDTO {
    private List<CartItemDTO> items;
    private String paymentMethod;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;

    public CreateOrderRequestDTO(List<CartItemDTO> items, String paymentMethod,
                                 String receiverName, String receiverPhone, String receiverAddress) {
        this.items = items;
        this.paymentMethod = paymentMethod;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.receiverAddress = receiverAddress;
    }
    // Getters
    public List<CartItemDTO> getItems() {
        return items;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }
}
