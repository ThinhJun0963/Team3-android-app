package com.example.prm392_group_project.api;

import com.example.prm392_group_project.models.CreateOrderRequestDTO;
import com.example.prm392_group_project.models.Order;
import com.example.prm392_group_project.models.OrderResponseDTO;
import com.example.prm392_group_project.models.ResponseMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OrderApi {
    @POST("/orders")
    Call<ResponseMessage<OrderResponseDTO>> createOrder(@Body CreateOrderRequestDTO request);

    @GET("orders/me")
    Call<ResponseMessage<List<Order>>> getMyOrders();

}
