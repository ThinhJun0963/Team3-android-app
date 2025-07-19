package com.example.prm392_group_project.activities;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prm392_group_project.R;
import com.example.prm392_group_project.adapters.CartAdapter;
import com.example.prm392_group_project.api.OrderApi;
import com.example.prm392_group_project.api.RetrofitClient;
import com.example.prm392_group_project.models.CartItemDTO;
import com.example.prm392_group_project.models.CreateOrderRequestDTO;
import com.example.prm392_group_project.models.ResponseMessage;
import com.example.prm392_group_project.utils.CartManager;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    private RecyclerView recyclerCheckout;
    private TextView tvTotalPrice;
    private Spinner spinnerPayment;
    private Button btnPlaceOrder;

    private List<CartItemDTO> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        recyclerCheckout = findViewById(R.id.recyclerCheckout);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        spinnerPayment = findViewById(R.id.spinnerPayment);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        cartItems = CartManager.getInstance().getItems();

        // Hiển thị danh sách sản phẩm
        recyclerCheckout.setLayoutManager(new LinearLayoutManager(this));
        recyclerCheckout.setAdapter(new CartAdapter(this, cartItems, () -> {}));

        // Hiển thị tổng tiền
        BigDecimal total = BigDecimal.ZERO;
        for (CartItemDTO item : cartItems) {
            total = total.add(BigDecimal.valueOf(item.getTotalPrice()));
        }
        tvTotalPrice.setText("Tổng tiền: " + total + " VND");

        // Cài đặt phương thức thanh toán cho spinner
        ArrayAdapter<String> paymentAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Arrays.asList("CASH", "ZALOPAY")
        );
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPayment.setAdapter(paymentAdapter);

        // Xử lý khi nhấn nút đặt hàng
        btnPlaceOrder.setOnClickListener(v -> placeOrder());
    }

    private void placeOrder() {
        String selectedMethod = spinnerPayment.getSelectedItem().toString();

        CreateOrderRequestDTO request = new CreateOrderRequestDTO(cartItems, selectedMethod);

        OrderApi orderApi = RetrofitClient.getOrderApi(this);
        orderApi.createOrder(request).enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CheckoutActivity.this, "Đặt hàng thành công", Toast.LENGTH_LONG).show();
                    CartManager.getInstance().clearCart();
                    finish();
                } else {
                    Toast.makeText(CheckoutActivity.this, "Đặt hàng thất bại", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Toast.makeText(CheckoutActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
