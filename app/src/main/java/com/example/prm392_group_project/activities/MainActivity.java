// app/src/main/java/com/example/prm392_group_project/activities/MainActivity.java
package com.example.prm392_group_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.adapters.ProductAdapter;
import com.example.prm392_group_project.api.ProductApi;
import com.example.prm392_group_project.models.Product;
import com.example.prm392_group_project.models.ProductCategoryDTO;
import com.example.prm392_group_project.api.RetrofitClient;
import com.example.prm392_group_project.utils.TokenManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TokenManager tokenManager;
    private TextView tvWelcome;
    private Button btnLogout, btnUpdateProfile;
    private Spinner spinnerCategory;
    private RecyclerView recyclerProducts;
    private ProductAdapter productAdapter;
    private List<ProductCategoryDTO> categoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tokenManager = new TokenManager(this);
        tvWelcome = findViewById(R.id.tvWelcome);
        btnLogout = findViewById(R.id.btnLogout);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        recyclerProducts = findViewById(R.id.recyclerProducts);
        recyclerProducts.setLayoutManager(new LinearLayoutManager(this));

        if (tokenManager.isLoggedIn()) {
            tvWelcome.setText("Chào mừng, bạn đã đăng nhập!");
        } else {
            tvWelcome.setText("Bạn chưa đăng nhập.");
        }

        loadCategories();

        btnLogout.setOnClickListener(v -> performLogout());
        btnUpdateProfile.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, UpdateProfileActivity.class)));
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

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
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
                }
            }

            @Override
            public void onFailure(Call<List<ProductCategoryDTO>> call, Throwable t) {
                showSnackbar("Không lấy được danh mục");
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

    private void performLogout() {
        tokenManager.deleteTokens();
        showSnackbar("Đăng xuất thành công!");
        navigateToLoginScreen();
    }

    private void navigateToLoginScreen() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }
}

