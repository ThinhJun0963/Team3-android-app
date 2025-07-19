package com.example.prm392_group_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.adapters.AdminProductAdapter;
import com.example.prm392_group_project.api.AdminProductApi;
import com.example.prm392_group_project.api.RetrofitClient;
import com.example.prm392_group_project.models.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductManagementActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdminProductAdapter adapter;
    private FloatingActionButton fabAdd;

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_management);

        recyclerView = findViewById(R.id.recyclerAdminProducts);
        fabAdd = findViewById(R.id.fabAddProduct);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Quay lại AdminDashboard
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProductFormActivity.class);
            startActivity(intent);
        });

        loadProducts();
    }

    private void loadProducts() {
        AdminProductApi api = RetrofitClient.getAdminProductApi(this);
        api.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new AdminProductAdapter(
                            ProductManagementActivity.this,
                            response.body(),
                            ProductManagementActivity.this::onEdit,
                            ProductManagementActivity.this::onDelete
                    );
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(ProductManagementActivity.this, "Không thể tải sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(ProductManagementActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onEdit(Product product) {
        Intent intent = new Intent(this, ProductFormActivity.class);
        intent.putExtra("product_id", product.getId());
        startActivity(intent);
    }

    private void onDelete(Product product) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa sản phẩm")
                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    AdminProductApi api = RetrofitClient.getAdminProductApi(this);
                    api.deleteProduct(product.getId()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(ProductManagementActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                            loadProducts();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(ProductManagementActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
