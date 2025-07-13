package com.example.prm392_group_project.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.api.AdminProductApi;
import com.example.prm392_group_project.api.RetrofitClient;
import com.example.prm392_group_project.models.CreateProductRequest;
import com.example.prm392_group_project.models.Product;
import com.example.prm392_group_project.models.ProductCategoryDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFormActivity extends AppCompatActivity {

    private EditText edtName, edtPrice, edtStock, edtImage, edtDesc;
    private Spinner spinnerCategory;
    private Button btnSave;

    private List<ProductCategoryDTO> categories = new ArrayList<>();
    private Product editingProduct = null;
    private Long editingProductId = -1L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        edtName = findViewById(R.id.edtProductName);
        edtPrice = findViewById(R.id.edtProductPrice);
        edtStock = findViewById(R.id.edtProductStock);
        edtImage = findViewById(R.id.edtProductImage);
        edtDesc = findViewById(R.id.edtProductDesc);
        spinnerCategory = findViewById(R.id.spinnerProductCategory);
        btnSave = findViewById(R.id.btnSaveProduct);

        editingProductId = getIntent().getLongExtra("product_id", -1L);

        loadCategories();

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(edtPrice.getText().toString().trim()));
            int stock = Integer.parseInt(edtStock.getText().toString().trim());
            String img = edtImage.getText().toString().trim();
            String desc = edtDesc.getText().toString().trim();
            Long categoryId = categories.get(spinnerCategory.getSelectedItemPosition()).getId();

            AdminProductApi api = RetrofitClient.getAdminProductApi(this);

            if (editingProduct != null) {
                // Update sản phẩm
                editingProduct.setName(name);
                editingProduct.setPrice(price);
                editingProduct.setStock(stock);
                editingProduct.setImageUrl(img);
                editingProduct.setDescription(desc);
                editingProduct.getCategory().setId(categoryId);

                api.updateProduct(editingProduct.getId(), editingProduct).enqueue(callback("Cập nhật"));
            } else {
                // Tạo mới
                CreateProductRequest newProduct = new CreateProductRequest();
                newProduct.setName(name);
                newProduct.setPrice(price);
                newProduct.setStock(stock);
                newProduct.setImageUrl(img);
                newProduct.setDescription(desc);
                newProduct.setCategoryId(categoryId);

                api.createProduct(newProduct).enqueue(callback("Tạo"));
            }
        });
    }

    private void loadCategories() {
        RetrofitClient.getProductApi(this).getCategories().enqueue(new Callback<List<ProductCategoryDTO>>() {
            @Override
            public void onResponse(Call<List<ProductCategoryDTO>> call, Response<List<ProductCategoryDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categories = response.body();
                    List<String> names = new ArrayList<>();
                    for (ProductCategoryDTO cat : categories) {
                        names.add(cat.getName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ProductFormActivity.this, android.R.layout.simple_spinner_item, names);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCategory.setAdapter(adapter);

                    // Sau khi load category xong thì mới load product nếu có ID
                    if (editingProductId != -1) {
                        loadProductDetails(editingProductId);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ProductCategoryDTO>> call, Throwable t) {
                Toast.makeText(ProductFormActivity.this, "Lỗi tải danh mục", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadProductDetails(Long id) {
        AdminProductApi api = RetrofitClient.getAdminProductApi(this);
        api.getProductById(id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful() && response.body() != null) {
                    editingProduct = response.body();
                    edtName.setText(editingProduct.getName());
                    edtPrice.setText(String.valueOf(editingProduct.getPrice()));
                    edtStock.setText(String.valueOf(editingProduct.getStock()));
                    edtImage.setText(editingProduct.getImageUrl());
                    edtDesc.setText(editingProduct.getDescription());

                    // Gán category vào spinner
                    for (int i = 0; i < categories.size(); i++) {
                        if (categories.get(i).getId().equals(editingProduct.getCategory().getId())) {
                            spinnerCategory.setSelection(i);
                            break;
                        }
                    }
                } else {
                    Toast.makeText(ProductFormActivity.this, "Không thể tải chi tiết sản phẩm", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ProductFormActivity.this, "Lỗi khi tải chi tiết sản phẩm", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private Callback<Product> callback(String action) {
        return new Callback<>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Toast.makeText(ProductFormActivity.this, action + " thành công", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ProductFormActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }
}
