// app/src/main/java/com/example/prm392_group_project/activities/MainActivity.java
package com.example.prm392_group_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.utils.TokenManager;

public class MainActivity extends AppCompatActivity {

    private TokenManager tokenManager;
    private TextView tvWelcome;
    private Button btnLogout, btnUpdateProfile, btnProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tokenManager = new TokenManager(this);
        tvWelcome = findViewById(R.id.tvWelcome);
        btnLogout = findViewById(R.id.btnLogout);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        btnProductList = findViewById(R.id.btnProductList);

        // Hiển thị thông tin người dùng
        if (tokenManager.isLoggedIn()) {
            // userId không còn được lưu trực tiếp từ response login
            // Nếu cần hiển thị thông tin người dùng, bạn sẽ phải giải mã JWT
            // hoặc gọi một API khác để lấy thông tin profile của người dùng.
            tvWelcome.setText("Chào mừng, bạn đã đăng nhập!");
        } else {
            tvWelcome.setText("Bạn chưa đăng nhập.");
        }

        btnLogout.setOnClickListener(v -> performLogout());
        btnUpdateProfile.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, UpdateProfileActivity.class)));
        btnProductList.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
            startActivity(intent);
        });
    }

    private void performLogout() {
        // Xóa token khỏi client-side
        tokenManager.deleteTokens();
        showSnackbar("Đăng xuất thành công!");
        navigateToLoginScreen();
    }

    private void navigateToLoginScreen() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }
}