package com.example.prm392_group_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.adapters.CartAdapter;
import com.example.prm392_group_project.models.CartItemDTO;
import com.example.prm392_group_project.utils.CartManager;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerCart;
    private TextView tvTotalPrice;
    private Button btnCheckout;
    private CartAdapter cartAdapter;
    private List<CartItemDTO> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerCart = findViewById(R.id.recyclerCart);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnCheckout = findViewById(R.id.btnCheckout);

        cartItems = CartManager.getInstance().getItems();

        // ✅ Truyền context, danh sách ban đầu và callback
        cartAdapter = new CartAdapter(this, cartItems, this::onCartUpdated);

        recyclerCart.setLayoutManager(new LinearLayoutManager(this));
        recyclerCart.setAdapter(cartAdapter);

        updateTotalPrice();

        btnCheckout.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
                return;
            }

            // ✅ Chuyển sang màn hình thanh toán
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            startActivity(intent);
            BottomNavHelper.setupBottomNav(this);
        });
    }

    // ✅ Hàm callback khi có sản phẩm bị xoá
    private void onCartUpdated() {
        cartItems = CartManager.getInstance().getItems();
        cartAdapter.updateItems(cartItems); // Cập nhật lại dữ liệu adapter
        updateTotalPrice(); // Cập nhật lại tổng tiền
    }

    private void updateTotalPrice() {
        double total = CartManager.getInstance().getTotalPrice();
        tvTotalPrice.setText("Tổng tiền: " + total + " VND");
    }
}
