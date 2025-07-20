package com.example.prm392_group_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.adapters.OrderAdminAdapter;
import com.example.prm392_group_project.api.AdminOrderApi;
import com.example.prm392_group_project.api.RetrofitClient;
import com.example.prm392_group_project.models.OrderResponseDTO;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminOrderManagementActivity extends AppCompatActivity {

    private RecyclerView recyclerOrders;
    private OrderAdminAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);

        recyclerOrders = findViewById(R.id.recyclerOrders);
        recyclerOrders.setLayoutManager(new LinearLayoutManager(this));

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.btnQueryById).setOnClickListener(v -> showOrderByIdDialog());
        findViewById(R.id.btnQueryByUser).setOnClickListener(v -> showOrdersByUserIdDialog());

        loadAllOrders();
    }

    private void loadAllOrders() {
        AdminOrderApi api = RetrofitClient.getAdminOrderApi(this);
        api.getAllOrders().enqueue(new Callback<List<OrderResponseDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<OrderResponseDTO>> call, @NonNull Response<List<OrderResponseDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orderAdapter = new OrderAdminAdapter(response.body());
                    recyclerOrders.setAdapter(orderAdapter);
                } else {
                    Toast.makeText(AdminOrderManagementActivity.this, "Không thể tải đơn hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<OrderResponseDTO>> call, @NonNull Throwable t) {
                Toast.makeText(AdminOrderManagementActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showOrderByIdDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_order_query, null);
        EditText edtId = view.findViewById(R.id.edtId);

        new AlertDialog.Builder(this)
                .setTitle("Tìm đơn hàng theo ID")
                .setView(view)
                .setPositiveButton("Tìm", (dialog, which) -> {
                    long id = Long.parseLong(edtId.getText().toString());
                    getOrderById(id);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showOrdersByUserIdDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_order_query, null);
        EditText edtId = view.findViewById(R.id.edtId);

        new AlertDialog.Builder(this)
                .setTitle("Tìm đơn theo User ID")
                .setView(view)
                .setPositiveButton("Tìm", (dialog, which) -> {
                    long userId = Long.parseLong(edtId.getText().toString());
                    getOrdersByUserId(userId);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void getOrderById(long id) {
        AdminOrderApi api = RetrofitClient.getAdminOrderApi(this);
        api.getOrderById(id).enqueue(new Callback<OrderResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<OrderResponseDTO> call, @NonNull Response<OrderResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orderAdapter = new OrderAdminAdapter(Collections.singletonList(response.body()));
                    recyclerOrders.setAdapter(orderAdapter);
                } else {
                    Toast.makeText(AdminOrderManagementActivity.this, "Không tìm thấy đơn hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<OrderResponseDTO> call, @NonNull Throwable t) {
                Toast.makeText(AdminOrderManagementActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getOrdersByUserId(long userId) {
        AdminOrderApi api = RetrofitClient.getAdminOrderApi(this);
        api.getOrdersByUserId(userId).enqueue(new Callback<List<OrderResponseDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<OrderResponseDTO>> call, @NonNull Response<List<OrderResponseDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orderAdapter = new OrderAdminAdapter(response.body());
                    recyclerOrders.setAdapter(orderAdapter);
                } else {
                    Toast.makeText(AdminOrderManagementActivity.this, "Không tìm thấy đơn hàng của user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<OrderResponseDTO>> call, @NonNull Throwable t) {
                Toast.makeText(AdminOrderManagementActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}