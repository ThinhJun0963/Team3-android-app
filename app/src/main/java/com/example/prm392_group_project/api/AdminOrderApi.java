package com.example.prm392_group_project.api;

import com.example.prm392_group_project.models.OrderResponseDTO;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Path;

public interface AdminOrderApi {
    @GET("/admin/orders")
    Call<List<OrderResponseDTO>> getAllOrders();
    @GET("/admin/orders/{id}")
    Call<OrderResponseDTO> getOrderById(@Path("id") long id);

    @GET("/admin/accounts/{userId}/orders")
    Call<List<OrderResponseDTO>> getOrdersByUserId(@Path("userId") long userId);
}
