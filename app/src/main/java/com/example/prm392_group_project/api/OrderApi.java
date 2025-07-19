package com.example.prm392_group_project.api;

import com.example.prm392_group_project.models.CreateOrderRequestDTO;
import com.example.prm392_group_project.models.ResponseMessage;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OrderApi {
    @POST("/orders")
    Call<ResponseMessage> createOrder(@Body CreateOrderRequestDTO requestDTO);
}
