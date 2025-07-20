package com.example.prm392_group_project.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.activities.CartActivity;
import com.example.prm392_group_project.activities.OrderHistoryActivity;
import com.example.prm392_group_project.activities.ProductListActivity;

public class BottomNavHelper {
    public static void setupBottomNav(Activity activity) {
        View btnHome = activity.findViewById(R.id.btnHome);
        View btnCart = activity.findViewById(R.id.btnCart);
        View btnOrders = activity.findViewById(R.id.btnOrders);

        btnHome.setOnClickListener(v -> {
            if (!(activity instanceof ProductListActivity)) {
                activity.startActivity(new Intent(activity, ProductListActivity.class));
            }
        });

        btnCart.setOnClickListener(v -> {
            if (!(activity instanceof CartActivity)) {
                activity.startActivity(new Intent(activity, CartActivity.class));
            }
        });

        btnOrders.setOnClickListener(v -> {
            if (!(activity instanceof OrderHistoryActivity)) {
                activity.startActivity(new Intent(activity, OrderHistoryActivity.class));
            }
        });
    }
}
