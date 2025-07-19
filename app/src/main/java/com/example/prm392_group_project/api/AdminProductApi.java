package com.example.prm392_group_project.api;

import com.example.prm392_group_project.models.CreateProductRequest;
import com.example.prm392_group_project.models.Product;
import com.example.prm392_group_project.models.ProductCategoryDTO;
import com.example.prm392_group_project.models.UpdateProductRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AdminProductApi {

    @GET("/admin/products")
    Call<List<Product>> getAllProducts();

    @GET("/admin/products/{id}")
    Call<Product> getProductById(@Path("id") long id);

    @POST("/admin/products")
    Call<Product> createProduct(@Body CreateProductRequest dto);

    @PUT("/admin/products/{id}")
    Call<Product> updateProduct(@Path("id") long id, @Body UpdateProductRequest request); // ✅ Đúng

    @DELETE("/admin/products/{id}")
    Call<Void> deleteProduct(@Path("id") long id);
    @GET("categories")
    Call<List<ProductCategoryDTO>> getCategories();
}
