package com.example.prm392_group_project.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.utils.TokenManager;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

public class AdminDashboardActivity extends AppCompatActivity {

    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        tokenManager = new TokenManager(this);

        // Gán tag thủ công rồi setup card
        setupCardWithTag(R.id.cardUsers, "👤|Quản lý Người Dùng|#4FC3F7|#0F1C3F", UserManagementActivity.class);
        setupCardWithTag(R.id.cardOrders, "📄|Quản lý Đơn Hàng|#FF8A65|#0F1C3F", AdminOrderManagementActivity.class);
        setupCardWithTag(R.id.cardProducts, "📦|Quản lý Sản Phẩm|#FFD54F|#0F1C3F", ProductManagementActivity.class);
        setupCardWithTag(R.id.cardCategories, "📁|Quản lý Danh Mục|#4DB6AC|#0F1C3F", CategoryManagementActivity.class);
        setupCardWithTag(R.id.cardLogout, "🚪|Đăng xuất|#EF5350|#B71C1C", null); // logout riêng
    }

    private void setupCardWithTag(int viewId, String tagValue, Class<?> activityToStart) {
        View view = findViewById(viewId);
        view.setTag(tagValue);
        setupCard(view, activityToStart);
    }

    private void setupCard(View view, Class<?> activityToStart) {
        MaterialCardView card = (MaterialCardView) view;
        View layout = card.getChildAt(0);
        TextView iconView = layout.findViewById(R.id.cardIcon);
        TextView titleView = layout.findViewById(R.id.cardTitle);

        String tag = (String) view.getTag();
        if (tag != null) {
            String[] parts = tag.split("\\|");
            if (parts.length == 4) {
                iconView.setText(parts[0]);
                titleView.setText(parts[1]);
                card.setCardBackgroundColor(Color.parseColor(parts[2]));
                titleView.setTextColor(Color.parseColor(parts[3]));
            }
        }

        card.setOnClickListener(v -> {
            if (activityToStart != null) {
                startActivity(new Intent(AdminDashboardActivity.this, activityToStart));
            } else {
                performLogout();
            }
        });
    }

    private void performLogout() {
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
