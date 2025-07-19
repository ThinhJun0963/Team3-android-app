package com.example.prm392_group_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.adapters.CategoryAdapter;
import com.example.prm392_group_project.api.AdminCategoryApi;
import com.example.prm392_group_project.api.RetrofitClient;
import com.example.prm392_group_project.models.ProductCategoryDTO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryManagementActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private CategoryAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();
        loadCategories();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_management);

        recyclerView = findViewById(R.id.recyclerCategories);
        fabAdd = findViewById(R.id.fabAddCategory);
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
            startActivity(new Intent(this, CategoryFormActivity.class));
        });

        loadCategories();
    }

    private void loadCategories() {
        AdminCategoryApi api = RetrofitClient.getAdminCategoryApi(this);
        api.getAllCategories().enqueue(new Callback<List<ProductCategoryDTO>>() {
            @Override
            public void onResponse(Call<List<ProductCategoryDTO>> call, Response<List<ProductCategoryDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new CategoryAdapter(response.body(),
                            CategoryManagementActivity.this::onEdit,
                            CategoryManagementActivity.this::onDelete);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(CategoryManagementActivity.this, "Lỗi tải danh mục", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductCategoryDTO>> call, Throwable t) {
                Toast.makeText(CategoryManagementActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onEdit(ProductCategoryDTO category) {
        Intent intent = new Intent(this, CategoryFormActivity.class);
        intent.putExtra("category_id", category.getId());
        intent.putExtra("category_name", category.getName());
        startActivity(intent);
    }

    private void onDelete(ProductCategoryDTO category) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa danh mục")
                .setMessage("Bạn có chắc chắn muốn xóa danh mục này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    AdminCategoryApi api = RetrofitClient.getAdminCategoryApi(this);
                    api.deleteCategory(category.getId()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(CategoryManagementActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                            loadCategories();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(CategoryManagementActivity.this, "Lỗi khi xóa", Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
