package com.example.prm392_group_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.utils.TokenManager;
import com.google.android.material.snackbar.Snackbar;

public class AdminDashboardActivity extends AppCompatActivity {

    private TokenManager tokenManager;
    private Button btnManageUsers, btnManageOrders, btnManageProducts, btnManageCategories, btnAdminLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        tokenManager = new TokenManager(this);
        btnManageUsers = findViewById(R.id.btnManageUsers);
        btnManageOrders = findViewById(R.id.btnManageOrders);
        btnManageProducts = findViewById(R.id.btnManageProducts);
        btnManageCategories = findViewById(R.id.btnManageCategories);
        btnAdminLogout = findViewById(R.id.btnAdminLogout);

        btnManageUsers.setOnClickListener(v ->
                startActivity(new Intent(this, UserManagementActivity.class)));

        btnManageOrders.setOnClickListener(v ->
                startActivity(new Intent(this, AdminOrderManagementActivity.class)));

        btnManageProducts.setOnClickListener(v ->
                startActivity(new Intent(this, ProductManagementActivity.class)));

        btnManageCategories.setOnClickListener(v ->
                startActivity(new Intent(this, CategoryManagementActivity.class)));
        btnAdminLogout.setOnClickListener(v -> performLogout());
    }
    private void performLogout() {
        // Xóa token khỏi client-side
        tokenManager.deleteTokens();
        showSnackbar("Đăng xuất thành công!");
        navigateToLoginScreen();
    }
    private void navigateToLoginScreen() {
        Intent intent = new Intent(AdminDashboardActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }
}
