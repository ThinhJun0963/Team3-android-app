package com.example.prm392_group_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.adapters.CartAdapter;
import com.example.prm392_group_project.api.OrderApi;
import com.example.prm392_group_project.api.PaymentApi;
import com.example.prm392_group_project.api.RetrofitClient;
import com.example.prm392_group_project.models.*;
import com.example.prm392_group_project.utils.CartManager;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    private RecyclerView recyclerCheckout;
    private TextView tvTotalPrice;
    private Spinner spinnerPayment;
    private Button btnPlaceOrder;
    private EditText edtReceiverName, edtReceiverPhone, edtReceiverAddress;

    private List<CartItemDTO> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        recyclerCheckout = findViewById(R.id.recyclerCheckout);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        spinnerPayment = findViewById(R.id.spinnerPayment);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        edtReceiverName = findViewById(R.id.edtReceiverName);
        edtReceiverPhone = findViewById(R.id.edtReceiverPhone);
        edtReceiverAddress = findViewById(R.id.edtReceiverAddress);

        cartItems = CartManager.getInstance().getItems();

        recyclerCheckout.setLayoutManager(new LinearLayoutManager(this));
        recyclerCheckout.setAdapter(new CartAdapter(this, cartItems, () -> {}));

        BigDecimal total = BigDecimal.ZERO;
        for (CartItemDTO item : cartItems) {
            total = total.add(BigDecimal.valueOf(item.getTotalPrice()));
        }
        tvTotalPrice.setText("Tổng tiền: " + total + " VND");

        ArrayAdapter<String> paymentAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Arrays.asList("CASH", "ZALOPAY")
        );
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPayment.setAdapter(paymentAdapter);

        btnPlaceOrder.setOnClickListener(v -> placeOrder());
        BottomNavHelper.setupBottomNav(this);
    }

    private void placeOrder() {
        String method = spinnerPayment.getSelectedItem().toString();
        String name = edtReceiverName.getText().toString().trim();
        String phone = edtReceiverPhone.getText().toString().trim();
        String address = edtReceiverAddress.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin người nhận", Toast.LENGTH_SHORT).show();
            return;
        }

        CreateOrderRequestDTO request = new CreateOrderRequestDTO(
                cartItems, method, name, phone, address
        );

        OrderApi orderApi = RetrofitClient.getOrderApi(this);
        orderApi.createOrder(request).enqueue(new Callback<ResponseMessage<OrderResponseDTO>>() {
            @Override
            public void onResponse(Call<ResponseMessage<OrderResponseDTO>> call, Response<ResponseMessage<OrderResponseDTO>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    Long orderId = response.body().getData().getId();

                    if ("ZALOPAY".equals(method)) {
                        createZaloPay(orderId);
                    } else {
                        Toast.makeText(CheckoutActivity.this, "Đặt hàng thành công", Toast.LENGTH_LONG).show();
                        CartManager.getInstance().clearCart();
                        startActivity(new Intent(CheckoutActivity.this, OrderHistoryActivity.class));
                        finish();
                    }
                } else {
                    Toast.makeText(CheckoutActivity.this, "Đặt hàng thất bại", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage<OrderResponseDTO>> call, Throwable t) {
                Toast.makeText(CheckoutActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createZaloPay(Long orderId) {
        Map<String, Object> paymentRequest = new HashMap<>();
        paymentRequest.put("orderId", orderId);
        paymentRequest.put("paymentMethod", "ZALOPAY");

        PaymentApi paymentApi = RetrofitClient.getPaymentApi(this);
        paymentApi.createPayment(paymentRequest).enqueue(new Callback<ResponseMessage<PaymentResponseDTO>>() {
            @Override
            public void onResponse(Call<ResponseMessage<PaymentResponseDTO>> call, Response<ResponseMessage<PaymentResponseDTO>> response) {
                Log.d("ZaloPay", "Status Code: " + response.code());
                Log.e("ZaloPay", "RAW response: " + new Gson().toJson(response.body()));


                ResponseMessage<PaymentResponseDTO> responseMsg = response.body();

                if (response.isSuccessful() && responseMsg != null && responseMsg.isSuccess() && responseMsg.getData() != null) {
                    String orderUrl = responseMsg.getData().getZalopay().getOrderUrl();
                    // Mở WebView hoặc chuyển màn hình
                } else {
                    Log.e("ZaloPay", "Payment failed: " + responseMsg.getMessage());
                    Toast.makeText(CheckoutActivity.this,
                            "ZaloPay thất bại: " + responseMsg.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }


            @Override
            public void onFailure(Call<ResponseMessage<PaymentResponseDTO>> call, Throwable t) {
                Toast.makeText(CheckoutActivity.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
