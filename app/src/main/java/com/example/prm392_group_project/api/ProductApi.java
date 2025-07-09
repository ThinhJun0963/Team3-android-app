package com.example.prm392_group_project.api;

import com.example.prm392_group_project.models.Product;
import com.example.prm392_group_project.models.ProductCategoryDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ProductApi {

    @GET("api/products")
    Call<List<Product>> getProducts(
            @Query("sortBy") String sortBy,
            @Query("direction") String direction,
            @Query("categoryId") Long categoryId,
            @Query("minPrice") BigDecimal minPrice,
            @Query("maxPrice") BigDecimal maxPrice,
            @Query("search") String search,
            @Query("minStock") Integer minStock,
            @Query("maxStock") Integer maxStock
    );

    @GET("api/products")
    Call<List<Product>> getProductsFiltered(@QueryMap Map<String, String> filters);

    @GET("categories")
    Call<List<ProductCategoryDTO>> getCategories();

}
