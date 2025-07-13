package com.example.prm392_group_project.activities;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.adapters.OrderAdminAdapter;
import com.example.prm392_group_project.api.AdminOrderApi;
import com.example.prm392_group_project.api.RetrofitClient;
import com.example.prm392_group_project.models.OrderResponseDTO;

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

        loadOrders();
    }

    private void loadOrders() {
        AdminOrderApi api = RetrofitClient.getAdminOrderApi(this);
        api.getAllOrders().enqueue(new Callback<List<OrderResponseDTO>>() {
            @Override
            public void onResponse(Call<List<OrderResponseDTO>> call, Response<List<OrderResponseDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orderAdapter = new OrderAdminAdapter(response.body()); // Đảm bảo đúng tên adapter
                    recyclerOrders.setAdapter(orderAdapter);
                } else {
                    Toast.makeText(AdminOrderManagementActivity.this, "Không thể tải đơn hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<OrderResponseDTO>> call, Throwable t) {
                Toast.makeText(AdminOrderManagementActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
