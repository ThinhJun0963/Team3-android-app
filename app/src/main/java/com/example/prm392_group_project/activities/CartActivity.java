package com.example.prm392_group_project.activities;

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

        cartItems = CartManager.getCartItems();
        cartAdapter = new CartAdapter(cartItems);
        recyclerCart.setLayoutManager(new LinearLayoutManager(this));
        recyclerCart.setAdapter(cartAdapter);

        updateTotalPrice();

        btnCheckout.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
                return;
            }

            // TODO: Gửi API POST /orders ở bước sau
            Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
            CartManager.clearCart();
            cartAdapter.notifyDataSetChanged();
            updateTotalPrice();
        });
    }

    private void updateTotalPrice() {
        double total = CartManager.getTotalPrice();
        tvTotalPrice.setText("Tổng tiền: " + total + " VND");
    }
}
