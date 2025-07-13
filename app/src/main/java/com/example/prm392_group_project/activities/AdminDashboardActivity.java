package com.example.prm392_group_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_group_project.R;

public class AdminDashboardActivity extends AppCompatActivity {

    private Button btnManageUsers, btnManageOrders, btnManageProducts, btnManageCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnManageUsers = findViewById(R.id.btnManageUsers);
        btnManageOrders = findViewById(R.id.btnManageOrders);
        btnManageProducts = findViewById(R.id.btnManageProducts);
        btnManageCategories = findViewById(R.id.btnManageCategories);

        //btnManageUsers.setOnClickListener(v ->
        //        startActivity(new Intent(this, UserManagementActivity.class)));

        //btnManageOrders.setOnClickListener(v ->
        //        startActivity(new Intent(this, OrderManagementActivity.class)));

        btnManageProducts.setOnClickListener(v ->
                startActivity(new Intent(this, ProductManagementActivity.class)));

        btnManageCategories.setOnClickListener(v ->
                startActivity(new Intent(this, CategoryManagementActivity.class)));
    }
}
