package com.example.prm392_group_project.models;

public class UpdateOrderStatusRequestDTO {
    private String status;

    public UpdateOrderStatusRequestDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

