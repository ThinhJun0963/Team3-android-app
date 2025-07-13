package com.example.prm392_group_project.api;

import com.example.prm392_group_project.models.AccountResponseDTO;
import com.example.prm392_group_project.models.UpdateProfileRequestDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AdminUserApi {
    @GET("/admin/accounts")
    Call<List<AccountResponseDTO>> getAllUsers();
    @PATCH("/admin/accounts/{id}")
    Call<AccountResponseDTO> updateUser(
            @Path("id") long id,
            @Body UpdateProfileRequestDTO updateDTO
    );

    @PUT("/admin/accounts/{id}/ban")
    Call<Void> banUser(@Path("id") long id);

    @PUT("/admin/accounts/{id}/unban")
    Call<Void> unbanUser(@Path("id") long id);
}
