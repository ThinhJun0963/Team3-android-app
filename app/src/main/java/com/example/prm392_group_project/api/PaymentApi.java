package com.example.prm392_group_project.api;

import com.example.prm392_group_project.models.PaymentResponseDTO;
import com.example.prm392_group_project.models.ResponseMessage;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PaymentApi {
    @POST("payment/create")
    Call<ResponseMessage<PaymentResponseDTO>> createPayment(@Body Map<String, Object> paymentRequest);

}
