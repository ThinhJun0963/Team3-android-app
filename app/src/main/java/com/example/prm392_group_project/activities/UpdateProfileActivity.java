package com.example.prm392_group_project.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.api.RetrofitClient;
import com.example.prm392_group_project.models.ResponseMessage;
import com.example.prm392_group_project.models.UpdateProfileRequest;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText etName, etPhoneNumber, etAvatarUrl;
    private RadioGroup rgGender;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        // Khởi tạo các view
        etName = findViewById(R.id.etName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etAvatarUrl = findViewById(R.id.etAvatarUrl);
        rgGender = findViewById(R.id.rgGender);
        btnUpdate = findViewById(R.id.btnUpdate);

        // Xử lý sự kiện nhấn nút cập nhật
        btnUpdate.setOnClickListener(v -> performUpdateProfile());
    }

    private void performUpdateProfile() {
        String name = etName.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String avatar = etAvatarUrl.getText().toString().trim();
        String gender = getSelectedGender();

        // Kiểm tra dữ liệu đầu vào
        if (name.isEmpty() || phoneNumber.isEmpty() || gender.isEmpty()) {
            showSnackbar("Vui lòng điền đầy đủ thông tin bắt buộc.");
            return;
        }

        // Tạo đối tượng UpdateProfileRequest
        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest(name, phoneNumber, avatar, gender);

        // Gửi yêu cầu đến server
        RetrofitClient.getAuthService(this).updateProfile(updateProfileRequest).enqueue(new Callback<ResponseMessage<String>>() {
            @Override
            public void onResponse(Call<ResponseMessage<String>> call, Response<ResponseMessage<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseMessage<String> responseMessage = response.body();
                    if (responseMessage.isSuccess()) {
                        showSnackbar("Cập nhật hồ sơ thành công!");
                        finish(); // Quay lại màn hình trước
                    } else {
                        showSnackbar("Cập nhật thất bại: " + responseMessage.getMessage());
                    }
                } else {
                    String errorMessage = "Cập nhật hồ sơ thất bại.";
                    try {
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    showSnackbar(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage<String>> call, Throwable t) {
                showSnackbar("Lỗi kết nối: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private String getSelectedGender() {
        int selectedId = rgGender.getCheckedRadioButtonId();
        if (selectedId == R.id.rbMale) {
            return "MALE";
        } else if (selectedId == R.id.rbFemale) {
            return "FEMALE";
        } else if (selectedId == R.id.rbOther) {
            return "OTHER";
        }
        return "";
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }
}