package com.example.prm392_group_project.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.api.AdminUserApi;
import com.example.prm392_group_project.api.RetrofitClient;
import com.example.prm392_group_project.models.AccountResponseDTO;
import com.example.prm392_group_project.models.UpdateProfileRequestDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserEditActivity extends AppCompatActivity {

    private EditText edtUserName, edtUserPhone, edtUserAvatar, edtUserGender;
    private Button btnSave;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        edtUserName = findViewById(R.id.edtUserName);
        edtUserPhone = findViewById(R.id.edtUserPhone);
        edtUserAvatar = findViewById(R.id.edtUserAvatar);
        edtUserGender = findViewById(R.id.edtUserGender);
        btnSave = findViewById(R.id.btnSaveUser);

        userId = getIntent().getLongExtra("user_id", -1);
        if (userId == -1) {
            Toast.makeText(this, "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load dữ liệu người dùng nếu cần...

        btnSave.setOnClickListener(v -> updateUser());
    }

    private void updateUser() {
        UpdateProfileRequestDTO request = new UpdateProfileRequestDTO();
        request.setName(edtUserName.getText().toString());
        request.setPhoneNumber(edtUserPhone.getText().toString());
        request.setAvatar(edtUserAvatar.getText().toString());
        request.setGender(edtUserGender.getText().toString());

        AdminUserApi api = RetrofitClient.getAdminUserApi(this);
        api.updateUser(userId, request).enqueue(new Callback<AccountResponseDTO>() {
            @Override
            public void onResponse(Call<AccountResponseDTO> call, Response<AccountResponseDTO> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UserEditActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UserEditActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountResponseDTO> call, Throwable t) {
                Toast.makeText(UserEditActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
