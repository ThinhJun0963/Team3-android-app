package com.example.prm392_group_project.models;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class PaymentResponseDTO {
    private Long id;
    private BigDecimal amount;
    private PaymentMethod method;
    private PaymentStatus status;
    private ZaloPayOrderResponseDTO zalopay;
    @SerializedName("isPaid")
    private Boolean isPaid;
    @SerializedName("isRefunded")
    private Boolean isRefunded;
    private String transactionId;
}
