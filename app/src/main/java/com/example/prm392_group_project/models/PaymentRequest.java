package com.example.prm392_group_project.models;

public class PaymentRequest {
    private String orderId;
    private String paymentMethod;

    public PaymentRequest(String orderId, String paymentMethod) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}
