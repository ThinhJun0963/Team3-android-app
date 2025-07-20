package com.example.prm392_group_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.api.RetrofitClient;
import com.example.prm392_group_project.models.LoginDataResponse;
import com.example.prm392_group_project.models.LoginRequest;
import com.example.prm392_group_project.models.ResponseMessage;
import com.example.prm392_group_project.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister; // Loại bỏ tvForgotPassword
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tokenManager = new TokenManager(this);

        // Kiểm tra nếu người dùng đã đăng nhập, chuyển hướng ngay
        if (tokenManager.isLoggedIn()) {
            navigateToMainScreen();
            return;
        }

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        // tvForgotPassword = findViewById(R.id.tvForgotPassword); // Loại bỏ khai báo

        btnLogin.setOnClickListener(v -> performLogin());
        tvRegister.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
        // tvForgotPassword.setOnClickListener(v -> showSnackbar("Tính năng quên mật khẩu đang được phát triển.")); // Loại bỏ sự kiện
    }

    private void performLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showSnackbar("Vui lòng nhập đầy đủ email và mật khẩu.");
            return;
        }

        LoginRequest loginRequest = new LoginRequest(email, password);

        RetrofitClient.getAuthService(this).login(loginRequest).enqueue(new Callback<ResponseMessage<LoginDataResponse>>() {
            @Override
            public void onResponse(Call<ResponseMessage<LoginDataResponse>> call, Response<ResponseMessage<LoginDataResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseMessage<LoginDataResponse> responseMessage = response.body();
                    if (responseMessage.isSuccess()) {
                        LoginDataResponse loginData = responseMessage.getData();
                        if (loginData != null && loginData.getToken() != null) {
                            // Lưu token. userId không có trong response login hiện tại.
                            tokenManager.saveAuthToken(loginData.getToken()); // Cập nhật phương thức này
                            showSnackbar("Đăng nhập thành công!");
                            navigateToMainScreen();
                        } else {
                            showSnackbar("Dữ liệu đăng nhập không hợp lệ từ server.");
                        }
                    } else {
                        showSnackbar("Đăng nhập thất bại: " + responseMessage.getMessage());
                    }
                } else {
                    String errorMessage = "Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin.";
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
            public void onFailure(Call<ResponseMessage<LoginDataResponse>> call, Throwable t) {
                showSnackbar("Lỗi kết nối: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private String extractRoleFromJwt(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length >= 2) {
                String payloadJson = new String(android.util.Base64.decode(parts[1], android.util.Base64.URL_SAFE));
                org.json.JSONObject payload = new org.json.JSONObject(payloadJson);
                return payload.optString("role", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void navigateToMainScreen() {
        String token = tokenManager.getAuthToken();
        String role = extractRoleFromJwt(token);

        Intent intent;
        if ("ADMIN".equalsIgnoreCase(role)) {
            intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
        } else {
            intent = new Intent(LoginActivity.this, MainActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }
}