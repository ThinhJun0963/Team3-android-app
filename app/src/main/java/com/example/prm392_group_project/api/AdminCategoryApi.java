package com.example.prm392_group_project.api;

import com.example.prm392_group_project.models.ProductCategoryDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface AdminCategoryApi {

    @GET("/categories")
    Call<List<ProductCategoryDTO>> getAllCategories();

    @POST("/categories")
    Call<ProductCategoryDTO> createCategory(@Body ProductCategoryDTO categoryDTO);

    @PUT("/categories/{id}")
    Call<ProductCategoryDTO> updateCategory(@Path("id") Long id, @Body ProductCategoryDTO categoryDTO);

    @DELETE("/categories/{id}")
    Call<Void> deleteCategory(@Path("id") Long id);
}

