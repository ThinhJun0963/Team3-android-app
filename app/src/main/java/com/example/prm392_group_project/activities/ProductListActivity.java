package com.example.prm392_group_project.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.adapters.ProductAdapter;
import com.example.prm392_group_project.api.ProductApi;
import com.example.prm392_group_project.api.RetrofitClient;
import com.example.prm392_group_project.models.Product;
import com.example.prm392_group_project.models.ProductCategoryDTO;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity {

    private Spinner spinnerCategory;
    private RecyclerView recyclerProducts;
    private List<ProductCategoryDTO> categoryList = new ArrayList<>();
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        spinnerCategory = findViewById(R.id.spinnerCategory);
        recyclerProducts = findViewById(R.id.recyclerProducts);
        recyclerProducts.setLayoutManager(new LinearLayoutManager(this));

        loadCategories();
    }

    private void loadCategories() {
        ProductApi productApi = RetrofitClient.getProductApi(this);
        productApi.getCategories().enqueue(new Callback<List<ProductCategoryDTO>>() {
            @Override
            public void onResponse(Call<List<ProductCategoryDTO>> call, Response<List<ProductCategoryDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryList = response.body();

                    List<String> categoryNames = new ArrayList<>();
                    categoryNames.add("Tất cả");
                    for (ProductCategoryDTO cat : categoryList) {
                        categoryNames.add(cat.getName());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ProductListActivity.this,
                            android.R.layout.simple_spinner_item, categoryNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCategory.setAdapter(adapter);

                    spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Long categoryId = (position == 0) ? null : categoryList.get(position - 1).getId();
                            fetchProductsByCategory(categoryId);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                } else {
                    showSnackbar("Không lấy được danh mục.");
                }
            }

            @Override
            public void onFailure(Call<List<ProductCategoryDTO>> call, Throwable t) {
                showSnackbar("Lỗi mạng khi tải danh mục.");
            }
        });
    }

    private void fetchProductsByCategory(Long categoryId) {
        ProductApi productApi = RetrofitClient.getProductApi(this);

        Map<String, String> queryMap = new HashMap<>();
        if (categoryId != null) queryMap.put("categoryId", String.valueOf(categoryId));

        productApi.getProductsFiltered(queryMap).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productAdapter = new ProductAdapter(response.body());
                    recyclerProducts.setAdapter(productAdapter);
                } else {
                    showSnackbar("Không lấy được sản phẩm.");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                showSnackbar("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }
}
