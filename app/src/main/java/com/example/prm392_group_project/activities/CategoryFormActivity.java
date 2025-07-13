package com.example.prm392_group_project.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.api.AdminCategoryApi;
import com.example.prm392_group_project.api.RetrofitClient;
import com.example.prm392_group_project.models.ProductCategoryDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFormActivity extends AppCompatActivity {

    private EditText edtName;
    private Button btnSave;
    private Long categoryId = -1L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_form);

        edtName = findViewById(R.id.edtCategoryName);
        btnSave = findViewById(R.id.btnSaveCategory);

        // Kiểm tra nếu là cập nhật
        categoryId = getIntent().getLongExtra("category_id", -1L);
        String categoryName = getIntent().getStringExtra("category_name");
        if (categoryId != -1 && categoryName != null) {
            edtName.setText(categoryName);
        }

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Tên danh mục không được trống", Toast.LENGTH_SHORT).show();
                return;
            }

            ProductCategoryDTO category = new ProductCategoryDTO();
            category.setName(name);

            AdminCategoryApi api = RetrofitClient.getAdminCategoryApi(this);
            if (categoryId != -1) {
                // Update
                api.updateCategory(categoryId, category).enqueue(getCallback("Cập nhật"));
            } else {
                // Create
                api.createCategory(category).enqueue(getCallback("Tạo"));
            }
        });
    }

    private Callback<ProductCategoryDTO> getCallback(String action) {
        return new Callback<>() {
            @Override
            public void onResponse(Call<ProductCategoryDTO> call, Response<ProductCategoryDTO> response) {
                Toast.makeText(CategoryFormActivity.this, action + " thành công", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ProductCategoryDTO> call, Throwable t) {
                Toast.makeText(CategoryFormActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }
}
