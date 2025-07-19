package com.example.prm392_group_project.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.adapters.OrderHistoryAdapter;
import com.example.prm392_group_project.api.OrderApi;
import com.example.prm392_group_project.api.RetrofitClient;
import com.example.prm392_group_project.models.Order;
import com.example.prm392_group_project.models.ResponseMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        recyclerOrders = findViewById(R.id.recyclerOrders);
        recyclerOrders.setLayoutManager(new LinearLayoutManager(this));

        fetchOrders();
    }

    private void fetchOrders() {
        OrderApi orderApi = RetrofitClient.getOrderApi(this);
        orderApi.getMyOrders().enqueue(new Callback<ResponseMessage<List<Order>>>() {
            @Override
            public void onResponse(Call<ResponseMessage<List<Order>>> call, Response<ResponseMessage<List<Order>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<Order> orders = response.body().getData();
                    recyclerOrders.setAdapter(new OrderHistoryAdapter(orders));
                } else {
                    Toast.makeText(OrderHistoryActivity.this, "Không thể tải đơn hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage<List<Order>>> call, Throwable t) {
                Toast.makeText(OrderHistoryActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
