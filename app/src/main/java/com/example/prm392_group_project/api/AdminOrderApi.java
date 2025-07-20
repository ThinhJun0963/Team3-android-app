package com.example.prm392_group_project.api;

import com.example.prm392_group_project.models.OrderResponseDTO;
import com.example.prm392_group_project.models.UpdateOrderStatusRequestDTO;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface AdminOrderApi {
    @GET("/admin/orders")
    Call<List<OrderResponseDTO>> getAllOrders();
    @PATCH("/orders/{id}/status")
    Call<OrderResponseDTO> updateOrderStatus(@Path("id") long id, @Body UpdateOrderStatusRequestDTO request);
}
