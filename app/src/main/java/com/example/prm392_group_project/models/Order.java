package com.example.prm392_group_project.models;

import com.example.prm392_group_project.models.OrderDetailResponseDTO;
import com.example.prm392_group_project.models.Payment;

import java.util.List;

public class Order {
    private int id;
    private String createdAt;
    private String status;
    private Payment payment; // Thay cho paymentMethod
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private List<OrderDetailResponseDTO> orderDetail; // Đúng tên trường trong JSON

    public int getId() { return id; }
    public String getCreatedAt() { return createdAt; }
    public String getStatus() { return status; }
    public Payment getPayment() { return payment; }
    public String getReceiverName() { return receiverName; }
    public String getReceiverPhone() { return receiverPhone; }
    public String getReceiverAddress() { return receiverAddress; }
    public List<OrderDetailResponseDTO> getOrderDetail() { return orderDetail; }
}
