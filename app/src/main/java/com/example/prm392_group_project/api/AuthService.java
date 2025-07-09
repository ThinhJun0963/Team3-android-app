package com.example.prm392_group_project.api;

import com.example.prm392_group_project.models.EmailRequest;
import com.example.prm392_group_project.models.LoginDataResponse;
import com.example.prm392_group_project.models.LoginRequest;
import com.example.prm392_group_project.models.RegisterRequest;
import com.example.prm392_group_project.models.ResponseMessage;
import com.example.prm392_group_project.models.UpdateProfileRequest;
import com.example.prm392_group_project.models.VerifyEmailRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PATCH;

public interface AuthService {

    @POST("/auth/register")
    Call<ResponseMessage<String>> register(@Body RegisterRequest registerRequest);

    @POST("/auth/send-verify-email")
    Call<ResponseMessage<String>> sendVerifyEmail(@Body EmailRequest emailRequest);

    @POST("/auth/verify-email")
    Call<ResponseMessage<String>> verifyEmail(@Body VerifyEmailRequest verifyEmailRequest);

    @POST("/auth/login")
    Call<ResponseMessage<LoginDataResponse>> login(@Body LoginRequest loginRequest);

    @PATCH("/auth/update-profile")
    Call<ResponseMessage<String>> updateProfile(@Body UpdateProfileRequest updateProfileRequest);
}