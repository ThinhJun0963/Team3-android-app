package com.example.prm392_group_project.api;

import com.example.prm392_group_project.models.OrderResponseDTO;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.Call;

public interface AdminOrderApi {
    @GET("/admin/orders")
    Call<List<OrderResponseDTO>> getAllOrders();
}
