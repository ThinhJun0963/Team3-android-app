package com.example.prm392_group_project.models;

import java.math.BigDecimal;

public class PaymentResponseDTO {
    private Long id;
    private BigDecimal amount;
    private String method;
    private String status;
    private ZaloPayResponseDTO zalopay;
    private String transactionId;
    private boolean isPaid;
    private boolean isRefunded;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZaloPayResponseDTO getZalopay() {
        return zalopay;
    }

    public void setZalopay(ZaloPayResponseDTO zalopay) {
        this.zalopay = zalopay;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isRefunded() {
        return isRefunded;
    }

    public void setRefunded(boolean refunded) {
        isRefunded = refunded;
    }
}
