package com.example.prm392_group_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.adapters.UserAdapter;
import com.example.prm392_group_project.api.AdminUserApi;
import com.example.prm392_group_project.api.RetrofitClient;
import com.example.prm392_group_project.models.AccountResponseDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserManagementActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        loadUsers();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        recyclerView = findViewById(R.id.recyclerUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadUsers() {
        AdminUserApi api = RetrofitClient.getAdminUserApi(this);
        api.getAllUsers().enqueue(new Callback<List<AccountResponseDTO>>() {
            @Override
            public void onResponse(Call<List<AccountResponseDTO>> call, Response<List<AccountResponseDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userAdapter = new UserAdapter(response.body(), UserManagementActivity.this::onBanToggle);
                    recyclerView.setAdapter(userAdapter);
                } else {
                    Toast.makeText(UserManagementActivity.this, "Không thể tải danh sách người dùng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AccountResponseDTO>> call, Throwable t) {
                Toast.makeText(UserManagementActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onBanToggle(AccountResponseDTO user) {
        AdminUserApi api = RetrofitClient.getAdminUserApi(this);
        Call<Void> call = user.isBanned() ?
                api.unbanUser(user.getId()) :
                api.banUser(user.getId());

        String action = user.isBanned() ? "Gỡ chặn" : "Chặn";

        new AlertDialog.Builder(this)
                .setTitle(action + " người dùng")
                .setMessage("Bạn có chắc chắn muốn " + action.toLowerCase() + " người dùng này?")
                .setPositiveButton(action, (dialog, which) -> {
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(UserManagementActivity.this, action + " thành công", Toast.LENGTH_SHORT).show();
                            loadUsers(); // refresh
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(UserManagementActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}

